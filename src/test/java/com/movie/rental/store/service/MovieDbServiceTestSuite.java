package com.movie.rental.store.service;

import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.exception.MovieNotFoundException;
import com.movie.rental.store.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.transaction.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MovieDbServiceTestSuite {
    @Autowired
    private MovieDbService movieDbService;

    @Autowired
    private MovieRepository movieRepository;

    private List<Movie> movies = movieExamples();

    private List<Movie> movieExamples() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("Nigdy w życiu",
                "Ryszard Zatorski",
                "Opis abcdefg",
                Type.COMEDY,
                2004));
        list.add(new Movie("Iron Man",
                "Jon Favreau",
                "abdefkoas",
                Type.ACTION,
                2008));
        list.add(new Movie("Ojciec Chrzestny",
                "Francis Ford Coppola",
                "kxiabcd",
                Type.DRAMA_MOVIE,
                1972));
        list.add(new Movie("Cudowny chłopak",
                "Stephen Chbosky",
                "fafiuhdfajkbdfiaudfbabcddi wijaonnduanksjnd asj",
                Type.DRAMA_MOVIE,
                2017));
        list.add(new Movie("Labirynt Fauna",
                "Guillermo del Toro",
                "Opis xyz",
                Type.FANTASY,
                2006));
        list.add(new Movie("Kształt wody",
                "Guillermo del Toro",
                "opis testowy",
                Type.DRAMA_MOVIE,
                2017));
        list.add(new Movie("Shrek",
                "Andrew Adamson and Vicky Jenson",
                "okislabcdasjdi",
                Type.ANIMATED,
                2001));
        list.add(new Movie("Mroczne wody",
                "Todd Haynes",
                "non",
                Type.ANIMATED,
                2019));
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
        List<Movie> movieList = movieDbService.getAllMovie();

        //Then
        assertEquals(8, movieList.size());

        //Clean-up
        movieRepository.deleteAll();
    }

    @Test
    public void getMovieByIdTest() throws MovieNotFoundException {
        //Given
        List<Long> ids = saveMoviesInDatabase(movies);

        //When
        Movie movie1 = movieDbService.getMovieById(ids.get(3)).orElseThrow(MovieNotFoundException::new);
        Movie movie2 = movieDbService.getMovieById(ids.get(5)).orElseThrow(MovieNotFoundException::new);

        //Then
        assertEquals(ids.get(3), movie1.getMovieId());
        assertEquals(ids.get(5), movie2.getMovieId());

        //Clean-up
        movieRepository.deleteAll();
    }

    @Test
    public void getMovieByTitleTest() {
        //Given
        saveMoviesInDatabase(movies);

        //When
        List<Movie> movieList = movieDbService.searchMovieByTitle("wod");

        //Then
        assertEquals(2, movieList.size());

        //Clean-up
        movieRepository.deleteAll();
    }

    @Test
    public void getMovieByDirectorTest() {
        //Given
        saveMoviesInDatabase(movies);

        //When
        List<Movie> movieList = movieDbService.searchMovieByDirector("ler");

        //Then
        assertEquals(2, movieList.size());

        //Clean-up
        movieRepository.deleteAll();
    }

    @Test
    public void getMovieByDescriptionTest() {
        //Given
        saveMoviesInDatabase(movies);

        //When
        List<Movie> movieList = movieDbService.searchMovieByDescription("abcd");

        //Then
        assertEquals(4, movieList.size());

        //Clean-up
        movieRepository.deleteAll();
    }

    @Test
    public void saveMovieTest() {
        //Given
        saveMoviesInDatabase(movies);
        Movie movie = new Movie("Save test movie",
                "Test director",
                "Test description",
                Type.FAMILY,
                2021);

        //When
        List<Movie> movieListBeforeAdd = movieDbService.getAllMovie();
        movieDbService.saveMovie(movie);
        Long movieId = movie.getMovieId();
        List<Movie> movieListAfterAdd = movieDbService.getAllMovie();

        //Then
        assertEquals(8, movieListBeforeAdd.size());
        assertEquals(9, movieListAfterAdd.size());
        assertEquals(movieId, movieListAfterAdd.get(movieListAfterAdd.size() - 1).getMovieId());

        //Clean-up
        movieRepository.deleteAll();
    }
}
