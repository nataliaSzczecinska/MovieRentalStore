package com.movie.rental.store.mapper;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.dto.MovieDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MovieMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieMapper.class);

    public Movie mapToMovie(final MovieDto movieDto, final List<Copy> copies, final List<DeleteCopy> deleteCopies) {
        LOGGER.info("Map MovieDto to Movie");
        return  Movie.builder()
                .movieId(movieDto.getMovieId())
                .movieTitle(movieDto.getMovieTitle())
                .movieDirector(movieDto.getMovieDirector())
                .movieDescription(movieDto.getMovieDescription())
                .movieType(movieDto.getMovieType())
                .movieYear(movieDto.getMovieYear())
                .copies(copies)
                .deleteCopies(deleteCopies)
                .build();
    }

    public MovieDto mapToMovieDto (final Movie movie) {
        LOGGER.info("Map Movie to MovieDto");
        return  MovieDto.builder()
                .movieId(movie.getMovieId())
                .movieTitle(movie.getMovieTitle())
                .movieDirector(movie.getMovieDirector())
                .movieDescription(movie.getMovieDescription())
                .movieType(movie.getMovieType())
                .movieYear(movie.getMovieYear())
                .build();

    }

    public List<MovieDto> mapToMovieDtoList (final List<Movie> movieList) {
        LOGGER.info("Map MovieList to MovieDtoList");
        return movieList.stream()
                .map(movie -> mapToMovieDto(movie))
                .collect(Collectors.toList());
    }
}
