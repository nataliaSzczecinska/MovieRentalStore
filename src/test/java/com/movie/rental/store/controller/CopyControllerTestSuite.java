package com.movie.rental.store.controller;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.dto.CopyDto;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.facade.CopyFacade;
import com.movie.rental.store.mapper.CopyMapper;
import com.movie.rental.store.service.CopyDbService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(CopyController.class)
public class CopyControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CopyMapper copyMapper;

    @MockBean
    private CopyFacade copyFacade;

    @MockBean
    private CopyDbService copyDbService;

    private Movie movie = Movie.builder()
                .movieId(1L)
                .movieTitle("Movie title")
                .movieDirector("Movie director")
                .movieDescription("Movie description")
                .movieType(Type.FAMILY)
                .movieYear(2020)
                .build();

    private Copy copy1 = Copy.builder()
            .copyId(1L)
            .movie(movie)
            .copyStatus(Status.AVAILABLE)
            .mediaType(com.movie.rental.store.domain.enums.MediaType.DVD)
            .build();
    private Copy copy2 = Copy.builder()
            .copyId(2L)
            .movie(movie)
            .copyStatus(Status.BORROWED)
            .mediaType(com.movie.rental.store.domain.enums.MediaType.BLU_RAY)
            .build();
    private Copy copy3 = Copy.builder()
            .copyId(3L)
            .movie(movie)
            .copyStatus(Status.AVAILABLE)
            .mediaType(com.movie.rental.store.domain.enums.MediaType.DVD)
            .build();
    private CopyDto copy1Dto = CopyDto.builder()
            .copyId(1L)
            .movieId(movie.getMovieId())
            .copyStatus(Status.AVAILABLE)
            .mediaType(com.movie.rental.store.domain.enums.MediaType.DVD)
            .build();
    private CopyDto copy2Dto = CopyDto.builder()
            .copyId(2L)
            .movieId(movie.getMovieId())
            .copyStatus(Status.BORROWED)
            .mediaType(com.movie.rental.store.domain.enums.MediaType.BLU_RAY)
            .build();
    private CopyDto copy3Dto = CopyDto.builder()
            .copyId(3L)
            .movieId(movie.getMovieId())
            .copyStatus(Status.AVAILABLE)
            .mediaType(com.movie.rental.store.domain.enums.MediaType.DVD)
            .build();

    @Test
    public void getAllCopiesTest() throws Exception {
        //Given
        List<Copy> copies = Arrays.asList(copy1, copy2, copy3);
        List<CopyDto> copiesDto = Arrays.asList(copy1Dto, copy2Dto, copy3Dto);

        when(copyMapper.mapToCopyDtoList(copies)).thenReturn(copiesDto);
        when(copyFacade.getAllCopies()).thenReturn(copiesDto);
        when(copyDbService.getAllCopies()).thenReturn(copies);

        //When & Then
        mockMvc.perform(get("/movies/copies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].copyStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$[0].mediaType", is("DVD")))
                .andExpect(jsonPath("$[1].copyId", is(2)))
                .andExpect(jsonPath("$[1].movieId", is(1)))
                .andExpect(jsonPath("$[1].copyStatus", is("BORROWED")))
                .andExpect(jsonPath("$[1].mediaType", is("BLU_RAY")))
                .andExpect(jsonPath("$[2].copyId", is(3)))
                .andExpect(jsonPath("$[2].movieId", is(1)))
                .andExpect(jsonPath("$[2].copyStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$[2].mediaType", is("DVD")));
    }

    @Test
    public void getAllCopiesByMovieTest() throws Exception {
        //Given
        List<Copy> copies = Arrays.asList(copy1, copy2, copy3);
        List<CopyDto> copiesDto = Arrays.asList(copy1Dto, copy2Dto, copy3Dto);

        when(copyMapper.mapToCopyDtoList(copies)).thenReturn(copiesDto);
        when(copyFacade.getAllCopiesByMovie(1L)).thenReturn(copiesDto);
        when(copyDbService.getAllCopies()).thenReturn(copies);

        //When & Then
        mockMvc.perform(get("/movies/copies/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].copyStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$[0].mediaType", is("DVD")))
                .andExpect(jsonPath("$[1].copyId", is(2)))
                .andExpect(jsonPath("$[1].movieId", is(1)))
                .andExpect(jsonPath("$[1].copyStatus", is("BORROWED")))
                .andExpect(jsonPath("$[1].mediaType", is("BLU_RAY")))
                .andExpect(jsonPath("$[2].copyId", is(3)))
                .andExpect(jsonPath("$[2].movieId", is(1)))
                .andExpect(jsonPath("$[2].copyStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$[2].mediaType", is("DVD")));
    }

    @Test
    public void getAllAvailableCopiesTest() throws Exception {
        //Given
        List<Copy> copies = Arrays.asList(copy1, copy2, copy3);
        List<CopyDto> copiesDto = Arrays.asList(copy1Dto, copy2Dto, copy3Dto);

        when(copyMapper.mapToCopyDtoList(copies)).thenReturn(copiesDto);
        when(copyFacade.getAvailableCopies()).thenReturn(copiesDto);
        when(copyDbService.getAllCopies()).thenReturn(copies);

        //When & Then
        mockMvc.perform(get("/movies/copies/availableCopies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].copyStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$[0].mediaType", is("DVD")))
                .andExpect(jsonPath("$[1].copyId", is(2)))
                .andExpect(jsonPath("$[1].movieId", is(1)))
                .andExpect(jsonPath("$[1].copyStatus", is("BORROWED")))
                .andExpect(jsonPath("$[1].mediaType", is("BLU_RAY")))
                .andExpect(jsonPath("$[2].copyId", is(3)))
                .andExpect(jsonPath("$[2].movieId", is(1)))
                .andExpect(jsonPath("$[2].copyStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$[2].mediaType", is("DVD")));
    }

    @Test
    public void getAllAvailableCopiesByMovieTest() throws Exception {
        //Given
        List<Copy> copies = Arrays.asList(copy1, copy3);
        List<CopyDto> copiesDto = Arrays.asList(copy1Dto, copy3Dto);

        when(copyMapper.mapToCopyDtoList(copies)).thenReturn(copiesDto);
        when(copyFacade.getAvailableCopiesByMovie(1L)).thenReturn(copiesDto);
        when(copyDbService.getAllCopies()).thenReturn(copies);

        //When & Then
        mockMvc.perform(get("/movies/copies/1/availableCopies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].copyStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$[0].mediaType", is("DVD")))
                .andExpect(jsonPath("$[1].copyId", is(3)))
                .andExpect(jsonPath("$[1].movieId", is(1)))
                .andExpect(jsonPath("$[1].copyStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$[1].mediaType", is("DVD")));
    }

    @Test
    public void getAllDVDCopiesByMovieTest() throws Exception {
        //Given
        List<Copy> copies = Arrays.asList(copy1, copy3);
        List<CopyDto> copiesDto = Arrays.asList(copy1Dto, copy3Dto);

        when(copyMapper.mapToCopyDtoList(copies)).thenReturn(copiesDto);
        when(copyFacade.getDVDCopiesByMovie(1L)).thenReturn(copiesDto);
        when(copyDbService.getAllCopies()).thenReturn(copies);

        //When & Then
        mockMvc.perform(get("/movies/copies/1/dvdCopies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].copyStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$[0].mediaType", is("DVD")))
                .andExpect(jsonPath("$[1].copyId", is(3)))
                .andExpect(jsonPath("$[1].movieId", is(1)))
                .andExpect(jsonPath("$[1].copyStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$[1].mediaType", is("DVD")));
    }

    @Test
    public void getAllDVDAvailableCopiesByMovieTest() throws Exception {
        //Given
        List<Copy> copies = Arrays.asList(copy1, copy3);
        List<CopyDto> copiesDto = Arrays.asList(copy1Dto, copy3Dto);

        when(copyMapper.mapToCopyDtoList(copies)).thenReturn(copiesDto);
        when(copyFacade.getDVDAvailableCopiesByMovie(1L)).thenReturn(copiesDto);
        when(copyDbService.getAllCopies()).thenReturn(copies);

        //When & Then
        mockMvc.perform(get("/movies/copies/1/dvdAvailableCopies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].copyStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$[0].mediaType", is("DVD")))
                .andExpect(jsonPath("$[1].copyId", is(3)))
                .andExpect(jsonPath("$[1].movieId", is(1)))
                .andExpect(jsonPath("$[1].copyStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$[1].mediaType", is("DVD")));
    }

    @Test
    public void getBluRayCopiesByMovieTest() throws Exception {
        //Given
        List<Copy> copies = Arrays.asList(copy2);
        List<CopyDto> copiesDto = Arrays.asList(copy2Dto);

        when(copyMapper.mapToCopyDtoList(copies)).thenReturn(copiesDto);
        when(copyFacade.getBluRayCopiesByMovie(1L)).thenReturn(copiesDto);
        when(copyDbService.getAllCopies()).thenReturn(copies);

        //When & Then
        mockMvc.perform(get("/movies/copies/1/blu-rayCopies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].copyId", is(2)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].copyStatus", is("BORROWED")))
                .andExpect(jsonPath("$[0].mediaType", is("BLU_RAY")));
    }

    @Test
    public void getBluRayAvailableCopiesByMovieTest() throws Exception {
        //Given
        List<Copy> copies = new ArrayList<>();
        List<CopyDto> copiesDto = new ArrayList<>();

        when(copyMapper.mapToCopyDtoList(copies)).thenReturn(copiesDto);
        when(copyFacade.getBluRayCopiesByMovie(1L)).thenReturn(copiesDto);
        when(copyDbService.getAllCopies()).thenReturn(copies);

        //When & Then
        mockMvc.perform(get("/movies/copies/1/blu-rayAvailableCopies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getCopyByIdTest() throws Exception {
        //Given
        when(copyMapper.mapToCopyDto(copy1)).thenReturn(copy1Dto);
        when(copyFacade.getCopyById(1L)).thenReturn(copy1Dto);
        when(copyDbService.getCopyById(1L)).thenReturn(Optional.of(copy1));

        //When & Then
        mockMvc.perform(get("/movies/copies/copy1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.copyId", is(1)))
                .andExpect(jsonPath("$.movieId", is(1)))
                .andExpect(jsonPath("$.copyStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$.mediaType", is("DVD")));
    }

    @Test
    public void createCopyTest() throws Exception {
        //Given
        Borrow borrow = Borrow.builder().build();
        when(copyMapper.mapToCopyDto(ArgumentMatchers.any(Copy.class))).thenReturn(copy1Dto);
        when(copyMapper.mapToCopy(ArgumentMatchers.any(CopyDto.class), ArgumentMatchers.eq(movie), ArgumentMatchers.eq(borrow))).thenReturn(copy1);
        when(copyDbService.saveCopy(ArgumentMatchers.any(Copy.class))).thenReturn(copy1);

        Gson gson = new Gson();
        String jsContent = gson.toJson(copy1Dto);

        //When & Then
        mockMvc.perform(post("/movies/copies").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept("application/json")
                .content(jsContent))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCopyTest() throws Exception {
        //Given
        Borrow borrow = Borrow.builder().build();
        when(copyMapper.mapToCopyDto(ArgumentMatchers.any(Copy.class))).thenReturn(copy1Dto);
        when(copyMapper.mapToCopy(ArgumentMatchers.any(CopyDto.class), ArgumentMatchers.eq(movie), ArgumentMatchers.eq(borrow))).thenReturn(copy1);
        when(copyDbService.saveCopy(ArgumentMatchers.any(Copy.class))).thenReturn(copy1);
        when(copyFacade.updateCopy(ArgumentMatchers.any(CopyDto.class))).thenReturn(copy1Dto);

        Gson gson = new Gson();
        String jsContent = gson.toJson(copy1Dto);

        //When & Then
        mockMvc.perform(put("/movies/copies").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept("application/json")
                .content(jsContent))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCopyTest() throws Exception {
        //When & Then
        mockMvc.perform(delete("/movies/copies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }
}
