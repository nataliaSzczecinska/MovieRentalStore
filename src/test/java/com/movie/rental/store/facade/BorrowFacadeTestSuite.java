package com.movie.rental.store.facade;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.dto.BorrowDto;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.repository.BorrowRepository;
import com.movie.rental.store.repository.CopyRepository;
import com.movie.rental.store.repository.CustomerRepository;
import com.movie.rental.store.repository.MovieRepository;
import com.movie.rental.store.repository.archive.BorrowArchiveRepository;
import com.movie.rental.store.repository.archive.DeleteCopyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BorrowFacadeTestSuite {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BorrowFacade borrowFacade;

    private Movie movie1 = Movie.builder()
            .movieTitle("First Movie Title")
            .movieDirector("Director 1")
            .movieDescription("Description 1")
            .movieType(Type.COMEDY)
            .movieYear(2020)
            .build();
    private Movie movie2 = Movie.builder()
            .movieTitle("First Movie Title")
            .movieDirector("Director 1")
            .movieDescription("Description 1")
            .movieType(Type.COMEDY)
            .movieYear(2020)
            .build();

    private Copy copy1 = Copy.builder()
            .movie(movie1)
            .copyStatus(Status.BORROWED)
            .mediaType(MediaType.DVD)
            .build();
    private Copy copy2 = Copy.builder()
            .movie(movie2)
            .copyStatus(Status.BORROWED)
            .mediaType(MediaType.DVD)
            .build();
    private Copy copy3 = Copy.builder()
            .movie(movie2)
            .copyStatus(Status.AVAILABLE)
            .mediaType(MediaType.DVD)
            .build();

    private Customer customer = Customer.builder()
            .customerMailAddress("customer1@mail.com")
            .createAccountDate(LocalDate.of(2020, 10,7))
            .isBlocked(false)
            .build();

    private Borrow borrow1 = Borrow.builder()
            .copy(copy1)
            .customer(customer)
            .borrowDate(LocalDate.of(2021, 1, 12))
            .returnDate(LocalDate.of(2021, 1, 19))
            .build();
    private Borrow borrow2 = Borrow.builder()
            .copy(copy2)
            .customer(customer)
            .borrowDate(LocalDate.of(2021, 1, 12))
            .returnDate(LocalDate.of(2021, 1, 19))
            .build();

    @BeforeEach
    public void saveDataInDatabase() {
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        copyRepository.save(copy1);
        copyRepository.save(copy2);
        copyRepository.save(copy3);
        customerRepository.save(customer);
        borrowRepository.save(borrow1);
        borrowRepository.save(borrow2);
    }

    @AfterEach
    public void cleanDatabase() {
        borrowRepository.deleteAll();
        customerRepository.deleteAll();
        copyRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void getAllBorrowsTest() {
        //Given

        //When
        List<BorrowDto> borrows = borrowFacade.getAllBorrows();

        //Then
        assertEquals(2, borrows.size());
    }

    @Test
    public void getBorrowsByMovieIdTest() {
        //Given

        //When
        List<BorrowDto> borrows = borrowFacade.getBorrowsByMovieId(movie1.getMovieId());

        //Then
        assertEquals(1, borrows.size());
    }

    @Test
    public void getBorrowsByUserIdTest() {
        //Given

        //When
        List<BorrowDto> borrows = borrowFacade.getBorrowsByUserId(customer.getCustomerId());

        //Then
        assertEquals(2, borrows.size());
    }
}
