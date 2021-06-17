package com.movie.rental.store.facade;

import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.dto.MovieDto;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.exception.MovieNotFoundException;
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
public class MovieFacadeTestSuite {
    @Autowired
    private MovieFacade movieFacade;

    @Autowired
    private MovieRepository movieRepository;

    private List<Movie> movies = movieExamples();

    private List<Movie> movieExamples() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("Title 1 abcd",
                "Director 1 snhdxyzks",
                "Description 1",
                Type.COMEDY,
                2004));
        list.add(new Movie("Title 2",
                "Director 2 hxyzksa",
                "Description 2",
                Type.ACTION,
                2008));
        list.add(new Movie("Title 3 abbcdeabcd",
                "Director 3",
                "Description 3 new test",
                Type.ANIMATED,
                2018));
        list.add(new Movie("Title 4",
                "Director 4",
                "Description 4 new test",
                Type.FAMILY,
                2008));
        return list;
    }

    private List<Long> saveMoviesInDatabase(List<Movie> movieList) {
        List<Long> idLists = new ArrayList<>();
        for (Movie movie : movieList) {
            movieRepository.save(movie);
            idLists.add(movie.getMovieId());
        }
        return idLists;
    }

    @Test
    public void getAllMoviesTest() {
        //Given
        saveMoviesInDatabase(movies);

        //When
        List<MovieDto> movieDtoList = movieFacade.getAllMovies();

        //Then
        assertEquals(4, movieDtoList.size());

        //Clean-up
        movieRepository.deleteAll();
    }

    @Test
    public void getMovieByIdTest() throws MovieNotFoundException {
        //Given
        List<Long> ids = saveMoviesInDatabase(movies);

        //When
        MovieDto movieDto = movieFacade.getMovieById(ids.get(1));

        //Then
        assertEquals(ids.get(1), movieDto.getMovieId());

        //Clean-up
        movieRepository.deleteAll();
    }

    @Test
    public void getMovieByTitleTest() {
        //Given
        saveMoviesInDatabase(movies);

        //When
        List<MovieDto> movieDtoList = movieFacade.getMoviesByTitle("abc");

        //Then
        assertEquals(2, movieDtoList.size());

        //Clean-up
        movieRepository.deleteAll();
    }

    @Test
    public void getMovieByDirectorTest() {
        //Given
        saveMoviesInDatabase(movies);

        //When
        List<MovieDto> movieDtoList = movieFacade.getMoviesByDirector("xyz");

        //Then
        assertEquals(2, movieDtoList.size());

        //Clean-up
        movieRepository.deleteAll();
    }

    @Test
    public void getMovieByDescriptionTest() {
        //Given
        saveMoviesInDatabase(movies);

        //When
        List<MovieDto> movieDtoList = movieFacade.getMoviesByDescription("test");

        //Then
        assertEquals(2, movieDtoList.size());

        //Clean-up
        movieRepository.deleteAll();
    }

    @Test
    public void getMovieByYearTest() {
        //Given
        saveMoviesInDatabase(movies);

        //When
        List<MovieDto> movieDtoList = movieFacade.getMoviesByYear(2008);

        //Then
        assertEquals(2, movieDtoList.size());

        //Clean-up
        movieRepository.deleteAll();
    }

    @Test
    public void getMovieByTypeTest() {
        //Given
        saveMoviesInDatabase(movies);

        //When
        List<MovieDto> movieDtoList = movieFacade.getMoviesByType("FAMILY");

        //Then
        assertEquals(1, movieDtoList.size());

        //Clean-up
        movieRepository.deleteAll();
    }

    @Test
    public void createMovieTest() {
        //Given
        saveMoviesInDatabase(movies);
        MovieDto movieDto = MovieDto.builder()
                .movieTitle("Save test movie")
                .movieDirector("Test director")
                .movieDescription("Test description")
                .movieType(Type.FAMILY)
                .movieYear(2021)
                .build();

        //When
        List<MovieDto> movieListDtoBeforeAdd = movieFacade.getAllMovies();
        movieFacade.createMovie(movieDto);
        List<MovieDto> movieDtoListAfterAdd = movieFacade.getAllMovies();

        //Then
        assertEquals(4, movieListDtoBeforeAdd.size());
        assertEquals(5, movieDtoListAfterAdd.size());

        //Clean-up
        movieRepository.deleteAll();
    }

    @Test
    public void updateMovieTest() throws MovieNotFoundException {
        //Given
        List<Long> ids = saveMoviesInDatabase(movies);
        MovieDto movieDto = MovieDto.builder()
                .movieId(ids.get(1))
                .movieTitle("Update test movie")
                .movieDirector("Test director")
                .movieDescription("Test description")
                .movieType(Type.FAMILY)
                .movieYear(2021)
                .build();

        //When
        MovieDto movieListDtoBeforeUpdate = movieFacade.getMovieById(ids.get(1));
        MovieDto movieDtoListAfterUpdate = movieFacade.updateMovie(movieDto);

        //Then
        assertEquals(ids.get(1), movieListDtoBeforeUpdate.getMovieId());
        assertEquals("Title 2", movieListDtoBeforeUpdate.getMovieTitle());
        assertEquals("Director 2 hxyzksa", movieListDtoBeforeUpdate.getMovieDirector());
        assertEquals("Description 2", movieListDtoBeforeUpdate.getMovieDescription());
        assertEquals(Type.ACTION, movieListDtoBeforeUpdate.getMovieType());
        assertEquals(2008, movieListDtoBeforeUpdate.getMovieYear());
        assertEquals(ids.get(1), movieDtoListAfterUpdate.getMovieId());
        assertEquals("Update test movie", movieDtoListAfterUpdate.getMovieTitle());
        assertEquals("Test director", movieDtoListAfterUpdate.getMovieDirector());
        assertEquals("Test description", movieDtoListAfterUpdate.getMovieDescription());
        assertEquals(Type.FAMILY, movieDtoListAfterUpdate.getMovieType());
        assertEquals(2021, movieDtoListAfterUpdate.getMovieYear());

        //Clean-up
        movieRepository.deleteAll();
    }
}
