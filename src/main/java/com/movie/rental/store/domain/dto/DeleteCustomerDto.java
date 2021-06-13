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
public class DeleteCustomerDto {
    private Long deleteCustomerId;
    private Long previousCustomerId;
    private String customerMailAddress;
    private LocalDate createAccountDate;
    private boolean isBlocked;
    private LocalDate deleteAccountDate;
}
