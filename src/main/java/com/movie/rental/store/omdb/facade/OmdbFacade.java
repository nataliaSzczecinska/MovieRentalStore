package com.movie.rental.store.omdb.facade;

import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.exception.MovieNotFoundException;
import com.movie.rental.store.exception.TitleNotFoundInOMDbException;
import com.movie.rental.store.omdb.client.OmdbClient;
import com.movie.rental.store.omdb.domain.MovieOmdbDto;
import com.movie.rental.store.service.MovieDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OmdbFacade {
    private final OmdbClient omdbClient;
    private final MovieDbService movieDbService;

    public MovieOmdbDto getMoreDetailsAboutMovie(final Long movieId) throws TitleNotFoundInOMDbException, MovieNotFoundException {
        Movie movie = movieDbService.getMovieById(movieId).orElseThrow(MovieNotFoundException::new);
        return omdbClient.getMovieOmdb(movie.getMovieTitle());
    }
}
