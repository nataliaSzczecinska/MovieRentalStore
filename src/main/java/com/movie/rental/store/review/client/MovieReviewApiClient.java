package com.movie.rental.store.review.client;

import com.movie.rental.store.exception.TitleNotFoundInOMDbException;
import com.movie.rental.store.review.domain.MovieReviewDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import java.util.*;

@Component
@RequiredArgsConstructor
public class MovieReviewApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieReviewApiClient.class);
    private final RestTemplate restTemplate;

    @Value("${review.api.url}")
    private String reviewApiUrl;

    @Value("${review.api.key}")
    private String reviewKey;

    public MovieReviewDto getMovieReview(String title) throws TitleNotFoundInOMDbException {
        URI url = UriComponentsBuilder.fromHttpUrl(reviewApiUrl)
                .queryParam("api-key", reviewKey)
                .queryParam("query", title)
                .build()
                .encode()
                .toUri();
        LOGGER.info("The url address to movie review api: {}", url);
        MovieReviewDto movieReviewRespond = restTemplate.getForObject(url, MovieReviewDto.class);

        return Optional.ofNullable(movieReviewRespond).orElseThrow(TitleNotFoundInOMDbException::new);
    }
}
