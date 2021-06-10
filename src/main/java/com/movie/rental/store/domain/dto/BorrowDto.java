package com.movie.rental.store.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BorrowDto {
    private Long borrowId;
    private Long copyId;
    private Long customerId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean isFinish;
}
