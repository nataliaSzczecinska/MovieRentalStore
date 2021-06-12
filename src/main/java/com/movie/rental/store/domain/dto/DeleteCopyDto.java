package com.movie.rental.store.domain.dto;

import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
