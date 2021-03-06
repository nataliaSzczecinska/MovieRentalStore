package com.movie.rental.store.facade;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.dto.BorrowDto;
import com.movie.rental.store.exception.BorrowNotFoundException;
import com.movie.rental.store.exception.CustomerNotFoundException;
import com.movie.rental.store.exception.MovieNotFoundException;
import com.movie.rental.store.mapper.BorrowMapper;
import com.movie.rental.store.service.BorrowDbService;
import com.movie.rental.store.validator.BorrowValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BorrowFacade {
    private final BorrowMapper borrowMapper;
    private final BorrowDbService borrowDbService;
    private final BorrowValidator borrowValidator;

    public List<BorrowDto> getAllBorrows() {
        return borrowMapper.mapToBorrowDtoList(borrowDbService.getAllBorrows());
    }

    public BorrowDto getBorrowById(final Long borrowId) throws BorrowNotFoundException {
        return borrowMapper.mapToBorrowDto(borrowDbService.getBorrowById(borrowId).orElseThrow(BorrowNotFoundException::new));
    }

    public List<BorrowDto> getBorrowsByMovieId(final Long movieId) {
        return borrowMapper.mapToBorrowDtoList(borrowDbService.getAllBorrows().stream()
                .filter(borrow -> borrow.getCopy().getMovie().getMovieId().equals(movieId))
                .collect(Collectors.toList()));
    }

    public List<BorrowDto> getBorrowsByCustomerId(final Long customerId) {
        return borrowMapper.mapToBorrowDtoList(borrowDbService.getAllBorrows().stream()
                .filter(borrow -> borrow.getCustomer().getCustomerId().equals(customerId))
                .collect(Collectors.toList()));
    }

    public void createBorrow(final Long movieId, final Long customerId, final String mediaType) throws MovieNotFoundException, CustomerNotFoundException {
        borrowValidator.createBorrowIfPossible(movieId, customerId, mediaType);
    }

    public BorrowDto changeBorrowReturnDate(final String newBorrowDateText, final Long borrowId) throws BorrowNotFoundException {
        Borrow borrow = borrowDbService.getBorrowById(borrowId).orElseThrow(BorrowNotFoundException::new);
        borrow.setReturnDate(LocalDate.parse(newBorrowDateText));
        Borrow borrowSave = borrowDbService.saveBorrow(borrow);
        return borrowMapper.mapToBorrowDto(borrowSave);
    }

    public void borrowIsFinished(final Long borrowId, final String finishReason) throws BorrowNotFoundException {
        borrowValidator.finishBorrow(finishReason, borrowId);
    }
}
