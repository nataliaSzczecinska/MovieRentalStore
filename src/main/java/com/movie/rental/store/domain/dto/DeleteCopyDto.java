package com.movie.rental.store.domain.dto;

import com.movie.rental.store.domain.enums.MediaType;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeleteCopyDto {
    private Long deleteCopyId;
    private Long previousCopyId;
    private Long movieId;
    private MediaType mediaType;
    private LocalDate deleteDate;
}
