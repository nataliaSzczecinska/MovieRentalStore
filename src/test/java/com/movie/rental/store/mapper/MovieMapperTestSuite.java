package com.movie.rental.store.mapper;

import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.dto.MovieDto;
import com.movie.rental.store.domain.enums.Type;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MovieMapperTestSuite {
    @Autowired
    private MovieMapper movieMapper;

    @Test
    public void mapToMovieTest(){
        //Given
        MovieDto movieDto = new MovieDto(1L, "Test title", "Test director", "Test description", Type.ACTION, 2000);

        //When
        Movie movie = movieMapper.mapToMovie(movieDto, null, null);

        //Then
        assertEquals(1L, movie.getMovieId());
        assertEquals("Test title", movie.getMovieTitle());
        assertEquals("Test director", movie.getMovieDirector());
        assertEquals("Test description", movie.getMovieDescription());
        assertEquals(Type.ACTION, movie.getMovieType());
        assertEquals(2000, movie.getMovieYear());
    }

    @Test
    public void mapToMovieDtoTest(){
        //Given
        Movie movie = new Movie(1L, "Test title", "Test director", "Test description", Type.ACTION, 2000);

        //When
        MovieDto movieDto = movieMapper.mapToMovieDto(movie);

        //Then
        assertEquals(1L, movieDto.getMovieId());
        assertEquals("Test title", movieDto.getMovieTitle());
        assertEquals("Test director", movieDto.getMovieDirector());
        assertEquals("Test description", movieDto.getMovieDescription());
        assertEquals(Type.ACTION, movieDto.getMovieType());
        assertEquals(2000, movieDto.getMovieYear());
    }

    @Test
    public void mapToMovieDtoListTest(){
        //Given
        Movie movie1 = new Movie(1L, "Test title", "Test director", "Test description", Type.ACTION, 2000);
        Movie movie2 = new Movie(2L, "Test title", "Test director", "Test description", Type.ADVENTURE, 2000);
        Movie movie3 = new Movie(3L, "Test title", "Test director", "Test description", Type.HORROR, 2000);
        List<Movie> movies = Arrays.asList(movie1, movie2, movie3);

        //When
        List<MovieDto> moviesDto = movieMapper.mapToMovieDtoList(movies);

        //Then
        assertEquals(3, moviesDto.size());
        assertEquals(1L, moviesDto.get(0).getMovieId());
        assertEquals(2L, moviesDto.get(1).getMovieId());
        assertEquals(3L, moviesDto.get(2).getMovieId());
        assertEquals(Type.ACTION, moviesDto.get(0).getMovieType());
        assertEquals(Type.ADVENTURE, moviesDto.get(1).getMovieType());
        assertEquals(Type.HORROR, moviesDto.get(2).getMovieType());
    }
}
