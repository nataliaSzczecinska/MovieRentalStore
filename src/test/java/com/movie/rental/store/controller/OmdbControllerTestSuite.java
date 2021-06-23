package com.movie.rental.store.controller;

import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.omdb.client.OmdbClient;
import com.movie.rental.store.omdb.domain.MovieOmdbDto;
import com.movie.rental.store.omdb.domain.RatingsDto;
import com.movie.rental.store.omdb.facade.OmdbFacade;
import com.movie.rental.store.service.MovieDbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(OmdbController.class)
public class OmdbControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OmdbClient omdbClient;

    @MockBean
    private OmdbFacade omdbFacade;

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

    private RatingsDto ratings1Dto = RatingsDto.builder()
            .source("Source 1")
            .value("Value 1")
            .build();

    private RatingsDto ratings2Dto = RatingsDto.builder()
            .source("Source 2")
            .value("Value 2")
            .build();

    private MovieOmdbDto movieOmdbDto = MovieOmdbDto.builder()
            .title("Movie title")
            .year(2020)
            .genre("FAMILY")
            .director("Movie director")
            .actors("Movie actors")
            .plot("Movie plot")
            .awards("Movie awards")
            .production("Movie production")
            .ratings(Arrays.asList(ratings1Dto, ratings2Dto))
            .build();

    @Test
    public void getMovieOmdbByIdTest() throws Exception {
        //Given
        when(omdbFacade.getMoreDetailsAboutMovie(1L)).thenReturn(movieOmdbDto);
        when(omdbClient.getMovieOmdb("Movie title")).thenReturn(movieOmdbDto);
        when(movieDbService.getMovieById(1L)).thenReturn(Optional.of(movie));

        //When & Then
        mockMvc.perform(get("/movies/omdb/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Title", is("Movie title")))
                .andExpect(jsonPath("$.Year", is(2020)))
                .andExpect(jsonPath("$.Genre", is("FAMILY")))
                .andExpect(jsonPath("$.Director", is("Movie director")))
                .andExpect(jsonPath("$.Actors", is("Movie actors")))
                .andExpect(jsonPath("$.Plot", is("Movie plot")))
                .andExpect(jsonPath("$.Awards", is("Movie awards")))
                .andExpect(jsonPath("$.Production", is("Movie production")))
                .andExpect(jsonPath("$.Ratings[0].Source", is("Source 1")))
                .andExpect(jsonPath("$.Ratings[0].Value", is("Value 1")))
                .andExpect(jsonPath("$.Ratings[1].Source", is("Source 2")))
                .andExpect(jsonPath("$.Ratings[1].Value", is("Value 2")));
    }
}
