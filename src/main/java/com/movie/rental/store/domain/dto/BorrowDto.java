package com.movie.rental.store.domain.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
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

    public BorrowDto(Long copyId, Long customerId) {
        this.copyId = copyId;
        this.customerId = customerId;
    }
}
