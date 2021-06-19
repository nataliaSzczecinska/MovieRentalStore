package com.movie.rental.store.controller;

import com.movie.rental.store.exception.MovieNotFoundException;
import com.movie.rental.store.exception.TitleNotFoundInOMDbException;
import com.movie.rental.store.review.domain.MovieReviewDto;
import com.movie.rental.store.review.facade.MovieReviewFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies/review")
@RequiredArgsConstructor
public class MovieReviewController {
    private final MovieReviewFacade movieReviewFacade;

    @GetMapping(value = "/{movieId}")
    public MovieReviewDto getReviewFromMovieReviewApi(@PathVariable Long movieId) throws TitleNotFoundInOMDbException, MovieNotFoundException {
        return movieReviewFacade.getReviewsOfMovie(movieId);
    }
}
