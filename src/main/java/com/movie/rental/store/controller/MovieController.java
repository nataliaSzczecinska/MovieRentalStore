package com.movie.rental.store.controller;

import com.movie.rental.store.domain.enums.Type;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<MovieDto> getAllMovies() {
        LOGGER.info("Get all movies");
        return movieFacade.getAllMovies();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}")
    public MovieDto getMovieById(@PathVariable Long movieId) throws MovieNotFoundException {
        LOGGER.info("Get movie by id " + movieId);
        return movieFacade.getMovieById(movieId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/movieByTitle")
    public List<MovieDto> getMoviesByTitle(@RequestParam String movieTitle) {
        return movieFacade.getMoviesByTitle(movieTitle);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/movieByDirector")
    public List<MovieDto> getMoviesByDirector(@RequestParam String movieDirector) {
        return movieFacade.getMoviesByDirector(movieDirector);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/movieByDescription")
    public List<MovieDto> getMoviesByDescription(@RequestParam String movieDescription) {
        return movieFacade.getMoviesByDescription(movieDescription);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/movieByYear")
    public List<MovieDto> getMoviesByYear(@RequestParam int movieYear) {
        return movieFacade.getMoviesByYear(movieYear);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/movieByType")
    public List<MovieDto> getMoviesByType(@RequestParam String movieType) {
        return movieFacade.getMoviesByType(movieType);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/movieByFilmweb")
    public List<MovieDto> getMoviesByFilmweb() {
        List<MovieDto> movieList = new ArrayList<>();
        movieList.add(new MovieDto(1L, "Movie by filmweb 1", "Director 1", "Description", Type.COMEDY, 2000));
        movieList.add(new MovieDto(2L, "Movie by filmweb 2", "Director 2", "Description", Type.ACTION, 1999));
        movieList.add(new MovieDto(3L, "Movie by filmweb 3", "Director 3", "Description", Type.ACTION, 2020));
        return movieList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/movieByImdb")
    public List<MovieDto> getMoviesByImdb() {
        List<MovieDto> movieList = new ArrayList<>();
        movieList.add(new MovieDto(1L, "Movie by imdb 1", "Director 1", "Description", Type.ACTION, 2000));
        movieList.add(new MovieDto(2L, "Movie by imdb 2", "Director 2", "Description", Type.ADVENTURE, 1999));
        movieList.add(new MovieDto(3L, "Movie by imdb 3", "Director 3", "Description", Type.ANIMATED, 2020));
        return movieList;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createMovie(@RequestBody MovieDto movieDto) {
        LOGGER.info("The new movie has just been created");
        movieFacade.createMovie(movieDto);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public MovieDto updateMovie(@RequestBody MovieDto movieDto) {
        LOGGER.info("The movie has just been updated");
        return movieFacade.updateMovie(movieDto);
    }
}
