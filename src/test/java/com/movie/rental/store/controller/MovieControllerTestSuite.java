package com.movie.rental.store.controller;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.dto.MovieDto;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.facade.MovieFacade;
import com.movie.rental.store.mapper.MovieMapper;
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
@WebMvcTest(MovieController.class)
public class MovieControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieMapper movieMapper;

    @MockBean
    private MovieFacade movieFacade;

    @MockBean
    private MovieDbService movieDbService;

    @Test
    public void getAllMoviesTest() throws Exception {
        //Given
        Movie movie1 = Movie.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();
        Movie movie2 = Movie.builder()
                .movieId(2L)
                .movieTitle("Movie title 2")
                .movieDirector("Movie director 2")
                .movieDescription("Movie description 2")
                .movieType(Type.CRIMINAL)
                .movieYear(2021)
                .build();
        MovieDto movie1Dto = MovieDto.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();
        MovieDto movie2Dto = MovieDto.builder()
                .movieId(2L)
                .movieTitle("Movie title 2")
                .movieDirector("Movie director 2")
                .movieDescription("Movie description 2")
                .movieType(Type.CRIMINAL)
                .movieYear(2021)
                .build();
        List<Movie> movies = Arrays.asList(movie1, movie2);
        List<MovieDto> moviesDto = Arrays.asList(movie1Dto, movie2Dto);

        when(movieMapper.mapToMovieDtoList(movies)).thenReturn(moviesDto);
        when(movieFacade.getAllMovies()).thenReturn(moviesDto);
        when(movieDbService.getAllMovie()).thenReturn(movies);

        //When & Then
        mockMvc.perform(get("/movies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].movieTitle", is("Movie title 1")))
                .andExpect(jsonPath("$[0].movieDirector", is("Movie director 1")))
                .andExpect(jsonPath("$[0].movieType", is("FAMILY")))
                .andExpect(jsonPath("$[0].movieYear", is(2020)))
                .andExpect(jsonPath("$[1].movieId", is(2)))
                .andExpect(jsonPath("$[1].movieTitle", is("Movie title 2")))
                .andExpect(jsonPath("$[1].movieDirector", is("Movie director 2")))
                .andExpect(jsonPath("$[1].movieType", is("CRIMINAL")))
                .andExpect(jsonPath("$[1].movieYear", is(2021)));
    }

    @Test
    public void getMovieByIdTest() throws Exception {
        //Given
        Movie movie = Movie.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();
        MovieDto movieDto = MovieDto.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();

        when(movieMapper.mapToMovieDto(movie)).thenReturn(movieDto);
        when(movieFacade.getMovieById(1L)).thenReturn(movieDto);
        when(movieDbService.getMovieById(1L)).thenReturn(Optional.of(movie));

        //When & Then
        mockMvc.perform(get("/movies/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", is(1)))
                .andExpect(jsonPath("$.movieTitle", is("Movie title 1")))
                .andExpect(jsonPath("$.movieDirector", is("Movie director 1")))
                .andExpect(jsonPath("$.movieType", is("FAMILY")))
                .andExpect(jsonPath("$.movieYear", is(2020)));
    }

    @Test
    public void getMoviesByTitleTest() throws Exception {
        //Given
        Movie movie1 = Movie.builder()
                .movieId(1L)
                .movieTitle("Abcdefgh")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();
        Movie movie2 = Movie.builder()
                .movieId(2L)
                .movieTitle("Ab def")
                .movieDirector("Movie director 2")
                .movieDescription("Movie description 2")
                .movieType(Type.CRIMINAL)
                .movieYear(2021)
                .build();
        MovieDto movie1Dto = MovieDto.builder()
                .movieId(1L)
                .movieTitle("Abcdefgh")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();
        MovieDto movie2Dto = MovieDto.builder()
                .movieId(2L)
                .movieTitle("Ab def")
                .movieDirector("Movie director 2")
                .movieDescription("Movie description 2")
                .movieType(Type.CRIMINAL)
                .movieYear(2021)
                .build();
        List<Movie> movies = Arrays.asList(movie1, movie2);
        List<MovieDto> moviesDto = Arrays.asList(movie1Dto, movie2Dto);

        when(movieMapper.mapToMovieDtoList(movies)).thenReturn(moviesDto);
        when(movieFacade.getMoviesByTitle("def")).thenReturn(moviesDto);
        when(movieDbService.searchMovieByTitle("def")).thenReturn(movies);

        //When & Then
        mockMvc.perform(get("/movies/movieByTitle=def").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].movieTitle", is("Abcdefgh")))
                .andExpect(jsonPath("$[0].movieDirector", is("Movie director 1")))
                .andExpect(jsonPath("$[0].movieType", is("FAMILY")))
                .andExpect(jsonPath("$[0].movieYear", is(2020)))
                .andExpect(jsonPath("$[1].movieId", is(2)))
                .andExpect(jsonPath("$[1].movieTitle", is("Ab def")))
                .andExpect(jsonPath("$[1].movieDirector", is("Movie director 2")))
                .andExpect(jsonPath("$[1].movieType", is("CRIMINAL")))
                .andExpect(jsonPath("$[1].movieYear", is(2021)));
    }

    @Test
    public void getMoviesByDirectorTest() throws Exception {
        //Given
        Movie movie1 = Movie.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Anna Wilk")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();
        Movie movie2 = Movie.builder()
                .movieId(2L)
                .movieTitle("Movie title 2")
                .movieDirector("Anna Kowal")
                .movieDescription("Movie description 2")
                .movieType(Type.CRIMINAL)
                .movieYear(2021)
                .build();
        MovieDto movie1Dto = MovieDto.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Anna Wilk")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();
        MovieDto movie2Dto = MovieDto.builder()
                .movieId(2L)
                .movieTitle("Movie title 2")
                .movieDirector("Anna Kowal")
                .movieDescription("Movie description 2")
                .movieType(Type.CRIMINAL)
                .movieYear(2021)
                .build();
        List<Movie> movies = Arrays.asList(movie1, movie2);
        List<MovieDto> moviesDto = Arrays.asList(movie1Dto, movie2Dto);

        when(movieMapper.mapToMovieDtoList(movies)).thenReturn(moviesDto);
        when(movieFacade.getMoviesByDirector("anna")).thenReturn(moviesDto);
        when(movieDbService.searchMovieByDirector("anna")).thenReturn(movies);

        //When & Then
        mockMvc.perform(get("/movies/movieByDirector=anna").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].movieTitle", is("Movie title 1")))
                .andExpect(jsonPath("$[0].movieDirector", is("Anna Wilk")))
                .andExpect(jsonPath("$[0].movieType", is("FAMILY")))
                .andExpect(jsonPath("$[0].movieYear", is(2020)))
                .andExpect(jsonPath("$[1].movieId", is(2)))
                .andExpect(jsonPath("$[1].movieTitle", is("Movie title 2")))
                .andExpect(jsonPath("$[1].movieDirector", is("Anna Kowal")))
                .andExpect(jsonPath("$[1].movieType", is("CRIMINAL")))
                .andExpect(jsonPath("$[1].movieYear", is(2021)));
    }

    @Test
    public void getMoviesByDescriptionTest() throws Exception {
        //Given
        Movie movie1 = Movie.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();
        Movie movie2 = Movie.builder()
                .movieId(2L)
                .movieTitle("Movie title 2")
                .movieDirector("Movie director 2")
                .movieDescription("Movie description 2")
                .movieType(Type.CRIMINAL)
                .movieYear(2021)
                .build();
        MovieDto movie1Dto = MovieDto.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();
        MovieDto movie2Dto = MovieDto.builder()
                .movieId(2L)
                .movieTitle("Movie title 2")
                .movieDirector("Movie director 2")
                .movieDescription("Movie description 2")
                .movieType(Type.CRIMINAL)
                .movieYear(2021)
                .build();
        List<Movie> movies = Arrays.asList(movie1, movie2);
        List<MovieDto> moviesDto = Arrays.asList(movie1Dto, movie2Dto);

        when(movieMapper.mapToMovieDtoList(movies)).thenReturn(moviesDto);
        when(movieFacade.getMoviesByDescription("description")).thenReturn(moviesDto);
        when(movieDbService.searchMovieByDescription("description")).thenReturn(movies);

        //When & Then
        mockMvc.perform(get("/movies/movieByDescription=description").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].movieTitle", is("Movie title 1")))
                .andExpect(jsonPath("$[0].movieDirector", is("Movie director 1")))
                .andExpect(jsonPath("$[0].movieType", is("FAMILY")))
                .andExpect(jsonPath("$[0].movieYear", is(2020)))
                .andExpect(jsonPath("$[1].movieId", is(2)))
                .andExpect(jsonPath("$[1].movieTitle", is("Movie title 2")))
                .andExpect(jsonPath("$[1].movieDirector", is("Movie director 2")))
                .andExpect(jsonPath("$[1].movieType", is("CRIMINAL")))
                .andExpect(jsonPath("$[1].movieYear", is(2021)));
    }

    @Test
    public void getMoviesByYearTest() throws Exception {
        //Given
        Movie movie1 = Movie.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();
        Movie movie2 = Movie.builder()
                .movieId(2L)
                .movieTitle("Movie title 2")
                .movieDirector("Movie director 2")
                .movieDescription("Movie description 2")
                .movieType(Type.CRIMINAL)
                .movieYear(2021)
                .build();
        MovieDto movie1Dto = MovieDto.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();
        MovieDto movie2Dto = MovieDto.builder()
                .movieId(2L)
                .movieTitle("Movie title 2")
                .movieDirector("Movie director 2")
                .movieDescription("Movie description 2")
                .movieType(Type.CRIMINAL)
                .movieYear(2020)
                .build();
        List<Movie> movies = Arrays.asList(movie1, movie2);
        List<MovieDto> moviesDto = Arrays.asList(movie1Dto, movie2Dto);

        when(movieMapper.mapToMovieDtoList(movies)).thenReturn(moviesDto);
        when(movieFacade.getMoviesByYear(2020)).thenReturn(moviesDto);
        when(movieDbService.getAllMovie()).thenReturn(movies);

        //When & Then
        mockMvc.perform(get("/movies/movieByYear=2020").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].movieTitle", is("Movie title 1")))
                .andExpect(jsonPath("$[0].movieDirector", is("Movie director 1")))
                .andExpect(jsonPath("$[0].movieType", is("FAMILY")))
                .andExpect(jsonPath("$[0].movieYear", is(2020)))
                .andExpect(jsonPath("$[1].movieId", is(2)))
                .andExpect(jsonPath("$[1].movieTitle", is("Movie title 2")))
                .andExpect(jsonPath("$[1].movieDirector", is("Movie director 2")))
                .andExpect(jsonPath("$[1].movieType", is("CRIMINAL")))
                .andExpect(jsonPath("$[1].movieYear", is(2020)));
    }

    @Test
    public void getMoviesByTypeTest() throws Exception {
        //Given
        Movie movie1 = Movie.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();
        Movie movie2 = Movie.builder()
                .movieId(2L)
                .movieTitle("Movie title 2")
                .movieDirector("Movie director 2")
                .movieDescription("Movie description 2")
                .movieType(Type.CRIMINAL)
                .movieYear(2021)
                .build();
        MovieDto movie1Dto = MovieDto.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.ROMANTIC)
                .movieYear(2020)
                .build();
        MovieDto movie2Dto = MovieDto.builder()
                .movieId(2L)
                .movieTitle("Movie title 2")
                .movieDirector("Movie director 2")
                .movieDescription("Movie description 2")
                .movieType(Type.ROMANTIC)
                .movieYear(2021)
                .build();
        List<Movie> movies = Arrays.asList(movie1, movie2);
        List<MovieDto> moviesDto = Arrays.asList(movie1Dto, movie2Dto);

        when(movieMapper.mapToMovieDtoList(movies)).thenReturn(moviesDto);
        when(movieFacade.getMoviesByType("ROMANTIC")).thenReturn(moviesDto);
        when(movieDbService.getAllMovie()).thenReturn(movies);

        //When & Then
        mockMvc.perform(get("/movies/movieByType=ROMANTIC").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].movieTitle", is("Movie title 1")))
                .andExpect(jsonPath("$[0].movieDirector", is("Movie director 1")))
                .andExpect(jsonPath("$[0].movieType", is("ROMANTIC")))
                .andExpect(jsonPath("$[0].movieYear", is(2020)))
                .andExpect(jsonPath("$[1].movieId", is(2)))
                .andExpect(jsonPath("$[1].movieTitle", is("Movie title 2")))
                .andExpect(jsonPath("$[1].movieDirector", is("Movie director 2")))
                .andExpect(jsonPath("$[1].movieType", is("ROMANTIC")))
                .andExpect(jsonPath("$[1].movieYear", is(2021)));
    }

    @Test
    public void updateMovieTest() throws Exception {
        //Given
        List<Copy> copies = new ArrayList<>();
        List<DeleteCopy> deleteCopies = new ArrayList<>();
        Movie movie = Movie.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .copies(copies)
                .deleteCopies(deleteCopies)
                .build();
        MovieDto movieDto = MovieDto.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();

        when(movieMapper.mapToMovieDto(ArgumentMatchers.any(Movie.class))).thenReturn(movieDto);
        when(movieMapper.mapToMovie(ArgumentMatchers.any(MovieDto.class), ArgumentMatchers.eq(copies), ArgumentMatchers.eq(deleteCopies))).thenReturn(movie);
        when(movieDbService.saveMovie(ArgumentMatchers.any(Movie.class))).thenReturn(movie);
        when(movieFacade.updateMovie(ArgumentMatchers.any(MovieDto.class))).thenReturn(movieDto);

        Gson gson = new Gson();
        String jsContent = gson.toJson(movieDto);

        //When & Then
        mockMvc.perform(put("/movies").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept("application/json")
                .content(jsContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", is(1)))
                .andExpect(jsonPath("$.movieTitle", is("Movie title 1")))
                .andExpect(jsonPath("$.movieDirector", is("Movie director 1")))
                .andExpect(jsonPath("$.movieType", is("FAMILY")))
                .andExpect(jsonPath("$.movieYear", is(2020)));
    }

    @Test
    public void createMovieTest() throws Exception {
        //Given
        List<Copy> copies = new ArrayList<>();
        List<DeleteCopy> deleteCopies = new ArrayList<>();
        Movie movie = Movie.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .copies(copies)
                .deleteCopies(deleteCopies)
                .build();
        MovieDto movieDto = MovieDto.builder()
                .movieId(1L)
                .movieTitle("Movie title 1")
                .movieDirector("Movie director 1")
                .movieDescription("Movie description 1")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();

        when(movieMapper.mapToMovieDto(ArgumentMatchers.any(Movie.class))).thenReturn(movieDto);
        when(movieMapper.mapToMovie(ArgumentMatchers.any(MovieDto.class), ArgumentMatchers.eq(copies), ArgumentMatchers.eq(deleteCopies))).thenReturn(movie);
        when(movieDbService.saveMovie(ArgumentMatchers.any(Movie.class))).thenReturn(movie);

        Gson gson = new Gson();
        String jsContent = gson.toJson(movieDto);

        //When & Then
        mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept("application/json")
                .content(jsContent))
                .andExpect(status().isOk());
    }
}
