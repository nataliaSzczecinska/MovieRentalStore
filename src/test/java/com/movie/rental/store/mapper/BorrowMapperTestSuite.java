package com.movie.rental.store.mapper;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.dto.BorrowDto;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.enums.Type;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BorrowMapperTestSuite {
    @Autowired
    private BorrowMapper borrowMapper;

    @Test
    public void mapToBorrowTest() {
        //Given
        Movie movie = new Movie(1L, "Test title", "Test director", "Test description", Type.ACTION, 2000);
        Copy copy = new Copy(1L, movie, Status.AVAILABLE, MediaType.BLU_RAY);
        Customer customer = new Customer(1L, "mail@address.com", LocalDate.of(2000, 1, 1), false);
        BorrowDto borrowDto = new BorrowDto(1L, 1L, 1L, LocalDate.of(2000, 10, 10), LocalDate.of(2000, 10, 13));

        //When
        Borrow borrow = borrowMapper.mapToBorrow(borrowDto, copy, customer);

        //Then
        assertEquals(1L, borrow.getBorrowId());
        assertEquals(1L, borrow.getCopy().getCopyId());
        assertEquals(1L, borrow.getCustomer().getCustomerId());
        assertEquals(Status.AVAILABLE, borrow.getCopy().getCopyStatus());
        assertEquals(MediaType.BLU_RAY, borrow.getCopy().getMediaType());
        assertEquals(LocalDate.of(2000, 10, 10), borrow.getBorrowDate());
        assertEquals(LocalDate.of(2000, 10, 13), borrow.getReturnDate());
    }

    @Test
    public void mapToBorrowDtoTest() {
        //Given
        Movie movie = new Movie(1L, "Test title", "Test director", "Test description", Type.ACTION, 2000);
        Copy copy = new Copy(1L, movie, Status.AVAILABLE, MediaType.BLU_RAY);
        Customer customer = new Customer(1L, "mail@address.com", LocalDate.of(2000, 1, 1), false);
        Borrow borrow = new Borrow(1L, copy, customer, LocalDate.of(2000, 10, 10), LocalDate.of(2000, 10, 13));

        //When
        BorrowDto borrowDto = borrowMapper.mapToBorrowDto(borrow);

        //Then
        assertEquals(1L, borrowDto.getBorrowId());
        assertEquals(1L, borrowDto.getCopyId());
        assertEquals(1L, borrowDto.getCustomerId());
        assertEquals(LocalDate.of(2000, 10, 10), borrowDto.getBorrowDate());
        assertEquals(LocalDate.of(2000, 10, 13), borrowDto.getReturnDate());
    }

    @Test
    public void mapToBorrowDtoListTest() {
        //Given
        Movie movie1 = new Movie(1L, "Test title 1", "Test director", "Test description", Type.ACTION, 2000);
        Movie movie2 = new Movie(2L, "Test title 2", "Test director", "Test description", Type.ADVENTURE, 2001);
        Copy copy1 = new Copy(1L, movie1, Status.AVAILABLE, MediaType.BLU_RAY);
        Copy copy2 = new Copy(2L, movie1, Status.AVAILABLE, MediaType.DVD);
        Copy copy3 = new Copy(3L, movie2, Status.BORROWED, MediaType.DVD);
        Customer customer1 = new Customer(1L, "mail1@address.com", LocalDate.of(2000, 1, 1), false);
        Customer customer2 = new Customer(2L, "mail2@address.com", LocalDate.of(2001, 1, 1), false);
        Borrow borrow1 = new Borrow(1L, copy1, customer1, LocalDate.of(2001, 10, 10), LocalDate.of(2001, 10, 13));
        Borrow borrow2 = new Borrow(2L, copy2, customer2, LocalDate.of(2002, 10, 10), LocalDate.of(2002, 10, 13));
        Borrow borrow3 = new Borrow(3L, copy3, customer2, LocalDate.of(2003, 10, 10), LocalDate.of(2003, 10, 13));
        List<Borrow> borrows = Arrays.asList(borrow1, borrow2, borrow3);

        //When
        List<BorrowDto> borrowDtos = borrowMapper.mapToBorrowDtoList(borrows);

        //Then
        assertEquals(3, borrowDtos.size());
        assertEquals(1L, borrowDtos.get(0).getBorrowId());
        assertEquals(1L, borrowDtos.get(0).getCustomerId());
        assertEquals(1L, borrowDtos.get(0).getCopyId());
        assertEquals(LocalDate.of(2001, 10, 10), borrowDtos.get(0).getBorrowDate());
        assertEquals(2L, borrowDtos.get(1).getBorrowId());
        assertEquals(2L, borrowDtos.get(1).getCustomerId());
        assertEquals(2L, borrowDtos.get(1).getCopyId());
        assertEquals(LocalDate.of(2002, 10, 10), borrowDtos.get(1).getBorrowDate());
        assertEquals(3L, borrowDtos.get(2).getBorrowId());
        assertEquals(2L, borrowDtos.get(2).getCustomerId());
        assertEquals(3L, borrowDtos.get(2).getCopyId());
        assertEquals(LocalDate.of(2003, 10, 10), borrowDtos.get(2).getBorrowDate());
    }
}
