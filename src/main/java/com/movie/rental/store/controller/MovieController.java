package com.movie.rental.store.controller;

import com.movie.rental.store.domain.dto.MovieDto;
import com.movie.rental.store.exception.MovieNotFoundException;
import com.movie.rental.store.facade.MovieFacade;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);
    private final MovieFacade movieFacade;

    @GetMapping
    public List<MovieDto> getAllMovies() {
        LOGGER.info("Get all movies");
        return movieFacade.getAllMovies();
    }

    @GetMapping(value = "/{movieId}")
    public MovieDto getMovieById(@PathVariable Long movieId) throws MovieNotFoundException {
        LOGGER.info("Get movie by id {}", movieId);
        return movieFacade.getMovieById(movieId);
    }

    @GetMapping(value = "/movieByTitle")
    public List<MovieDto> getMoviesByTitle(@RequestParam String movieTitle) {
        return movieFacade.getMoviesByTitle(movieTitle);
    }

    @GetMapping(value = "/movieByDirector")
    public List<MovieDto> getMoviesByDirector(@RequestParam String movieDirector) {
        return movieFacade.getMoviesByDirector(movieDirector);
    }

    @GetMapping(value = "/movieByDescription")
    public List<MovieDto> getMoviesByDescription(@RequestParam String movieDescription) {
        return movieFacade.getMoviesByDescription(movieDescription);
    }

    @GetMapping(value = "/movieByYear")
    public List<MovieDto> getMoviesByYear(@RequestParam int movieYear) {
        return movieFacade.getMoviesByYear(movieYear);
    }

    @GetMapping(value = "/movieByType")
    public List<MovieDto> getMoviesByType(@RequestParam String movieType) {
        return movieFacade.getMoviesByType(movieType);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createMovie(@RequestBody MovieDto movieDto) {
        LOGGER.info("The new movie has just been created");
        movieFacade.createMovie(movieDto);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public MovieDto updateMovie(@RequestBody MovieDto movieDto) {
        LOGGER.info("The movie id {} has just been updated", movieDto.getMovieId());
        return movieFacade.updateMovie(movieDto);
    }
}
