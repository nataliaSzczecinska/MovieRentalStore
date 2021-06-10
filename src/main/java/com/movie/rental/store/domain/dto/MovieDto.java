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
    private String movieDescription;
    private Type movieType;
    private int movieYear;

    public MovieDto(String movieTitle, String movieDirector, String movieDescription, Type movieType, int movieYear) {
        this.movieTitle = movieTitle;
        this.movieDirector = movieDirector;
        this.movieDescription = movieDescription;
        this.movieType = movieType;
        this.movieYear = movieYear;
    }
}
