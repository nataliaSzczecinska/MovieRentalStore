package com.movie.rental.store.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Borrow {
    private Long borrowId;
    private Copy copy;
    private Customer customer;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate realReturnDate;
    private boolean isFinish;
}
