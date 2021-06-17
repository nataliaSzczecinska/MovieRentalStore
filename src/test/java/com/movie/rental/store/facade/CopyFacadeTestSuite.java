package com.movie.rental.store.facade;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.dto.CopyDto;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.exception.CopyNotFoundException;
import com.movie.rental.store.exception.MovieNotFoundException;
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
public class CopyFacadeTestSuite {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private CopyFacade copyFacade;

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
                .copyStatus(Status.BORROWED)
                .mediaType(MediaType.BLU_RAY)
                .build());
        return copyList;
    }

    private List<Long> saveCopiesInDatabase(List<Copy> copies) {
        movieRepository.save(movie1);
        movieRepository.save(movie2);
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
        List<CopyDto> copyDtoList = copyFacade.getAllCopies();

        //Then
        assertEquals(6, copyDtoList.size());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getCopyByIdTest() throws CopyNotFoundException {
        //Given
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        List<Copy> copies = exampleCopies();
        List<Long> ids = saveCopiesInDatabase(copies);

        //When
        CopyDto copyDto1 = copyFacade.getCopyById(ids.get(2));
        CopyDto copyDto2 = copyFacade.getCopyById(ids.get(5));

        //Then
        assertEquals(ids.get(2), copyDto1.getCopyId());
        assertEquals(ids.get(5), copyDto2.getCopyId());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getCopyByMovieIdTest() {
        //Given
        List<Copy> copies = exampleCopies();
        saveCopiesInDatabase(copies);
        Long idMovie1 = movie1.getMovieId();
        Long idMovie2 = movie2.getMovieId();

        //When
        List<CopyDto> copyDtoList1 = copyFacade.getAllCopiesByMovie(idMovie1);
        List<CopyDto> copyDtoList2 = copyFacade.getAllCopiesByMovie(idMovie2);

        //Then
        assertEquals(4, copyDtoList1.size());
        assertEquals(2, copyDtoList2.size());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getAvailableCopiesTest() {
        //Given
        List<Copy> copies = exampleCopies();
        saveCopiesInDatabase(copies);

        //When
        List<CopyDto> copyDtoList = copyFacade.getAvailableCopies();

        //Then
        assertEquals(4, copyDtoList.size());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getAvailableCopiesByMovieTest() {
        //Given
        List<Copy> copies = exampleCopies();
        saveCopiesInDatabase(copies);
        Long idMovie1 = movie1.getMovieId();
        Long idMovie2 = movie2.getMovieId();

        //When
        List<CopyDto> copyDtoList1 = copyFacade.getAvailableCopiesByMovie(idMovie1);
        List<CopyDto> copyDtoList2 = copyFacade.getAvailableCopiesByMovie(idMovie2);

        //Then
        assertEquals(3, copyDtoList1.size());
        assertEquals(1, copyDtoList2.size());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getDVDCopiesByMovieTest() {
        //Given
        List<Copy> copies = exampleCopies();
        saveCopiesInDatabase(copies);
        Long idMovie1 = movie1.getMovieId();
        Long idMovie2 = movie2.getMovieId();

        //When
        List<CopyDto> copyDtoList1 = copyFacade.getDVDCopiesByMovie(idMovie1);
        List<CopyDto> copyDtoList2 = copyFacade.getDVDCopiesByMovie(idMovie2);

        //Then
        assertEquals(3, copyDtoList1.size());
        assertEquals(1, copyDtoList2.size());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getBluRayCopiesByMovieTest() {
        //Given
        List<Copy> copies = exampleCopies();
        saveCopiesInDatabase(copies);
        Long idMovie1 = movie1.getMovieId();
        Long idMovie2 = movie2.getMovieId();

        //When
        List<CopyDto> copyDtoList1 = copyFacade.getBluRayCopiesByMovie(idMovie1);
        List<CopyDto> copyDtoList2 = copyFacade.getBluRayCopiesByMovie(idMovie2);

        //Then
        assertEquals(1, copyDtoList1.size());
        assertEquals(1, copyDtoList2.size());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getDVDAvailableCopiesByMovieTest() {
        //Given
        List<Copy> copies = exampleCopies();
        saveCopiesInDatabase(copies);
        Long idMovie1 = movie1.getMovieId();
        Long idMovie2 = movie2.getMovieId();

        //When
        List<CopyDto> copyDtoList1 = copyFacade.getDVDAvailableCopiesByMovie(idMovie1);
        List<CopyDto> copyDtoList2 = copyFacade.getDVDAvailableCopiesByMovie(idMovie2);

        //Then
        assertEquals(2, copyDtoList1.size());
        assertEquals(1, copyDtoList2.size());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getBluRayAvailableCopiesByMovieTest() {
        //Given
        List<Copy> copies = exampleCopies();
        saveCopiesInDatabase(copies);
        Long idMovie1 = movie1.getMovieId();
        Long idMovie2 = movie2.getMovieId();

        //When
        List<CopyDto> copyDtoList1 = copyFacade.getBluRayAvailableCopiesByMovie(idMovie1);
        List<CopyDto> copyDtoList2 = copyFacade.getBluRayAvailableCopiesByMovie(idMovie2);

        //Then
        assertEquals(1, copyDtoList1.size());
        assertEquals(0, copyDtoList2.size());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void createCopyTest() throws MovieNotFoundException {
        //Given
        List<Copy> copies = exampleCopies();
        saveCopiesInDatabase(copies);
        CopyDto copyDto = CopyDto.builder()
                .movieId(movie2.getMovieId())
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.BLU_RAY)
                .build();

        //When
        List<CopyDto> copyDtoListBeforeAdd = copyFacade.getAllCopies();
        copyFacade.createCopy(copyDto);
        List<CopyDto> copyDtoListAfterAdd = copyFacade.getAllCopies();

        //Then
        assertEquals(6, copyDtoListBeforeAdd.size());
        assertEquals(7, copyDtoListAfterAdd.size());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void updateCopyTest() throws MovieNotFoundException, CopyNotFoundException {
        //Given
        List<Copy> copies = exampleCopies();
        List<Long> ids = saveCopiesInDatabase(copies);
        CopyDto copyDto = CopyDto.builder()
                .copyId(ids.get(5))
                .movieId(movie2.getMovieId())
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.BLU_RAY)
                .build();

        //When
        CopyDto copyDtoBeforeUpdate = copyFacade.getCopyById(ids.get(5));
        copyFacade.updateCopy(copyDto);
        CopyDto copyDtoAfterUpdate = copyFacade.getCopyById(ids.get(5));

        //Then
        assertEquals(ids.get(5), copyDtoBeforeUpdate.getCopyId());
        assertEquals(Status.BORROWED, copyDtoBeforeUpdate.getCopyStatus());
        assertEquals(ids.get(5), copyDtoAfterUpdate.getCopyId());
        assertEquals(Status.AVAILABLE, copyDtoAfterUpdate.getCopyStatus());

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void deleteCopyTest() throws CopyNotFoundException {
        //Given
        List<Copy> copies = exampleCopies();
        List<Long> ids = saveCopiesInDatabase(copies);

        //When
        List<CopyDto> copyListDtoBeforeDelete = copyFacade.getAllCopies();
        CopyDto copyDto = copyFacade.getCopyById(ids.get(2));
        copyFacade.deleteCopy(ids.get(2));
        List<CopyDto> copyDtoListAfterDelete = copyFacade.getAllCopies();

        //Then
        assertEquals(6, copyListDtoBeforeDelete.size());
        assertEquals(5, copyDtoListAfterDelete.size());
        assertNotNull(copyDto);

        //Clean-up
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }
}
