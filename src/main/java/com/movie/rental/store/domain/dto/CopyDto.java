package com.movie.rental.store.domain.dto;

import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CopyDto {
    private Long copyId;
    private Long titleId;
    private Status copyStatus;
    private MediaType mediaType;
}
