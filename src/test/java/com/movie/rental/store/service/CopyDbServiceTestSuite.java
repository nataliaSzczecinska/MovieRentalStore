package com.movie.rental.store.service;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.repository.CopyRepository;
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
public class CopyDbServiceTestSuite {
    @Autowired
    CopyDbService copyDbService;

    @Autowired
    CopyRepository copyRepository;

    @Autowired
    MovieRepository movieRepository;

    private Movie movie1 = Movie.builder()
            .movieTitle("First Movie Title")
            .movieDirector("Director 1")
            .movieDescription("Description 1")
            .movieType(Type.COMEDY)
            .movieYear(2020)
            .build();
    private Movie movie2= Movie.builder()
            .movieTitle("Second Movie Title")
            .movieDirector("Director 2")
            .movieDescription("Description 2")
            .movieType(Type.THRILLER)
            .movieYear(2009)
            .build();

    private List<Copy> exampleCopies() {
        List<Copy> copyList = new ArrayList<>();
        copyList.add(Copy.builder()
                .movie(movie1)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.DVD)
                .build());
        copyList.add(Copy.builder()
                .movie(movie1)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.DVD)
                .build());
        copyList.add(Copy.builder()
                .movie(movie1)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.BLU_RAY)
                .build());
        copyList.add(Copy.builder()
                .movie(movie1)
                .copyStatus(Status.BORROWED)
                .mediaType(MediaType.DVD)
                .build());
        copyList.add(Copy.builder()
                .movie(movie2)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.DVD)
                .build());
        copyList.add(Copy.builder()
                .movie(movie2)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.BLU_RAY)
                .build());
        return copyList;
    }

    private List<Long> saveCopiesInDatabase(List<Copy> copies) {
        List <Long> list = new ArrayList<>();
        for (Copy copy : copies) {
            copyRepository.save(copy);
            list.add(copy.getCopyId());
        }
        return list;
    }

    @Test
    public void getAllCopiesTest() {
        //Given
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        List<Copy> copies = exampleCopies();
        saveCopiesInDatabase(copies);

        //When
        List<Copy> copyList = copyDbService.getAllCopies();

        //Then
        assertEquals(6, copyList.size());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getCopyByIdTest() {
        //Given
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        List<Copy> copies = exampleCopies();
        List<Long> ids = saveCopiesInDatabase(copies);

        //When
        Optional<Copy> copy1Optional = copyDbService.getCopyById(ids.get(2));
        Optional<Copy> copy2Optional = copyDbService.getCopyById(ids.get(5));

        //Then
        assertTrue(copy1Optional.isPresent());
        assertTrue(copy2Optional.isPresent());
        assertEquals(ids.get(2), copy1Optional.get().getCopyId());
        assertEquals(ids.get(5), copy2Optional.get().getCopyId());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void saveCopyTest() {
        //Given
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        List<Copy> copies = exampleCopies();
        saveCopiesInDatabase(copies);
        Copy copy = Copy.builder()
                .movie(movie2)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.BLU_RAY)
                .build();

        //When
        List<Copy> copyListBeforeAdd = copyDbService.getAllCopies();
        copyDbService.saveCopy(copy);
        Long copyId = copy.getCopyId();
        List<Copy> copyListAfterAdd = copyDbService.getAllCopies();

        //Then
        assertEquals(6, copyListBeforeAdd.size());
        assertEquals(7, copyListAfterAdd.size());
        assertEquals(copyId, copyListAfterAdd.get(copyListAfterAdd.size() - 1).getCopyId());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void deleteCopyTest() {
        //Given
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        List<Copy> copies = exampleCopies();
        List<Long> ids = saveCopiesInDatabase(copies);

        //When
        List<Copy> copyListBeforeDelete = copyDbService.getAllCopies();
        Optional<Copy> copyOptionalBeforeDelete = copyDbService.getCopyById(ids.get(2));
        copyDbService.deleteCopyById(ids.get(2));
        Optional<Copy> copyOptionalAfterDelete = copyDbService.getCopyById(ids.get(2));
        List<Copy> copyListAfterDelete = copyDbService.getAllCopies();

        //Then
        assertEquals(6, copyListBeforeDelete.size());
        assertEquals(5, copyListAfterDelete.size());
        assertTrue(copyOptionalBeforeDelete.isPresent());
        assertFalse(copyOptionalAfterDelete.isPresent());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }
}
