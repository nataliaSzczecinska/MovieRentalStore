package com.movie.rental.store.domain.archive;

import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.repository.MovieRepository;
import com.movie.rental.store.repository.archive.DeleteCopyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DeleteCopyTestSuite {
    @Autowired
    private DeleteCopyRepository deleteCopyRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void saveDeleteCopyTest() {
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Title Test")
                .movieDirector("Director Test")
                .movieDescription("Description Test")
                .movieType(Type.ACTION)
                .movieYear(2000)
                .build();
        movieRepository.save(movie);
        DeleteCopy deleteCopy = DeleteCopy.builder()
                .previousCopyId(1L)
                .movie(movie)
                .mediaType(MediaType.DVD)
                .deleteDate(LocalDate.of(2000, 9, 19))
                .build();

        //When
        deleteCopyRepository.save(deleteCopy);
        Long id = deleteCopy.getDeleteCopyId();
        Optional<DeleteCopy> deleteCopyOptional = deleteCopyRepository.findById(id);

        //Then
        assertTrue(deleteCopyOptional.isPresent());

        //Clean-up
        deleteCopyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getDeleteCopyByIdTest() {
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Title Test")
                .movieDirector("Director Test")
                .movieDescription("Description Test")
                .movieType(Type.ACTION)
                .movieYear(2000)
                .build();
        movieRepository.save(movie);
        DeleteCopy deleteCopy = DeleteCopy.builder()
                .previousCopyId(1L)
                .movie(movie)
                .mediaType(MediaType.DVD)
                .deleteDate(LocalDate.of(2000, 9, 19))
                .build();

        //When
        deleteCopyRepository.save(deleteCopy);
        Long id = deleteCopy.getDeleteCopyId();
        Optional<DeleteCopy> deleteCopyOptional = deleteCopyRepository.findById(id);

        //Then
        assertTrue(deleteCopyOptional.isPresent());
        assertEquals(id, deleteCopyOptional.get().getDeleteCopyId());

        //Clean-up
        deleteCopyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getAllDeleteCopiesTest() {
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Title Test")
                .movieDirector("Director Test")
                .movieDescription("Description Test")
                .movieType(Type.ACTION)
                .movieYear(2000)
                .build();
        movieRepository.save(movie);
        DeleteCopy deleteCopy1 = DeleteCopy.builder()
                .previousCopyId(1L)
                .movie(movie)
                .mediaType(MediaType.DVD)
                .deleteDate(LocalDate.of(2000, 9, 19))
                .build();
        DeleteCopy deleteCopy2 = DeleteCopy.builder()
                .previousCopyId(3L)
                .movie(movie)
                .mediaType(MediaType.DVD)
                .deleteDate(LocalDate.of(2000, 12, 15))
                .build();

        //When
        deleteCopyRepository.save(deleteCopy1);
        deleteCopyRepository.save(deleteCopy2);
        List<DeleteCopy> deleteCopies = deleteCopyRepository.findAll();

        //Then
        assertEquals(2, deleteCopies.size());

        //Clean-up
        deleteCopyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getDeleteCopyByPreviousCopyIdTest() {
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Title Test")
                .movieDirector("Director Test")
                .movieDescription("Description Test")
                .movieType(Type.ACTION)
                .movieYear(2000)
                .build();
        movieRepository.save(movie);
        DeleteCopy deleteCopy = DeleteCopy.builder()
                .previousCopyId(10L)
                .movie(movie)
                .mediaType(MediaType.DVD)
                .deleteDate(LocalDate.of(2000, 9, 19))
                .build();

        //When
        deleteCopyRepository.save(deleteCopy);
        Optional<DeleteCopy> deleteCopyOptional = deleteCopyRepository.retrieveDeleteCopyByPreviousCopyId(10L);

        //Then
        assertTrue(deleteCopyOptional.isPresent());
        assertEquals(10L, deleteCopyOptional.get().getPreviousCopyId());

        //Clean-up
        deleteCopyRepository.deleteAll();
        movieRepository.deleteAll();
    }
}
