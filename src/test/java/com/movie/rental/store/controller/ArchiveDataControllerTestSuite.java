package com.movie.rental.store.controller;

import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.archive.DeleteCustomer;
import com.movie.rental.store.domain.dto.BorrowArchiveDto;
import com.movie.rental.store.domain.dto.DeleteCopyDto;
import com.movie.rental.store.domain.dto.DeleteCustomerDto;
import com.movie.rental.store.domain.enums.BorrowArchiveType;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.facade.ArchiveDataFacade;
import com.movie.rental.store.mapper.archive.BorrowArchiveMapper;
import com.movie.rental.store.mapper.archive.DeleteCopyMapper;
import com.movie.rental.store.mapper.archive.DeleteCustomerMapper;
import com.movie.rental.store.service.archive.BorrowArchiveDbService;
import com.movie.rental.store.service.archive.DeleteCopyDbService;
import com.movie.rental.store.service.archive.DeleteCustomerDbService;
import com.movie.rental.store.validator.ArchiveValidator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@WebMvcTest(ArchiveDataController.class)
public class ArchiveDataControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowArchiveMapper borrowArchiveMapper;

    @MockBean
    private DeleteCopyMapper deleteCopyMapper;

    @MockBean
    private DeleteCustomerMapper deleteCustomerMapper;

    @MockBean
    private ArchiveDataFacade archiveDataFacade;

    @MockBean
    private BorrowArchiveDbService borrowArchiveDbService;

    @MockBean
    private DeleteCopyDbService deleteCopyDbService;

    @MockBean
    private DeleteCustomerDbService deleteCustomerDbService;

    @MockBean
    private ArchiveValidator archiveValidator;

    private Movie movie = Movie.builder()
            .movieId(1L)
            .movieTitle("Movie title")
            .movieDirector("Movie director")
            .movieDescription("Movie description")
            .movieType(Type.FAMILY)
            .movieYear(2020)
            .build();

    private BorrowArchive borrowArchive1 = BorrowArchive.builder()
            .borrowArchiveId(1L)
            .previousBorrowId(1L)
            .copyId(1L)
            .customerId(1L)
            .borrowDate(LocalDate.of(2010, 1, 29))
            .returnDate(LocalDate.of(2010, 2, 3))
            .realReturnDate(LocalDate.of(2010, 1, 31))
            .borrowArchiveType(BorrowArchiveType.RETURN_ON_TIME)
            .build();
    private BorrowArchive borrowArchive2 = BorrowArchive.builder()
            .borrowArchiveId(2L)
            .previousBorrowId(2L)
            .copyId(2L)
            .customerId(1L)
            .borrowDate(LocalDate.of(2011, 1, 29))
            .returnDate(LocalDate.of(2011, 2, 3))
            .realReturnDate(LocalDate.of(2011, 3, 31))
            .borrowArchiveType(BorrowArchiveType.RETURN_LATE)
            .build();

    private BorrowArchiveDto borrowArchive1Dto = BorrowArchiveDto.builder()
            .borrowArchiveId(1L)
            .previousBorrowId(1L)
            .copyId(1L)
            .customerId(1L)
            .borrowDate(LocalDate.of(2010, 1, 29))
            .returnDate(LocalDate.of(2010, 2, 3))
            .realReturnDate(LocalDate.of(2010, 1, 31))
            .borrowArchiveType(BorrowArchiveType.RETURN_ON_TIME)
            .build();
    private BorrowArchiveDto borrowArchive2Dto = BorrowArchiveDto.builder()
            .borrowArchiveId(2L)
            .previousBorrowId(2L)
            .copyId(2L)
            .customerId(1L)
            .borrowDate(LocalDate.of(2011, 1, 29))
            .returnDate(LocalDate.of(2011, 2, 3))
            .realReturnDate(LocalDate.of(2011, 3, 31))
            .borrowArchiveType(BorrowArchiveType.RETURN_LATE)
            .build();

    private DeleteCopy deleteCopy1 = DeleteCopy.builder()
            .deleteCopyId(1L)
            .previousCopyId(1L)
            .movie(movie)
            .mediaType(MediaType.DVD)
            .deleteDate(LocalDate.of(2010, 5, 6))
            .build();
    private DeleteCopy deleteCopy2 = DeleteCopy.builder()
            .deleteCopyId(2L)
            .previousCopyId(2L)
            .movie(movie)
            .mediaType(MediaType.DVD)
            .deleteDate(LocalDate.of(2011, 5, 6))
            .build();

    private DeleteCopyDto deleteCopy1Dto = DeleteCopyDto.builder()
            .deleteCopyId(1L)
            .previousCopyId(1L)
            .movieId(movie.getMovieId())
            .mediaType(MediaType.DVD)
            .deleteDate(LocalDate.of(2010, 5, 6))
            .build();
    private DeleteCopyDto deleteCopy2Dto = DeleteCopyDto.builder()
            .deleteCopyId(2L)
            .previousCopyId(2L)
            .movieId(movie.getMovieId())
            .mediaType(MediaType.DVD)
            .deleteDate(LocalDate.of(2011, 5, 6))
            .build();

    private DeleteCustomer deleteCustomer1 = DeleteCustomer.builder()
            .deleteCustomerId(1L)
            .previousCustomerId(1L)
            .customerMailAddress("mail1@address.com")
            .createAccountDate(LocalDate.of(2001, 2, 2))
            .isBlocked(false)
            .build();
    private DeleteCustomer deleteCustomer2 = DeleteCustomer.builder()
            .deleteCustomerId(2L)
            .previousCustomerId(2L)
            .customerMailAddress("mail2@address.com")
            .createAccountDate(LocalDate.of(2002, 2, 2))
            .isBlocked(false)
            .build();

    private DeleteCustomerDto deleteCustomer1Dto = DeleteCustomerDto.builder()
            .deleteCustomerId(1L)
            .previousCustomerId(1L)
            .customerMailAddress("mail1@address.com")
            .createAccountDate(LocalDate.of(2001, 2, 2))
            .isBlocked(false)
            .build();
    private DeleteCustomerDto deleteCustomer2Dto = DeleteCustomerDto.builder()
            .deleteCustomerId(2L)
            .previousCustomerId(2L)
            .customerMailAddress("mail2@address.com")
            .createAccountDate(LocalDate.of(2002, 2, 2))
            .isBlocked(false)
            .build();

    @Test
    public void getAllBorrowsArchiveTest() throws Exception {
        //Given
        List<BorrowArchive> borrowsArchive = Arrays.asList(borrowArchive1, borrowArchive2);
        List<BorrowArchiveDto> borrowsArchiveDto = Arrays.asList(borrowArchive1Dto, borrowArchive2Dto);

        when(borrowArchiveMapper.mapToBorrowArchiveDtoList(borrowsArchive)).thenReturn(borrowsArchiveDto);
        when(archiveDataFacade.getAllBorrowsArchive()).thenReturn(borrowsArchiveDto);
        when(borrowArchiveDbService.getAllBorrowArchive()).thenReturn(borrowsArchive);

        //When & Then
        mockMvc.perform(get("/movies/archive/borrowsArchive")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].borrowArchiveId", is(1)))
                .andExpect(jsonPath("$[0].previousBorrowId", is(1)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(1)))
                .andExpect(jsonPath("$[0].borrowDate", is("2010-01-29")))
                .andExpect(jsonPath("$[0].returnDate", is("2010-02-03")))
                .andExpect(jsonPath("$[0].borrowArchiveType", is("RETURN_ON_TIME")))
                .andExpect(jsonPath("$[1].borrowArchiveId", is(2)))
                .andExpect(jsonPath("$[1].previousBorrowId", is(2)))
                .andExpect(jsonPath("$[1].copyId", is(2)))
                .andExpect(jsonPath("$[1].customerId", is(1)))
                .andExpect(jsonPath("$[1].borrowDate", is("2011-01-29")))
                .andExpect(jsonPath("$[1].returnDate", is("2011-02-03")))
                .andExpect(jsonPath("$[1].borrowArchiveType", is("RETURN_LATE")));
    }

    @Test
    public void getAllDeletedCopiesTest() throws Exception {
        //Given
        List<DeleteCopy> deleteCopies = Arrays.asList(deleteCopy1, deleteCopy2);
        List<DeleteCopyDto> deleteCopiesDto = Arrays.asList(deleteCopy1Dto, deleteCopy2Dto);

        when(deleteCopyMapper.mapToDeleteCopyDtoList(deleteCopies)).thenReturn(deleteCopiesDto);
        when(archiveDataFacade.getAllDeleteCopies()).thenReturn(deleteCopiesDto);
        when(deleteCopyDbService.getAllDeletedCopies()).thenReturn(deleteCopies);

        //When & Then
        mockMvc.perform(get("/movies/archive/deleteCopies")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].deleteCopyId", is(1)))
                .andExpect(jsonPath("$[0].previousCopyId", is(1)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].mediaType", is("DVD")))
                .andExpect(jsonPath("$[0].deleteDate", is("2010-05-06")))
                .andExpect(jsonPath("$[1].deleteCopyId", is(2)))
                .andExpect(jsonPath("$[1].previousCopyId", is(2)))
                .andExpect(jsonPath("$[1].movieId", is(1)))
                .andExpect(jsonPath("$[1].mediaType", is("DVD")))
                .andExpect(jsonPath("$[1].deleteDate", is("2011-05-06")));
    }

    @Test
    public void getAllDeletedCustomersTest() throws Exception {
        //Given
        List<DeleteCustomer> deleteCustomers = Arrays.asList(deleteCustomer1, deleteCustomer2);
        List<DeleteCustomerDto> deleteCustomersDto = Arrays.asList(deleteCustomer1Dto, deleteCustomer2Dto);

        when(deleteCustomerMapper.mapToDeleteCustomerDtoList(deleteCustomers)).thenReturn(deleteCustomersDto);
        when(archiveDataFacade.getAllDeleteCustomers()).thenReturn(deleteCustomersDto);
        when(deleteCustomerDbService.getAllDeletedCustomers()).thenReturn(deleteCustomers);

        //When & Then
        mockMvc.perform(get("/movies/archive/deleteCustomers")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].deleteCustomerId", is(1)))
                .andExpect(jsonPath("$[0].previousCustomerId", is(1)))
                .andExpect(jsonPath("$[0].customerMailAddress", is("mail1@address.com")))
                .andExpect(jsonPath("$[0].blocked", is(false)))
                .andExpect(jsonPath("$[1].deleteCustomerId", is(2)))
                .andExpect(jsonPath("$[1].previousCustomerId", is(2)))
                .andExpect(jsonPath("$[1].customerMailAddress", is("mail2@address.com")))
                .andExpect(jsonPath("$[1].blocked", is(false)));
    }

    @Test
    public void getBorrowsArchiveByMovieIdTest() throws Exception {
        //Given
        List<BorrowArchive> borrowsArchive = Arrays.asList(borrowArchive1, borrowArchive2);
        List<BorrowArchiveDto> borrowsArchiveDto = Arrays.asList(borrowArchive1Dto, borrowArchive2Dto);

        when(borrowArchiveMapper.mapToBorrowArchiveDtoList(borrowsArchive)).thenReturn(borrowsArchiveDto);
        when(archiveDataFacade.getBorrowsArchiveByMovieId(1L)).thenReturn(borrowsArchiveDto);
        when(borrowArchiveDbService.getAllBorrowArchive()).thenReturn(borrowsArchive);
        when(archiveValidator.getBorrowsArchiveByMovieId(1L)).thenReturn(borrowsArchive);

        //When & Then
        mockMvc.perform(get("/movies/archive/movie=1")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].borrowArchiveId", is(1)))
                .andExpect(jsonPath("$[0].previousBorrowId", is(1)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(1)))
                .andExpect(jsonPath("$[0].borrowDate", is("2010-01-29")))
                .andExpect(jsonPath("$[0].returnDate", is("2010-02-03")))
                .andExpect(jsonPath("$[0].borrowArchiveType", is("RETURN_ON_TIME")))
                .andExpect(jsonPath("$[1].borrowArchiveId", is(2)))
                .andExpect(jsonPath("$[1].previousBorrowId", is(2)))
                .andExpect(jsonPath("$[1].copyId", is(2)))
                .andExpect(jsonPath("$[1].customerId", is(1)))
                .andExpect(jsonPath("$[1].borrowDate", is("2011-01-29")))
                .andExpect(jsonPath("$[1].returnDate", is("2011-02-03")))
                .andExpect(jsonPath("$[1].borrowArchiveType", is("RETURN_LATE")));
    }

    @Test
    public void getBorrowsArchiveByCustomerIdTest() throws Exception {
        //Given
        List<BorrowArchive> borrowsArchive = Arrays.asList(borrowArchive1, borrowArchive2);
        List<BorrowArchiveDto> borrowsArchiveDto = Arrays.asList(borrowArchive1Dto, borrowArchive2Dto);

        when(borrowArchiveMapper.mapToBorrowArchiveDtoList(borrowsArchive)).thenReturn(borrowsArchiveDto);
        when(archiveDataFacade.getBorrowsArchiveByCustomerId(1L)).thenReturn(borrowsArchiveDto);
        when(borrowArchiveDbService.searchBorrowArchiveByCustomerId(1L)).thenReturn(borrowsArchive);

        //When & Then
        mockMvc.perform(get("/movies/archive/customer=1")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].borrowArchiveId", is(1)))
                .andExpect(jsonPath("$[0].previousBorrowId", is(1)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(1)))
                .andExpect(jsonPath("$[0].borrowDate", is("2010-01-29")))
                .andExpect(jsonPath("$[0].returnDate", is("2010-02-03")))
                .andExpect(jsonPath("$[0].borrowArchiveType", is("RETURN_ON_TIME")))
                .andExpect(jsonPath("$[1].borrowArchiveId", is(2)))
                .andExpect(jsonPath("$[1].previousBorrowId", is(2)))
                .andExpect(jsonPath("$[1].copyId", is(2)))
                .andExpect(jsonPath("$[1].customerId", is(1)))
                .andExpect(jsonPath("$[1].borrowDate", is("2011-01-29")))
                .andExpect(jsonPath("$[1].returnDate", is("2011-02-03")))
                .andExpect(jsonPath("$[1].borrowArchiveType", is("RETURN_LATE")));
    }
}