package com.movie.rental.store.validator;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.dto.BorrowDto;
import com.movie.rental.store.domain.enums.BorrowArchiveType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.exception.CopyNotFoundException;
import com.movie.rental.store.exception.CustomerNotFoundException;
import com.movie.rental.store.mapper.archive.ToArchiveMapper;
import com.movie.rental.store.service.BorrowDbService;
import com.movie.rental.store.service.CopyDbService;
import com.movie.rental.store.service.CustomerDbService;
import com.movie.rental.store.service.archive.DeleteCopyDbService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class BorrowValidator {
    private final DeleteCopyDbService deleteCopyDbService;
    private final ToArchiveMapper toArchiveMapper;
    private final BorrowDbService borrowDbService;
    private final CopyDbService copyDbService;
    private final CustomerDbService customerDbService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowValidator.class);

    public LocalDate getReturnDateOfBorrow(BorrowArchiveType borrowArchiveType) {
        if (borrowArchiveType.equals(BorrowArchiveType.RETURN_LATE)
                || borrowArchiveType.equals(BorrowArchiveType.RETURN_ON_TIME)) {
            return LocalDate.now();
        } else {
            return null;
        }
    }

    public void deleteCopyIfItLostOrDestroy(String borrowArchiveType, Borrow borrow) {
        if (BorrowArchiveType.COPY_DESTROY_OR_LOST.equals(BorrowArchiveType.valueOf(borrowArchiveType))) {
            Copy copy = borrow.getCopy();
            DeleteCopy deleteCopy = toArchiveMapper.mapToDeleteCopy(copy, LocalDate.now());
            deleteCopyDbService.saveDeletedCopy(deleteCopy);
        }
    }

    public void createBorrowIfPossible(BorrowDto borrowDto) throws CopyNotFoundException, CustomerNotFoundException {
        Copy copy = copyDbService.getCopyById(borrowDto.getCopyId()).orElseThrow(CopyNotFoundException::new);
        Customer customer = customerDbService.getCustomerById(borrowDto.getCustomerId()).orElseThrow(CustomerNotFoundException::new);
        if (Status.BORROWED.equals(copy.getCopyStatus()) || customer.isBlocked()) {
            LOGGER.warn("The borrow cannot be created!");
        } else {
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
