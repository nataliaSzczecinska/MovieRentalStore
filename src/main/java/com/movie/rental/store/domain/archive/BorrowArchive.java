package com.movie.rental.store.domain.archive;

import com.movie.rental.store.domain.enums.BorrowArchiveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamedQuery(
        name = "BorrowArchive.retrieveBorrowArchiveByCustomerId",
        query = "FROM BorrowArchive WHERE customerId = id"
)
@Entity
@Table(name = "BORROWS_ARCHIVE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BorrowArchive {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "BORROW_ARCHIVE_ID")
    private Long borrowArchiveId;

    @NotNull
    @Column(name = "PREVIOUS_BORROW_ID")
    private Long previousBorrowId;

    @NotNull
    @Column(name = "COPY_ID")
    private Long copyId;

    @NotNull
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @NotNull
    @Column(name = "BORROW_DATE")
    private LocalDate borrowDate;

    @NotNull
    @Column(name = "RETURN_DATE")
    private LocalDate returnDate;

    @Column(name = "REAL_RETURN_DATE")
    private LocalDate realReturnDate;

    @NotNull
    @Column(name = "BORROW_ARCHIVE_TYPE")
    private BorrowArchiveType borrowArchiveType;

    public BorrowArchive(@NotNull Long previousBorrowId, @NotNull Long copyId, @NotNull Long customerId, @NotNull LocalDate borrowDate, @NotNull LocalDate returnDate, LocalDate realReturnDate, @NotNull BorrowArchiveType borrowArchiveType) {
        this.previousBorrowId = previousBorrowId;
        this.copyId = copyId;
        this.customerId = customerId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.realReturnDate = realReturnDate;
        this.borrowArchiveType = borrowArchiveType;
    }
}
