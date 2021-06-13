package com.movie.rental.store.mapper.archive;

import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.dto.DeleteCopyDto;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Type;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DeleteCopyMapperTestSuite {
    @Autowired
    private DeleteCopyMapper deleteCopyMapper;

    @Test
    public void mapToDeleteCopyTest() {
        //Given
        DeleteCopyDto deleteCopyDto = new DeleteCopyDto(1L, 3L, 4L, MediaType.DVD, LocalDate.of(2000, 3, 12));
        Movie movie = new Movie(deleteCopyDto.getMovieId(), "Movie title", "Movie director", "Movie description", Type.ACTION, 1990);

        //When
        DeleteCopy deleteCopy = deleteCopyMapper.mapToDeleteCopy(deleteCopyDto, movie);

        //Then
        assertEquals(1L, deleteCopy.getDeleteCopyId());
        assertEquals(3L, deleteCopy.getPreviousCopyId());
        assertEquals(4L, deleteCopy.getMovie().getMovieId());
        assertEquals(MediaType.DVD, deleteCopy.getMediaType());
        assertEquals(LocalDate.of(2000, 3, 12), deleteCopy.getDeleteDate());
    }

    @Test
    public void mapToDeleteCopyDtoTest() {
        //Given
        Movie movie = new Movie(4L, "Movie title", "Movie director", "Movie description", Type.ACTION, 1990);
        DeleteCopy deleteCopy = new DeleteCopy(1L, 3L, movie, MediaType.DVD, LocalDate.of(2000, 3, 12));

        //When
        DeleteCopyDto deleteCopyDto = deleteCopyMapper.mapToDeleteCopyDto(deleteCopy);

        //Then
        assertEquals(1L, deleteCopyDto.getDeleteCopyId());
        assertEquals(3L, deleteCopyDto.getPreviousCopyId());
        assertEquals(4L, deleteCopyDto.getMovieId());
        assertEquals(MediaType.DVD, deleteCopy.getMediaType());
        assertEquals(LocalDate.of(2000, 3, 12), deleteCopy.getDeleteDate());
    }

    @Test
    public void mapToDeleteCopyDtoListTest() {
        //Given
        Movie movie1 = new Movie(1L, "Movie title", "Movie director", "Movie description", Type.ACTION, 1990);
        Movie movie2 = new Movie(2L, "Movie title", "Movie director", "Movie description", Type.ACTION, 1990);
        DeleteCopy deleteCopy1 = new DeleteCopy(1L, 3L, movie1, MediaType.DVD, LocalDate.of(2001, 3, 12));
        DeleteCopy deleteCopy2 = new DeleteCopy(2L, 4L, movie1, MediaType.BLU_RAY, LocalDate.of(2002, 3, 12));
        DeleteCopy deleteCopy3 = new DeleteCopy(3L, 7L, movie2, MediaType.DVD, LocalDate.of(2003, 3, 12));
        List<DeleteCopy> deleteCopies = Arrays.asList(deleteCopy1, deleteCopy2, deleteCopy3);

        //When
        List<DeleteCopyDto> deleteCopyDtoList = deleteCopyMapper.mapToDeleteCopyDtoList(deleteCopies);

        //Then
        assertEquals(3, deleteCopyDtoList.size());
        assertEquals(1L, deleteCopyDtoList.get(0).getDeleteCopyId());
        assertEquals(3L, deleteCopyDtoList.get(0).getPreviousCopyId());
        assertEquals(1L, deleteCopyDtoList.get(0).getMovieId());
        assertEquals(MediaType.DVD, deleteCopyDtoList.get(0).getMediaType());
        assertEquals(LocalDate.of(2001, 3, 12), deleteCopyDtoList.get(0).getDeleteDate());
        assertEquals(2L, deleteCopyDtoList.get(1).getDeleteCopyId());
        assertEquals(4L, deleteCopyDtoList.get(1).getPreviousCopyId());
        assertEquals(1L, deleteCopyDtoList.get(1).getMovieId());
        assertEquals(MediaType.BLU_RAY, deleteCopyDtoList.get(1).getMediaType());
        assertEquals(LocalDate.of(2002, 3, 12), deleteCopyDtoList.get(1).getDeleteDate());
        assertEquals(3L, deleteCopyDtoList.get(2).getDeleteCopyId());
        assertEquals(7L, deleteCopyDtoList.get(2).getPreviousCopyId());
        assertEquals(2L, deleteCopyDtoList.get(2).getMovieId());
        assertEquals(MediaType.DVD, deleteCopyDtoList.get(2).getMediaType());
        assertEquals(LocalDate.of(2003, 3, 12), deleteCopyDtoList.get(2).getDeleteDate());
    }
}
