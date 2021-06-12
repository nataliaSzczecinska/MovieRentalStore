package com.movie.rental.store.domain.dto;

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
public class BorrowArchiveDto {
    private Long borrowArchiveId;
    private Long previousBorrowId;
    private Long copyId;
    private Long customerId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate realReturnDate;
    private BorrowArchiveType borrowArchiveType;
}