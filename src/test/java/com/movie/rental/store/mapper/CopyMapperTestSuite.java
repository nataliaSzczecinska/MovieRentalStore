package com.movie.rental.store.mapper;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.dto.CopyDto;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.enums.Type;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CopyMapperTestSuite {
    @Autowired
    private CopyMapper copyMapper;

    @Test
    public void mapToCopyTest() {
        //Given
        CopyDto copyDto = new CopyDto(1L, 1L, Status.AVAILABLE, MediaType.BLU_RAY);
        Movie movie = new Movie(1L, "Test title", "Test director", "Test description", Type.ACTION, 2000);

        //When
        Copy copy = copyMapper.mapToCopy(copyDto, movie);

        //Then
        assertEquals(1L, copy.getCopyId());
        assertEquals(1L, copy.getMovie().getMovieId());
        assertEquals("Test title", copy.getMovie().getMovieTitle());
        assertEquals(Status.AVAILABLE, copy.getCopyStatus());
        assertEquals(MediaType.BLU_RAY, copy.getMediaType());
    }

    @Test
    public void mapToCopyDtoTest() {
        //Given
        Movie movie = new Movie(1L, "Test title", "Test director", "Test description", Type.ACTION, 2000);
        Copy copy = new Copy(1L, movie, Status.AVAILABLE, MediaType.BLU_RAY);

        //When
        CopyDto copyDto = copyMapper.mapToCopyDto(copy);

        //Then
        assertEquals(1L, copyDto.getCopyId());
        assertEquals(1L, copyDto.getMovieId());
        assertEquals(Status.AVAILABLE, copy.getCopyStatus());
        assertEquals(MediaType.BLU_RAY, copy.getMediaType());
    }

    @Test
    public void mapToCopyDtoList() {
        //Given
        Movie movie1 = new Movie(1L, "Test title", "Test director", "Test description", Type.ACTION, 2000);
        Movie movie2 = new Movie(2L, "Test title", "Test director", "Test description", Type.ADVENTURE, 2000);
        Copy copy1 = new Copy(1L, movie1, Status.AVAILABLE, MediaType.BLU_RAY);
        Copy copy2 = new Copy(2L, movie1, Status.BORROWED, MediaType.DVD);
        Copy copy3 = new Copy(3L, movie2, Status.AVAILABLE, MediaType.DVD);
        List<Copy> copies = Arrays.asList(copy1, copy2, copy3);

        //When
        List<CopyDto> copyDtos = copyMapper.mapToCopyDtoList(copies);

        //Then
        assertEquals(3, copyDtos.size());
        assertEquals(1L, copyDtos.get(0).getCopyId());
        assertEquals(1L, copyDtos.get(0).getMovieId());
        assertEquals(Status.AVAILABLE, copyDtos.get(0).getCopyStatus());
        assertEquals(MediaType.BLU_RAY, copyDtos.get(0).getMediaType());
        assertEquals(2L, copyDtos.get(1).getCopyId());
        assertEquals(1L, copyDtos.get(1).getMovieId());
        assertEquals(Status.BORROWED, copyDtos.get(1).getCopyStatus());
        assertEquals(MediaType.DVD, copyDtos.get(1).getMediaType());
        assertEquals(3L, copyDtos.get(2).getCopyId());
        assertEquals(2L, copyDtos.get(2).getMovieId());
        assertEquals(Status.AVAILABLE, copyDtos.get(2).getCopyStatus());
        assertEquals(MediaType.DVD, copyDtos.get(2).getMediaType());
    }
}
