package com.movie.rental.store.domain.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDto {
    private Long customerId;
    private String customerMailAddress;
    private LocalDate createAccountDate;
    private boolean isBlocked;

    public CustomerDto(String customerMailAddress) {
        this.customerMailAddress = customerMailAddress;
    }
}
