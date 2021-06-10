package com.movie.rental.store.domain.dto;

import com.movie.rental.store.domain.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieDto {
    private Long movieId;
    private String movieTitle;
    private String movieDirector;
    private List<Type> movieTypes;
    private int movieYear;
    private List<Long> copiesId;
}
