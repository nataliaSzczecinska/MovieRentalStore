package com.movie.rental.store.mapper;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.dto.MovieDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MovieMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieMapper.class);

    public Movie mapToMovie (final MovieDto movieDto, final List<Copy> copies) {
        LOGGER.info("Map Movie to MovieDto");
        return new Movie(movieDto.getMovieId(),
                movieDto.getMovieTitle(),
                movieDto.getMovieDirector(),
                movieDto.getMovieDescription(),
                movieDto.getMovieType(),
                movieDto.getMovieYear(),
                copies);
    }

    public MovieDto mapToMovieDto (final Movie movie) {
        LOGGER.info("Map MovieDto to Movie");
        return new MovieDto(movie.getMovieId(),
                movie.getMovieTitle(),
                movie.getMovieDirector(),
                movie.getMovieDescription(),
                movie.getMovieType(),
                movie.getMovieYear());
    }

    public List<MovieDto> mapToMovieDtoList (final List<Movie> movieList) {
        LOGGER.info("Map MovieList to MovieDtoList");
        return movieList.stream()
                .map(movie -> mapToMovieDto(movie))
                .collect(Collectors.toList());
    }
}
