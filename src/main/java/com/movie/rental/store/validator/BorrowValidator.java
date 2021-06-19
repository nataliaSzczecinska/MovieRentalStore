package com.movie.rental.store.validator;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.enums.BorrowArchiveType;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.exception.CustomerNotFoundException;
import com.movie.rental.store.exception.MovieNotFoundException;
import com.movie.rental.store.mapper.archive.ToArchiveMapper;
import com.movie.rental.store.service.BorrowDbService;
import com.movie.rental.store.service.CopyDbService;
import com.movie.rental.store.service.CustomerDbService;
import com.movie.rental.store.service.MovieDbService;
import com.movie.rental.store.service.archive.DeleteCopyDbService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BorrowValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowValidator.class);
    private final MovieDbService movieDbService;
    private final DeleteCopyDbService deleteCopyDbService;
    private final ToArchiveMapper toArchiveMapper;
    private final BorrowDbService borrowDbService;
    private final CopyDbService copyDbService;
    private final CustomerDbService customerDbService;

    public LocalDate getReturnDateOfBorrow(BorrowArchiveType borrowArchiveType) {
        if (borrowArchiveType.equals(BorrowArchiveType.RETURN_LATE)
                || borrowArchiveType.equals(BorrowArchiveType.RETURN_ON_TIME)) {
            return LocalDate.now();
        } else {
            return null;
        }
    }

    public void deleteCopyIfItLostOrDestroy(String borrowArchiveType, Copy copy) {
        if (BorrowArchiveType.COPY_DESTROY_OR_LOST.equals(BorrowArchiveType.valueOf(borrowArchiveType))) {
            DeleteCopy deleteCopy = toArchiveMapper.mapToDeleteCopy(copy, LocalDate.now());
            deleteCopyDbService.saveDeletedCopy(deleteCopy);
            copyDbService.deleteCopyById(copy.getCopyId());
        }
    }

    public void createBorrowIfPossible(Long movieId, Long customerId, String mediaType) throws MovieNotFoundException, CustomerNotFoundException {
        List<Copy> copies = movieDbService.getMovieById(movieId).orElseThrow(MovieNotFoundException::new).getCopies().stream()
                .filter(copy -> Status.AVAILABLE.equals(copy.getCopyStatus()))
                .filter(copy -> MediaType.valueOf(mediaType).equals(copy.getMediaType()))
                .collect(Collectors.toList());
        Customer customer = customerDbService.getCustomerById(customerId).orElseThrow(CustomerNotFoundException::new);
        if (customer.isBlocked()) {
            LOGGER.warn("The borrow cannot be created! The customer is blocked");
        } else if (copies.isEmpty()) {
            LOGGER.warn("There is no available copies of movie id {}" +
                    " and media type {}", movieId, mediaType);
        } else {
            LOGGER.info("The borrow has been created");
            Copy copy = copies.get(0);
            copy.setCopyStatus(Status.BORROWED);
            copyDbService.saveCopy(copy);
            Borrow borrow = Borrow.builder()
                    .copy(copy)
                    .customer(customer)
                    .borrowDate(LocalDate.now())
                    .returnDate(LocalDate.now().plusDays(5L))
                    .build();
            borrowDbService.saveBorrow(borrow);
        }
    }
}
