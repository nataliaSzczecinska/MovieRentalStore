package com.movie.rental.store.domain;

import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.repository.CopyRepository;
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
public class CopyTestSuite {
    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void saveCopyTest(){
        //Given
        Movie movie = new Movie("Title Test", "Director Test", "Description Test", Type.ACTION, 2000);
        Copy copy = new Copy(movie, Status.AVAILABLE, MediaType.DVD);

        //When
        movieRepository.save(movie);
        Long movieId = movie.getMovieId();
        copyRepository.save(copy);
        Long copyId = copy.getCopyId();
        Optional<Copy> copyOptional = copyRepository.findById(copyId);

        //Then
        assertTrue(copyOptional.isPresent());

        //Clean-up
        copyRepository.deleteById(copyId);
        movieRepository.deleteById(movieId);
    }

    @Test
    public void deleteCopyTest(){
        //Given
        Movie movie = new Movie("Title Test", "Director Test", "Description Test", Type.ACTION, 2000);
        Copy copy = new Copy(movie, Status.AVAILABLE, MediaType.DVD);

        //When
        movieRepository.save(movie);
        Long movieId = movie.getMovieId();
        copyRepository.save(copy);
        Long copyId = copy.getCopyId();
        Optional<Copy> copyOptional1 = copyRepository.findById(copyId);
        copyRepository.deleteById(copyId);
        Optional<Copy> copyOptional2 = copyRepository.findById(copyId);

        //Then
        assertTrue(copyOptional1.isPresent());
        assertFalse(copyOptional2.isPresent());

        //Clean-up
        movieRepository.deleteById(movieId);
    }

    @Test
    public void findCopyByIdTest(){
        //Given
        Movie movie = new Movie("Title Test", "Director Test", "Description Test", Type.ACTION, 2000);
        Copy copy = new Copy(movie, Status.AVAILABLE, MediaType.DVD);


        //When
        movieRepository.save(movie);
        Long movieId = movie.getMovieId();
        copyRepository.save(copy);
        Long copyId = copy.getCopyId();
        Optional<Copy> copyOptional = copyRepository.findById(copyId);

        //Then
        assertTrue(copyOptional.isPresent());

        //Clean-up
        copyRepository.deleteById(copyId);
        movieRepository.deleteById(movieId);
    }

    @Test
    public void movieAndCopyConnectionTest(){
        //Given
        Movie movie = new Movie("Title Test", "Director Test", "Description Test", Type.ACTION, 2000);
        Copy copy1 = new Copy(movie, Status.AVAILABLE, MediaType.DVD);
        Copy copy2 = new Copy(movie, Status.BORROWED, MediaType.BLU_RAY);
        movie.setCopies(Arrays.asList(copy1, copy2));

        //When
        movieRepository.save(movie);
        Long movieId = movie.getMovieId();
        copyRepository.save(copy1);
        Long copy1Id = copy1.getCopyId();
        copyRepository.save(copy1);
        Long copy2Id = copy2.getCopyId();
        Optional<Copy> copy1Optional = copyRepository.findById(copy1Id);
        Optional<Copy> copy2Optional = copyRepository.findById(copy1Id);
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        //Then
        assertTrue(copy1Optional.isPresent());
        assertEquals(movie, copy1Optional.get().getMovie());
        assertEquals(movie.getMovieId(), copy1Optional.get().getMovie().getMovieId());
        assertTrue(copy2Optional.isPresent());
        assertEquals(movie, copy2Optional.get().getMovie());
        assertEquals(movie.getMovieId(), copy2Optional.get().getMovie().getMovieId());
        assertEquals(2, movie.getCopies().size());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteById(movieId);
    }
}
