package com.movie.rental.store.omdb.controller;

import com.movie.rental.store.exception.TitleNotFoundInOMDbException;
import com.movie.rental.store.omdb.client.OmdbClient;
import com.movie.rental.store.omdb.domain.MovieOmdbDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies/omdb")
@RequiredArgsConstructor
public class OmdbController {
    private final OmdbClient omdbClient;

    @RequestMapping(method = RequestMethod.GET, value = "/{title}")
    public MovieOmdbDto getMovieFromOmdbApi(@PathVariable String title) throws TitleNotFoundInOMDbException {
        return omdbClient.getMovieOmdb(title);
    }
}
