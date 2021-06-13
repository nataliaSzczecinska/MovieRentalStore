package com.movie.rental.store.service;

import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.movie.rental.store.facade.sql.SQLCondition.sqlCondition;

@RequiredArgsConstructor
@Service
public class MovieDbService {
    private final MovieRepository movieRepository;

    public List<Movie> getAllMovie() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(final Long movieId) {
        return movieRepository.findById(movieId);
    }

    public Movie saveMovie(final Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> searchMovieByTitle(String titleFragment){
        return movieRepository.retrieveMovieByTitleFragment(sqlCondition(titleFragment));
    }

    public List<Movie> searchMovieByDirector(String directorFragment){
        return movieRepository.retrieveMovieByDirectorFragment(sqlCondition(directorFragment));
    }

    public List<Movie> searchMovieByDescription(String directorFragment){
        return movieRepository.retrieveMovieByDescriptionFragment(sqlCondition(directorFragment));
    }
}
