package com.movie.rental.store.service.archive;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.exception.CopyNotFoundException;
import com.movie.rental.store.facade.CopyFacade;
import com.movie.rental.store.repository.CopyRepository;
import com.movie.rental.store.repository.MovieRepository;
import com.movie.rental.store.repository.archive.DeleteCopyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DeleteCopyDbServiceTestSuite {
    @Autowired
    private DeleteCopyDbService deleteCopyDbService;

    @Autowired
    private DeleteCopyRepository deleteCopyRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private CopyFacade copyFacade;

    private Movie movie = Movie.builder()
            .movieTitle("Movie title for test")
            .movieDirector("Movie director for test")
            .movieDescription("Movie description for test")
            .movieYear(2000)
            .movieType(Type.COMEDY)
            .build();

    private List<DeleteCopy> exampleDeleteCopy() {
        List<DeleteCopy> deleteCopies = new ArrayList<>();
        deleteCopies.add(DeleteCopy.builder()
                .previousCopyId(1L)
                .movie(movie)
                .mediaType(MediaType.DVD)
                .deleteDate(LocalDate.of(2019, 9, 8))
                .build());
        deleteCopies.add(DeleteCopy.builder()
                .previousCopyId(4L)
                .movie(movie)
                .mediaType(MediaType.BLU_RAY)
                .deleteDate(LocalDate.of(2020, 4, 19))
                .build());
        return deleteCopies;
    }

    private List<Long> saveDeleteCopyInDatabase(List<DeleteCopy> deleteCopies) {
        List<Long> ids = new ArrayList<>();

        for (DeleteCopy deleteCopy : deleteCopies) {
            deleteCopyRepository.save(deleteCopy);
            ids.add(deleteCopy.getDeleteCopyId());
        }

        return ids;
    }

    @Test
    public void getAllDeleteCopiesTest() {
        //Given
        movieRepository.save(movie);
        List<DeleteCopy> deleteCopies = exampleDeleteCopy();
        saveDeleteCopyInDatabase(deleteCopies);

        //When
        List<DeleteCopy> deleteCopyList = deleteCopyDbService.getAllDeletedCopies();

        //Then
        assertEquals(2, deleteCopyList.size());

        //Clean-up
        deleteCopyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getDeleteCopyByIdTest() {
        //Given
        movieRepository.save(movie);
        List<DeleteCopy> deleteCopies = exampleDeleteCopy();
        List<Long> ids = saveDeleteCopyInDatabase(deleteCopies);

        //When
        Optional<DeleteCopy> deleteCopy1Optional = deleteCopyDbService.getDeleteCopyById(ids.get(1));
        Optional<DeleteCopy> deleteCopy2Optional = deleteCopyDbService.getDeleteCopyById(6L);

        //Then
        assertEquals(ids.get(1), deleteCopy1Optional.get().getDeleteCopyId());
        assertTrue(deleteCopy1Optional.isPresent());
        assertFalse(deleteCopy2Optional.isPresent());

        //Clean-up
        deleteCopyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void saveDeleteCopyTest() {
        //Given
        movieRepository.save(movie);
        List<DeleteCopy> deleteCopies = exampleDeleteCopy();
        List<Long> ids = saveDeleteCopyInDatabase(deleteCopies);
        DeleteCopy deleteCopy = DeleteCopy.builder()
                .previousCopyId(6L)
                .movie(movie)
                .mediaType(MediaType.DVD)
                .deleteDate(LocalDate.of(2003, 10,13))
                .build();

        //When
        List<DeleteCopy> deleteCopiesBeforeAdd = deleteCopyDbService.getAllDeletedCopies();
        deleteCopyDbService.saveDeletedCopy(deleteCopy);
        Long id = deleteCopy.getDeleteCopyId();
        Optional<DeleteCopy> deleteCopyOptional = deleteCopyDbService.getDeleteCopyById(id);
        List<DeleteCopy> deleteCopiesAfterAdd = deleteCopyDbService.getAllDeletedCopies();


        //Then
        assertEquals(id, deleteCopyOptional.get().getDeleteCopyId());
        assertTrue(deleteCopyOptional.isPresent());
        assertEquals(2, deleteCopiesBeforeAdd.size());
        assertEquals(3, deleteCopiesAfterAdd.size());

        //Clean-up
        deleteCopyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void searchDeleteCopyByPreviousCopyId() throws CopyNotFoundException {
        //Given
        movieRepository.save(movie);
        Copy copy1 = Copy.builder()
                .movie(movie)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.DVD)
                .build();
        Copy copy2 = Copy.builder()
                .movie(movie)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.DVD)
                .build();
        copyRepository.save(copy1);
        copyRepository.save(copy2);
        Long copy1Id = copy1.getCopyId();
        Long copy2Id = copy2.getCopyId();
        List<DeleteCopy> deleteCopies = exampleDeleteCopy();
        saveDeleteCopyInDatabase(deleteCopies);
        copyFacade.deleteCopy(copy1Id);
        copyFacade.deleteCopy(copy2Id);

        //When
        List<DeleteCopy> deleteCopiesFromDb = deleteCopyDbService.getAllDeletedCopies();
        Optional<DeleteCopy> deleteCopy1Optional = deleteCopyDbService.searchDeleteCopyByPreviousCopyId(copy1Id);
        Optional<DeleteCopy> deleteCopy2Optional = deleteCopyDbService.searchDeleteCopyByPreviousCopyId(copy2Id);

        //Then
        assertTrue(deleteCopy1Optional.isPresent());
        assertTrue(deleteCopy2Optional.isPresent());
        assertEquals(4, deleteCopiesFromDb.size());

        //Clean-up
        deleteCopyRepository.deleteAll();
        movieRepository.deleteAll();
    }
}
