package com.movie.rental.store.controller;

import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.domain.dto.MovieDto;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<MovieDto> getAllMovies() {
        List<MovieDto> movieList = new ArrayList<>();
        movieList.add(new MovieDto(1L, "Title 1", "Director 1", "Description", Type.COMEDY, 2000));
        movieList.add(new MovieDto(2L, "Title 2", "Director 2", "Description", Type.COMEDY, 1999));
        movieList.add(new MovieDto(3L, "Title 3", "Director 3", "Description", Type.FAMILY, 2020));
        return movieList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}")
    public MovieDto getMovieById(@PathVariable Long movieId) {
        return new MovieDto(1L, "Movie by id", "Director 1", "Description", Type.CRIMINAL, 2000);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/movieByTitle")
    public List<MovieDto> getMoviesByTitle(@RequestParam String movieTitle) {
        List<MovieDto> movieList = new ArrayList<>();
        movieList.add(new MovieDto(1L, "Movie by title 1", "Director 1", "Description", Type.ROMANTIC, 2000));
        movieList.add(new MovieDto(2L, "Movie by title 2", "Director 2", "Description", Type.SCIENCE_FICTION, 1999));
        movieList.add(new MovieDto(3L, "Movie by title 3", "Director 3", "Description", Type.THRILLER, 2020));
        return movieList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/movieByDirector")
    public List<MovieDto> getMoviesByDirector(@RequestParam String movieDirector) {
        List<MovieDto> movieList = new ArrayList<>();
        movieList.add(new MovieDto(1L, "Movie by director 1", "Director 1", "Description", Type.FANTASY, 2000));
        movieList.add(new MovieDto(2L, "Movie by director 2", "Director 2", "Description", Type.HORROR, 1999));
        movieList.add(new MovieDto(3L, "Movie by director 3", "Director 3", "Description", Type.FAMILY, 2020));
        return movieList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/movieByYear")
    public List<MovieDto> getMoviesByYear(@RequestParam int movieYear) {
        List<MovieDto> movieList = new ArrayList<>();
        movieList.add(new MovieDto(1L, "Movie by year 1", "Director 1", "Description", Type.ADVENTURE, 2000));
        movieList.add(new MovieDto(2L, "Movie by year 2", "Director 2", "Description", Type.HORROR, 1999));
        movieList.add(new MovieDto(3L, "Movie by year 3", "Director 3", "Description", Type.ANIMATED, 2020));
        return movieList;
    }

    //jak to zrobić?
    @RequestMapping(method = RequestMethod.GET, value = "/movieByType")
    public List<MovieDto> getMoviesByType(@RequestParam String movieType) {
        List<MovieDto> movieList = new ArrayList<>();
        movieList.add(new MovieDto(1L, "Movie by type 1", "Director 1", "Description", Type.COMEDY, 2000));
        movieList.add(new MovieDto(2L, "Movie by type 2", "Director 2", "Description", Type.COMEDY, 1999));
        movieList.add(new MovieDto(3L, "Movie by type 3", "Director 3", "Description", Type.COMEDY, 2020));
        return movieList;
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
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public MovieDto updateMovie(@RequestBody MovieDto movieDto) {
        LOGGER.info("The movie has just been updated");
        return new MovieDto(1L, "Update movie", "Director 1", "Description", Type.ACTION, 2000);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{movieId}")
    public void deleteMovie(@PathVariable Long movieId) {
        LOGGER.info("The movie has just been deleted");
    }
}
