package com.movie.rental.store.controller;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.dto.BorrowDto;
import com.movie.rental.store.domain.dto.CopyDto;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.facade.BorrowFacade;
import com.movie.rental.store.facade.CopyFacade;
import com.movie.rental.store.facade.CustomerFacade;
import com.movie.rental.store.facade.MovieFacade;
import com.movie.rental.store.mapper.BorrowMapper;
import com.movie.rental.store.service.BorrowDbService;
import com.movie.rental.store.service.CopyDbService;
import com.movie.rental.store.service.CustomerDbService;
import com.movie.rental.store.service.MovieDbService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(BorrowController.class)
public class BorrowControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowMapper borrowMapper;

    @MockBean
    private BorrowFacade borrowFacade;

    @MockBean
    private BorrowDbService borrowDbService;

   /* @MockBean
    private CopyFacade copyFacade;

    @MockBean
    private CopyDbService copyDbService;

    @MockBean
    private CustomerFacade customerFacade;

    @MockBean
    private CustomerDbService customerDbService;

    @MockBean
    private MovieFacade movieFacade;

    @MockBean
    private MovieDbService movieDbService;*/

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
    private CopyDto copy1Dto = CopyDto.builder()
            .copyId(1L)
            .movieId(movie.getMovieId())
            .copyStatus(Status.BORROWED)
            .mediaType(com.movie.rental.store.domain.enums.MediaType.DVD)
            .build();
    private CopyDto copy2Dto = CopyDto.builder()
            .copyId(2L)
            .movieId(movie.getMovieId())
            .copyStatus(Status.BORROWED)
            .mediaType(com.movie.rental.store.domain.enums.MediaType.BLU_RAY)
            .build();

    private Customer customer = Customer.builder()
            .customerId(1L)
            .customerMailAddress("mail@address.com")
            .createAccountDate(LocalDate.of(2000, 1, 1))
            .isBlocked(false)
            .build();

    private Borrow borrow1 = Borrow.builder()
            .borrowId(1L)
            .copy(copy1)
            .customer(customer)
            .borrowDate(LocalDate.of(2010, 3, 12))
            .returnDate(LocalDate.of(2013, 12, 1))
            .build();
    private Borrow borrow2 = Borrow.builder()
            .borrowId(2L)
            .copy(copy2)
            .customer(customer)
            .borrowDate(LocalDate.of(2014, 3, 12))
            .returnDate(LocalDate.of(2014, 5, 11))
            .build();
    private BorrowDto borrow1Dto = BorrowDto.builder()
            .borrowId(1L)
            .copyId(copy1.getCopyId())
            .customerId(customer.getCustomerId())
            .borrowDate(LocalDate.of(2010, 3, 12))
            .returnDate(LocalDate.of(2013, 12, 1))
            .build();
    private BorrowDto borrow2Dto = BorrowDto.builder()
            .borrowId(2L)
            .copyId(copy2.getCopyId())
            .customerId(customer.getCustomerId())
            .borrowDate(LocalDate.of(2014, 3, 12))
            .returnDate(LocalDate.of(2014, 5, 11))
            .build();

    @Test
    public void getAllBorrowsTest() throws Exception {
        //Given
        List<Borrow> borrows = Arrays.asList(borrow1, borrow2);
        List<BorrowDto> borrowsDto = Arrays.asList(borrow1Dto, borrow2Dto);

        when(borrowMapper.mapToBorrowDtoList(borrows)).thenReturn(borrowsDto);
        when(borrowFacade.getAllBorrows()).thenReturn(borrowsDto);
        when(borrowDbService.getAllBorrows()).thenReturn(borrows);

        //When & Then
        mockMvc.perform(get("/movies/borrows").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].borrowId", is(1)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(1)))
                .andExpect(jsonPath("$[0].borrowDate", is("2010-03-12")))
                .andExpect(jsonPath("$[0].returnDate", is("2013-12-01")))
                .andExpect(jsonPath("$[1].borrowId", is(2)))
                .andExpect(jsonPath("$[1].copyId", is(2)))
                .andExpect(jsonPath("$[1].customerId", is(1)))
                .andExpect(jsonPath("$[1].borrowDate", is("2014-03-12")))
                .andExpect(jsonPath("$[1].returnDate", is("2014-05-11")));
    }

    @Test
    public void getBorrowByIdTest() throws Exception {
        //Given
        when(borrowMapper.mapToBorrowDto(borrow1)).thenReturn(borrow1Dto);
        when(borrowFacade.getBorrowById(1L)).thenReturn(borrow1Dto);
        when(borrowDbService.getBorrowById(1L)).thenReturn(Optional.of(borrow1));

        //When & Then
        mockMvc.perform(get("/movies/borrows/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.borrowId", is(1)))
                .andExpect(jsonPath("$.copyId", is(1)))
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.borrowDate", is("2010-03-12")))
                .andExpect(jsonPath("$.returnDate", is("2013-12-01")));
    }

    @Test
    public void getBorrowsByMovieTest() throws Exception {
        //Given
        List<Borrow> borrows = Arrays.asList(borrow1, borrow2);
        List<BorrowDto> borrowsDto = Arrays.asList(borrow1Dto, borrow2Dto);

        when(borrowMapper.mapToBorrowDtoList(borrows)).thenReturn(borrowsDto);
        when(borrowFacade.getBorrowsByMovieId(1L)).thenReturn(borrowsDto);
        when(borrowDbService.getAllBorrows()).thenReturn(borrows);

        //When & Then
        mockMvc.perform(get("/movies/borrows/movie/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].borrowId", is(1)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(1)))
                .andExpect(jsonPath("$[0].borrowDate", is("2010-03-12")))
                .andExpect(jsonPath("$[0].returnDate", is("2013-12-01")))
                .andExpect(jsonPath("$[1].borrowId", is(2)))
                .andExpect(jsonPath("$[1].copyId", is(2)))
                .andExpect(jsonPath("$[1].customerId", is(1)))
                .andExpect(jsonPath("$[1].borrowDate", is("2014-03-12")))
                .andExpect(jsonPath("$[1].returnDate", is("2014-05-11")));
    }

    @Test
    public void getBorrowsByCustomerTest() throws Exception {
        //Given
        List<Borrow> borrows = Arrays.asList(borrow1, borrow2);
        List<BorrowDto> borrowsDto = Arrays.asList(borrow1Dto, borrow2Dto);

        when(borrowMapper.mapToBorrowDtoList(borrows)).thenReturn(borrowsDto);
        when(borrowFacade.getBorrowsByCustomerId(1L)).thenReturn(borrowsDto);
        when(borrowDbService.getAllBorrows()).thenReturn(borrows);

        //When & Then
        mockMvc.perform(get("/movies/borrows/customer/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].borrowId", is(1)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(1)))
                .andExpect(jsonPath("$[0].borrowDate", is("2010-03-12")))
                .andExpect(jsonPath("$[0].returnDate", is("2013-12-01")))
                .andExpect(jsonPath("$[1].borrowId", is(2)))
                .andExpect(jsonPath("$[1].copyId", is(2)))
                .andExpect(jsonPath("$[1].customerId", is(1)))
                .andExpect(jsonPath("$[1].borrowDate", is("2014-03-12")))
                .andExpect(jsonPath("$[1].returnDate", is("2014-05-11")));
    }

    @Test
    public void createBorrowTest() throws Exception {
        //Given
        when(borrowMapper.mapToBorrowDto(ArgumentMatchers.any(Borrow.class))).thenReturn(borrow1Dto);
        when(borrowMapper.mapToBorrow(ArgumentMatchers.any(BorrowDto.class), ArgumentMatchers.eq(copy1), ArgumentMatchers.eq(customer))).thenReturn(borrow1);
        when(borrowDbService.saveBorrow(ArgumentMatchers.any(Borrow.class))).thenReturn(borrow1);

        Gson gson = new Gson();
        String jsContent = gson.toJson(borrow1Dto);

        //When & Then
        mockMvc.perform(post("/movies/borrows/movie=1/customer=1/DVD").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept("application/json")
                .content(jsContent))
                .andExpect(status().isOk());
    }

    @Test
    public void changeBorrowReturnDateTest() throws Exception {
        //Given
        when(borrowMapper.mapToBorrowDto(ArgumentMatchers.any(Borrow.class))).thenReturn(borrow1Dto);
        when(borrowMapper.mapToBorrow(ArgumentMatchers.any(BorrowDto.class), ArgumentMatchers.eq(copy1), ArgumentMatchers.eq(customer))).thenReturn(borrow1);
        when(borrowDbService.saveBorrow(ArgumentMatchers.any(Borrow.class))).thenReturn(borrow1);
        when(borrowFacade.changeBorrowReturnDate("2020-10-20", 1L)).thenReturn(borrow1Dto);

        Gson gson = new Gson();
        String jsContent = gson.toJson(borrow1Dto);

        //When & Then
        mockMvc.perform(put("/movies/borrows/1/2020-10-20").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept("application/json")
                .content(jsContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.borrowId", is(1)))
                .andExpect(jsonPath("$.copyId", is(1)))
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.borrowDate", is("2010-03-12")))
                .andExpect(jsonPath("$.returnDate", is("2013-12-01")));;
    }

    @Test
    public void borrowIsFinishedTest() throws Exception {
        //When & Then
        mockMvc.perform(delete("/movies/borrows/1/DELETE")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }
}
