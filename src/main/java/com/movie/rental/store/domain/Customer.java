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
public class Customer {
    private Long customerId;
    private String customerMailAddress;
    private LocalDate createAccountDate;
    private boolean isBlocked;
}
