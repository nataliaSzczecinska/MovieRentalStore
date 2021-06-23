package com.movie.rental.store.controller;

import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.omdb.client.OmdbClient;
import com.movie.rental.store.omdb.facade.OmdbFacade;
import com.movie.rental.store.review.client.MovieReviewApiClient;
import com.movie.rental.store.review.domain.LinkDto;
import com.movie.rental.store.review.domain.MovieReviewDto;
import com.movie.rental.store.review.domain.ResultDto;
import com.movie.rental.store.review.facade.MovieReviewFacade;
import com.movie.rental.store.service.MovieDbService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(MovieReviewController.class)
public class MovieReviewControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieReviewApiClient movieReviewApiClient;

    @MockBean
    private MovieReviewFacade movieReviewFacade;

    @MockBean
    private MovieDbService movieDbService;

    private Movie movie = Movie.builder()
            .movieId(1L)
            .movieTitle("Movie title")
            .movieDirector("Movie director")
            .movieDescription("Movie description")
            .movieType(Type.FAMILY)
            .movieYear(2020)
            .build();

    private LinkDto linkDto = LinkDto.builder()
            .reviewType("Review type")
            .reviewUrl("Url address of review")
            .build();

    private ResultDto resultDto = ResultDto.builder()
            .displayTitle("Review title")
            .mpaaRating("Mpaa rating")
            .authorOfReview("Review author")
            .headline("Review headline")
            .summary("Review summary")
            .link(linkDto)
            .build();

    private MovieReviewDto movieReviewDto = MovieReviewDto.builder()
            .results(Arrays.asList(resultDto))
            .build();

    @Test
    public void getMovieOmdbByIdTest() throws Exception {
        //Given
        when(movieReviewFacade.getReviewsOfMovie(1L)).thenReturn(movieReviewDto);
        when(movieReviewApiClient.getMovieReview("Movie title")).thenReturn(movieReviewDto);
        when(movieDbService.getMovieById(1L)).thenReturn(Optional.of(movie));

        //When & Then
        mockMvc.perform(get("/movies/review/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].display_title", is("Review title")))
                .andExpect(jsonPath("$.results[0].mpaa_rating", is("Mpaa rating")))
                .andExpect(jsonPath("$.results[0].byline", is("Review author")))
                .andExpect(jsonPath("$.results[0].headline", is("Review headline")))
                .andExpect(jsonPath("$.results[0].summary_short", is("Review summary")))
                .andExpect(jsonPath("$.results[0].link.type", is("Review type")))
                .andExpect(jsonPath("$.results[0].link.url", is("Url address of review")));
    }
}
