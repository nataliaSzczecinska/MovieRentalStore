package com.movie.rental.store.facade;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.domain.dto.BorrowDto;
import com.movie.rental.store.domain.enums.BorrowArchiveType;
import com.movie.rental.store.exception.BorrowNotFoundException;
import com.movie.rental.store.exception.CopyNotFoundException;
import com.movie.rental.store.exception.CustomerNotFoundException;
import com.movie.rental.store.mapper.BorrowMapper;
import com.movie.rental.store.mapper.archive.ToArchiveMapper;
import com.movie.rental.store.service.BorrowDbService;
import com.movie.rental.store.service.CopyDbService;
import com.movie.rental.store.service.CustomerDbService;
import com.movie.rental.store.service.archive.BorrowArchiveDbService;
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
    private final BorrowArchiveDbService borrowArchiveDbService;
    private final ToArchiveMapper toArchiveMapper;
    private final BorrowValidator borrowValidator;


    public List<BorrowDto> getBorrowsByMovieId(final Long movieId) {
        return borrowMapper.mapToBorrowDtoList(borrowDbService.getAllBorrows().stream()
                .filter(borrow -> borrow.getCopy().getMovie().getMovieId().equals(movieId))
                .collect(Collectors.toList()));
    }

    public List<BorrowDto> getBorrowsByUserId(final Long userId) {
        return borrowMapper.mapToBorrowDtoList(borrowDbService.getAllBorrows().stream()
                .filter(borrow -> borrow.getCustomer().getCustomerId().equals(userId))
                .collect(Collectors.toList()));
    }

    public void createBorrow(final BorrowDto borrowDto) throws CopyNotFoundException, CustomerNotFoundException {
        borrowValidator.createBorrowIfPossible(borrowDto);
    }

    public BorrowDto changeBorrowReturnDate(final String newBorrowDateText, final Long borrowId) throws BorrowNotFoundException {
        Borrow borrow = borrowDbService.getBorrowById(borrowId).orElseThrow(BorrowNotFoundException::new);
        borrow.setReturnDate(LocalDate.parse(newBorrowDateText));
        Borrow borrowSave = borrowDbService.saveBorrow(borrow);
        return borrowMapper.mapToBorrowDto(borrowSave);
    }

    public void borrowIsFinished(final Long borrowId, final String finishReason) throws BorrowNotFoundException {
        Borrow borrow = borrowDbService.getBorrowById(borrowId).orElseThrow(BorrowNotFoundException::new);
        LocalDate returnDate = borrowValidator.getReturnDateOfBorrow(BorrowArchiveType.valueOf(finishReason));
        BorrowArchive borrowArchive = toArchiveMapper.mapToBorrowArchive(borrow, returnDate, BorrowArchiveType.valueOf(finishReason));
        borrowArchiveDbService.saveBorrowArchive(borrowArchive);
        borrowValidator.deleteCopyIfItLostOrDestroy(finishReason, borrow);
        borrowDbService.deleteBorrow(borrowId);
    }
}
