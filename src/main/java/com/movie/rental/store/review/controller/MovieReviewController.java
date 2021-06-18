package com.movie.rental.store.review.controller;

import com.movie.rental.store.exception.TitleNotFoundInOMDbException;
import com.movie.rental.store.review.client.MovieReviewApiClient;
import com.movie.rental.store.review.domain.MovieReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies/review")
@RequiredArgsConstructor
public class MovieReviewController {
    private final MovieReviewApiClient movieReviewApiClient;

    @RequestMapping(method = RequestMethod.GET, value = "/{title}")
    public MovieReviewDto getReviewFromMovieReviewApi(@PathVariable String title) throws TitleNotFoundInOMDbException {
        return movieReviewApiClient.getMovieReview(title);
    }
}
