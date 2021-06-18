package com.movie.rental.store.omdb.client;

import com.movie.rental.store.exception.TitleNotFoundInOMDbException;
import com.movie.rental.store.omdb.domain.MovieOmdbDto;
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
public class OmdbClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(OmdbClient.class);
    private final RestTemplate restTemplate;

    /*@Value("${omdb.api.url}")
    private String omdbApiUrl;

    @Value("${omdb.api.key}")
    private String omdbKey;*/

    public MovieOmdbDto getMovieOmdb(String title) throws TitleNotFoundInOMDbException {
        URI url = UriComponentsBuilder.fromHttpUrl("http://www.omdbapi.com/")
                .queryParam("apikey", "e1e600a7")
                .queryParam("t", title)
                .queryParam("plot", "full")
                .build()
                .encode()
                .toUri();
        LOGGER.info("The url address to omdb api: " + url);
        MovieOmdbDto movieRespond = restTemplate.getForObject(url, MovieOmdbDto.class);

        return Optional.ofNullable(movieRespond).orElseThrow(TitleNotFoundInOMDbException::new);
    }
}
