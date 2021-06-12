package com.movie.rental.store.domain.archive;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.enums.BorrowArchiveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BorrowArchive {
    private Long borrowArchiveId;
    private Long previousBorrowId;;
    private Long copyId;
    private Long customerId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate realReturnDate;
    private BorrowArchiveType borrowArchiveType;

    public BorrowArchive(Long previousBorrowId, Long copyId, Long customerId, LocalDate borrowDate, LocalDate returnDate, LocalDate realReturnDate, BorrowArchiveType borrowArchiveType) {
        this.previousBorrowId = previousBorrowId;
        this.copyId = copyId;
        this.customerId = customerId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.realReturnDate = realReturnDate;
        this.borrowArchiveType = borrowArchiveType;
    }
}
