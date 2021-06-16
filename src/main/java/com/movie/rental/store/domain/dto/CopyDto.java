package com.movie.rental.store.domain.dto;

import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CopyDto {
    private Long copyId;
    private Long movieId;
    private Status copyStatus;
    private MediaType mediaType;
}
