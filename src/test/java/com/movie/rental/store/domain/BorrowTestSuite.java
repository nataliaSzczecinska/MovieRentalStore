package com.movie.rental.store.domain;

import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.repository.BorrowRepository;
import com.movie.rental.store.repository.CopyRepository;
import com.movie.rental.store.repository.CustomerRepository;
import com.movie.rental.store.repository.MovieRepository;
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
public class BorrowTestSuite {
    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void saveBorrowTest() {
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Title Test")
                .movieDirector("Director Test")
                .movieDescription("Description Test")
                .movieType(Type.ACTION)
                .movieYear(2000)
                .build();
        Copy copy = Copy.builder()
                .movie(movie)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.DVD)
                .build();
        Customer customer = Customer.builder()
                .customerMailAddress("mail@address.com")
                .createAccountDate(LocalDate.of(2000, 1, 1))
                .isBlocked(false)
                .build();
        Borrow borrow = Borrow.builder()
                .copy(copy)
                .customer(customer)
                .borrowDate(LocalDate.of(2001, 1, 11))
                .returnDate(LocalDate.of(2002, 2, 12))
                .build();
        customer.setBorrows(Arrays.asList(borrow));

        //When
        movieRepository.save(movie);
        Long movieId = movie.getMovieId();
        copyRepository.save(copy);
        Long copyId = copy.getCopyId();
        customerRepository.save(customer);
        Long customerId = customer.getCustomerId();
        borrowRepository.save(borrow);
        Long borrowId = borrow.getBorrowId();
        Optional<Borrow> borrowOptional = borrowRepository.findById(borrowId);

        //Then
        assertTrue(borrowOptional.isPresent());

