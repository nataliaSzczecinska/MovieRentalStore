package com.movie.rental.store.controller;

import com.movie.rental.store.exception.MovieNotFoundException;
import com.movie.rental.store.exception.TitleNotFoundInOMDbException;
import com.movie.rental.store.omdb.domain.MovieOmdbDto;
import com.movie.rental.store.omdb.facade.OmdbFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies/omdb")
@RequiredArgsConstructor
public class OmdbController {
    private final OmdbFacade omdbFacade;

    @GetMapping(value = "/{movieId}")
    public MovieOmdbDto getMovieFromOmdbApi(@PathVariable Long movieId) throws TitleNotFoundInOMDbException, MovieNotFoundException {
        return omdbFacade.getMoreDetailsAboutMovie(movieId);
    }
}
