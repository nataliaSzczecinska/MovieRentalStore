package com.movie.rental.store.service;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.repository.BorrowRepository;
import com.movie.rental.store.repository.CopyRepository;
import com.movie.rental.store.repository.CustomerRepository;
import com.movie.rental.store.repository.MovieRepository;
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
public class BorrowDbServiceTestSuite {
    @Autowired
    private BorrowDbService borrowDbService;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Movie movie = Movie.builder()
            .movieTitle("First Movie Title")
            .movieDirector("Director 1")
            .movieDescription("Description 1")
            .movieType(Type.COMEDY)
            .movieYear(2020)
            .build();

    private Copy copy1 = Copy.builder()
            .movie(movie)
            .copyStatus(Status.AVAILABLE)
            .mediaType(MediaType.DVD)
            .build();
    private Copy copy2 = Copy.builder()
            .movie(movie)
            .copyStatus(Status.AVAILABLE)
            .mediaType(MediaType.DVD)
            .build();
    private Copy copy3 = Copy.builder()
            .movie(movie)
            .copyStatus(Status.AVAILABLE)
            .mediaType(MediaType.DVD)
            .build();

    private Customer customer1 = Customer.builder()
            .customerMailAddress("customer1@mail.com")
            .createAccountDate(LocalDate.of(2020, 10,7))
            .isBlocked(false)
            .build();
    private Customer customer2 = Customer.builder()
            .customerMailAddress("customer2@mail.com")
            .createAccountDate(LocalDate.of(2020, 12,12))
            .isBlocked(false)
            .build();

    private List<Borrow> exampleBorrowList() {
        List<Borrow> borrowList = new ArrayList<>();
        borrowList.add(Borrow.builder()
                .copy(copy1)
                .customer(customer1)
                .borrowDate(LocalDate.of(2021, 1, 12))
                .returnDate(LocalDate.of(2021, 1, 19))
                .build());
        borrowList.add(Borrow.builder()
                .copy(copy2)
                .customer(customer1)
                .borrowDate(LocalDate.of(2021, 1, 12))
                .returnDate(LocalDate.of(2021, 1, 19))
                .build());
        borrowList.add(Borrow.builder()
                .copy(copy3)
                .customer(customer2)
                .borrowDate(LocalDate.of(2021, 1, 12))
                .returnDate(LocalDate.of(2021, 1, 19))
                .build());

        return borrowList;
    }

    private List<Long> saveBorrowsInDatabase(List<Borrow> borrowList) {
        List<Long> ids = new ArrayList<>();
        movieRepository.save(movie);
        copyRepository.save(copy1);
        copyRepository.save(copy2);
        copyRepository.save(copy3);
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        for (Borrow borrow : borrowList) {
            borrowDbService.saveBorrow(borrow);
            ids.add(borrow.getBorrowId());
        }

        return ids;
    }

    @Test
    public void getAllBorrowsTest() {
        //Given
        List<Borrow> borrows = exampleBorrowList();
        saveBorrowsInDatabase(borrows);

        //When
        List<Borrow> borrowList = borrowDbService.getAllBorrows();

        //Then
        assertEquals(3, borrowList.size());

        //Clean-up
        borrowRepository.deleteAll();
        customerRepository.deleteAll();
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getBorrowByIdTest() {
        //Given
        List<Borrow> borrows = exampleBorrowList();
        List<Long> borrowIds = saveBorrowsInDatabase(borrows);

        //When
        Optional<Borrow> borrow1Optional = borrowDbService.getBorrowById(borrowIds.get(1));
        Optional<Borrow> borrow2Optional = borrowDbService.getBorrowById(borrowIds.get(2) + 10L);

        //Then
        assertTrue(borrow1Optional.isPresent());
        assertFalse(borrow2Optional.isPresent());
        assertEquals(borrowIds.get(1), borrow1Optional.get().getBorrowId());
        assertEquals(copy2.getCopyId(), borrow1Optional.get().getCopy().getCopyId());
        assertEquals(customer1.getCustomerId(), borrow1Optional.get().getCustomer().getCustomerId());

        //Clean-up
        borrowRepository.deleteAll();
        customerRepository.deleteAll();
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void saveBorrowTest() {
        //Given
        List<Borrow> borrows = exampleBorrowList();
        saveBorrowsInDatabase(borrows);
        Borrow borrow = Borrow.builder()
                .copy(copy1)
                .customer(customer2)
                .borrowDate(LocalDate.of(2001, 12, 1))
                .returnDate(LocalDate.of(2001, 12, 15))
                .build();

        //When
        List<Borrow> borrowsBeforeAdd = borrowDbService.getAllBorrows();
        borrowDbService.saveBorrow(borrow);
        Long id = borrow.getBorrowId();
        List<Borrow> borrowsAfterAdd = borrowDbService.getAllBorrows();

        //Then
        assertEquals(3, borrowsBeforeAdd.size());
        assertEquals(4, borrowsAfterAdd.size());
        assertEquals(id, borrowsAfterAdd.get(borrowsAfterAdd.size() - 1).getBorrowId());

        //Clean-up
        borrowRepository.deleteAll();
        customerRepository.deleteAll();
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void deleteBorrowTest() {
        //Given
        List<Borrow> borrows = exampleBorrowList();
        List<Long> ids = saveBorrowsInDatabase(borrows);

        //When
        List<Borrow> borrowsBeforeDelete = borrowDbService.getAllBorrows();
        Optional<Borrow> beforeDelete = borrowDbService.getBorrowById(ids.get(1));
        borrowDbService.deleteBorrow(ids.get(1));
        Optional<Borrow> afterDelete = borrowDbService.getBorrowById(ids.get(1));
        List<Borrow> borrowsAfterDelete = borrowDbService.getAllBorrows();

        //Then
        assertTrue(beforeDelete.isPresent());
        assertFalse(afterDelete.isPresent());
        assertEquals(3, borrowsBeforeDelete.size());
        assertEquals(2, borrowsAfterDelete.size());

        //Clean-up
        borrowRepository.deleteAll();
        customerRepository.deleteAll();
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }
}