        //Clean-up
        borrowRepository.deleteById(borrowId);
        copyRepository.deleteById(copyId);
        movieRepository.deleteById(movieId);
        customerRepository.deleteById(customerId);
    }

    @Test
    public void deleteBorrowTest() {
        //Given
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Title Test")
                .movieDirector("Director Test")
                .movieDescription("Description Test")
                .movieType(Type.ACTION)
                .movieYear(2000)
                .build();
        Copy copy = Copy.builder()
                .movie(movie)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.DVD)
                .build();
        Customer customer = Customer.builder()
                .customerMailAddress("mail@address.com")
                .createAccountDate(LocalDate.of(2000, 1, 1))
                .isBlocked(false)
                .build();
        Borrow borrow = Borrow.builder()
                .copy(copy)
                .customer(customer)
                .borrowDate(LocalDate.of(2001, 1, 11))
                .returnDate(LocalDate.of(2002, 2, 12))
                .build();
        customer.setBorrows(Arrays.asList(borrow));

        //When
        movieRepository.save(movie);
        Long movieId = movie.getMovieId();
        copyRepository.save(copy);
        Long copyId = copy.getCopyId();
        customerRepository.save(customer);
        Long customerId = customer.getCustomerId();
        borrowRepository.save(borrow);
        Long borrowId = borrow.getBorrowId();
        Optional<Borrow> borrow1Optional = borrowRepository.findById(borrowId);
        borrowRepository.deleteById(borrowId);
        Optional<Borrow> borrow2Optional = borrowRepository.findById(borrowId);

        //Then
        assertTrue(borrow1Optional.isPresent());
        assertFalse(borrow2Optional.isPresent());

        //Clean-up
        copyRepository.deleteById(copyId);
        movieRepository.deleteById(movieId);
        customerRepository.deleteById(customerId);
    }

    @Test
    public void findAllBorrowsTest() {
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Title Test")
                .movieDirector("Director Test")
                .movieDescription("Description Test")
                .movieType(Type.ACTION)
                .movieYear(2000)
                .build();
        Copy copy1 = Copy.builder()
                .movie(movie)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.DVD)
                .build();
        Copy copy2 = Copy.builder()
                .movie(movie)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.BLU_RAY)
                .build();
        Customer customer = Customer.builder()
                .customerMailAddress("mail@address.com")
                .createAccountDate(LocalDate.of(2000, 1, 1))
                .isBlocked(false)
                .build();
        Borrow borrow1 = Borrow.builder()
                .copy(copy1)
                .customer(customer)
                .borrowDate(LocalDate.of(2001, 1, 11))
                .returnDate(LocalDate.of(2002, 2, 12))
                .build();
        Borrow borrow2 = Borrow.builder()
                .copy(copy2)
                .customer(customer)
                .borrowDate(LocalDate.of(2002, 2, 12))
                .returnDate(LocalDate.of(2003, 3, 13))
                .build();
        customer.setBorrows(Arrays.asList(borrow1, borrow2));

        //When
        movieRepository.save(movie);
        Long movieId = movie.getMovieId();
        copyRepository.save(copy1);
        Long copy1Id = copy1.getCopyId();
        copyRepository.save(copy2);
        Long copy2Id = copy2.getCopyId();
        customerRepository.save(customer);
        Long customerId = customer.getCustomerId();
        borrowRepository.save(borrow1);
        Long borrow1Id = borrow1.getBorrowId();
        borrowRepository.save(borrow2);
        Long borrow2Id = borrow2.getBorrowId();
        List<Borrow> borrowList = borrowRepository.findAll();

        //Then
        assertEquals(2, borrowList.size());
        assertEquals(borrow1Id, borrowList.get(0).getBorrowId());
        assertEquals(borrow2Id, borrowList.get(1).getBorrowId());

        //Clean-up
        borrowRepository.deleteById(borrow1Id);
        borrowRepository.deleteById(borrow2Id);
        copyRepository.deleteById(copy1Id);
        copyRepository.deleteById(copy2Id);
        movieRepository.deleteById(movieId);
        customerRepository.deleteById(customerId);
    }

    @Test
    public void customerAndCopyAndBorrowConnectionTest() {
        //Given
        //Given
        Movie movie = Movie.builder()
                .movieTitle("Title Test")
                .movieDirector("Director Test")
                .movieDescription("Description Test")
                .movieType(Type.ACTION)
                .movieYear(2000)
                .build();
        Copy copy1 = Copy.builder()
                .movie(movie)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.DVD)
                .build();
        Copy copy2 = Copy.builder()
                .movie(movie)
                .copyStatus(Status.AVAILABLE)
                .mediaType(MediaType.BLU_RAY)
                .build();
        Customer customer = Customer.builder()
                .customerMailAddress("mail@address.com")
                .createAccountDate(LocalDate.of(2000, 1, 1))
                .isBlocked(false)
                .build();
        Borrow borrow1 = Borrow.builder()
                .copy(copy1)
                .customer(customer)
                .borrowDate(LocalDate.of(2001, 1, 11))
                .returnDate(LocalDate.of(2002, 2, 12))
                .build();
        Borrow borrow2 = Borrow.builder()
                .copy(copy2)
                .customer(customer)
                .borrowDate(LocalDate.of(2002, 2, 12))
                .returnDate(LocalDate.of(2003, 3, 13))
                .build();
        customer.setBorrows(Arrays.asList(borrow1, borrow2));

        //When
        movieRepository.save(movie);
        Long movieId = movie.getMovieId();
        copyRepository.save(copy1);
        Long copy1Id = copy1.getCopyId();
        copyRepository.save(copy2);
        Long copy2Id = copy2.getCopyId();
        customerRepository.save(customer);
        Long customerId = customer.getCustomerId();
        borrowRepository.save(borrow1);
        Long borrow1Id = borrow1.getBorrowId();
        borrowRepository.save(borrow2);
        Long borrow2Id = borrow2.getBorrowId();
        List<Borrow> borrowList = borrowRepository.findAll();
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        //Then
        assertEquals(2, borrowList.size());
        assertEquals(borrow1Id, borrowList.get(0).getBorrowId());
        assertEquals(borrow2Id, borrowList.get(1).getBorrowId());
        assertTrue(customerOptional.isPresent());
        assertEquals(customerId, borrowList.get(0).getCustomer().getCustomerId());
        assertEquals(customerId, borrowList.get(1).getCustomer().getCustomerId());
        assertEquals(copy1Id, borrowList.get(0).getCopy().getCopyId());
        assertEquals(copy2Id, borrowList.get(1).getCopy().getCopyId());

        //Clean-up
        borrowRepository.deleteById(borrow1Id);
        borrowRepository.deleteById(borrow2Id);
        copyRepository.deleteById(copy1Id);
        copyRepository.deleteById(copy2Id);
        movieRepository.deleteById(movieId);
        customerRepository.deleteById(customerId);
    }
}
