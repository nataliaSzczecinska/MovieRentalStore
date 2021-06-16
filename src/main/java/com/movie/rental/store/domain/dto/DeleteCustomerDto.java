package com.movie.rental.store.domain.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
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
