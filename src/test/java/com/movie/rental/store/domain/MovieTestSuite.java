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
        Movie movie = new Movie("Title Test", "Director Test", "Description Test", Type.ACTION, 2000);

        //When
        movieRepository.save(movie);
        Long movieId = movie.getMovieId();
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        //Then
        System.out.println("The movie " + movieId);
        assertTrue(movieOptional.isPresent());

        //Clean-up
        movieRepository.deleteById(movieId);
    }

    @Test
    public void findAllMoviesTest() {
        //Given
        Movie movie1 = new Movie("Title Test 1", "Director Test 1", "Description Test 1", Type.ACTION, 2000);
        Movie movie2 = new Movie("Title Test 2", "Director Test 2", "Description Test 2", Type.FANTASY, 2010);
        Movie movie3 = new Movie("Title Test 3", "Director Test 3", "Description Test 3", Type.HORROR, 2007);

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
        Movie movie = new Movie("Title Test", "Director Test", "Description Test", Type.ACTION, 2000);

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
