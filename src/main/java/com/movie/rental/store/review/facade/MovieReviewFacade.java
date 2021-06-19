package com.movie.rental.store.review.facade;

import com.movie.rental.store.exception.MovieNotFoundException;
import com.movie.rental.store.exception.TitleNotFoundInOMDbException;
import com.movie.rental.store.review.client.MovieReviewApiClient;
import com.movie.rental.store.review.domain.MovieReviewDto;
import com.movie.rental.store.service.MovieDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieReviewFacade {
    private final MovieDbService movieDbService;
    private final MovieReviewApiClient movieReviewApiClient;

    public MovieReviewDto getReviewsOfMovie(final Long movieId) throws MovieNotFoundException, TitleNotFoundInOMDbException {
        return movieReviewApiClient.getMovieReview(movieDbService.getMovieById(movieId).orElseThrow(MovieNotFoundException::new).getMovieTitle());
    }
}
