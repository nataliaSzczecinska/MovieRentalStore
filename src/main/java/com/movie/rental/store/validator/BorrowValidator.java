package com.movie.rental.store.validator;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.enums.BorrowArchiveType;
import com.movie.rental.store.mapper.archive.ToArchiveMapper;
import com.movie.rental.store.service.archive.DeleteCopyDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class BorrowValidator {
    private final DeleteCopyDbService deleteCopyDbService;
    private final ToArchiveMapper toArchiveMapper;

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
}
