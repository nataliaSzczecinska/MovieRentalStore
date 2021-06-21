package com.movie.rental.store.domain;

import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MovieTestSuite {
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void saveMovieTest() {
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Title Test")
                .movieDirector("Director Test")
                .movieDescription("Description Test")
                .movieType(Type.ACTION)
                .movieYear(2000)
                .build();

        //When
        movieRepository.save(movie);
        Long movieId = movie.getMovieId();
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        //Then
        assertTrue(movieOptional.isPresent());

        //Clean-up
        movieRepository.deleteById(movieId);
    }

    @Test
    public void findAllMoviesTest() {
        //Given
        Movie movie1 = Movie.builder()
                .movieTitle("Title Test 1")
                .movieDirector("Director Test 1")
                .movieDescription("Description Test 1")
                .movieType(Type.ACTION)
                .movieYear(2000)
                .build();
        Movie movie2 = Movie.builder()
                .movieTitle("Title Test 2")
                .movieDirector("Director Test 2")
                .movieDescription("Description Test 2")
                .movieType(Type.FANTASY)
                .movieYear(2010)
                .build();
        Movie movie3 = Movie.builder()
                .movieTitle("Title Test 3")
                .movieDirector("Director Test 3")
                .movieDescription("Description Test 3")
                .movieType(Type.HORROR)
                .movieYear(2007)
                .build();

        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);
        Long movieId1 = movie1.getMovieId();
        Long movieId2 = movie2.getMovieId();
        Long movieId3 = movie3.getMovieId();

        //When
        List<Movie> movieList = movieRepository.findAll();

        //Then
        assertEquals(3, movieList.size());

        //Clean-up
        movieRepository.deleteById(movieId1);
        movieRepository.deleteById(movieId2);
        movieRepository.deleteById(movieId3);

        System.out.println(movieRepository.findAll().isEmpty());
    }

    @Test
    public void findMovieByIdTest() {
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Title Test 1")
                .movieDirector("Director Test 1")
                .movieDescription("Description Test 1")
                .movieType(Type.ACTION)
                .movieYear(2000)
                .build();

        //When
        movieRepository.save(movie);
        Long movieId = movie.getMovieId();
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        //Then
        assertEquals(movieId, movieOptional.get().getMovieId());

        //Clean-up
        movieRepository.deleteById(movieId);
    }
}
