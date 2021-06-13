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
        Movie movie = new Movie("Title Test", "Director Test", "Description Test", Type.ACTION, 2000);
        Copy copy = new Copy(movie, Status.AVAILABLE, MediaType.DVD);
        Customer customer = new Customer("mail@address.com", LocalDate.of(2000, 1, 1), false);
        Borrow borrow = new Borrow(copy, customer, LocalDate.of(2001, 1, 11), LocalDate.of(2002, 2, 12));
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
        Movie movie = new Movie("Title Test", "Director Test", "Description Test", Type.ACTION, 2000);
        Copy copy = new Copy(movie, Status.AVAILABLE, MediaType.DVD);
        Customer customer = new Customer("mail@address.com", LocalDate.of(2000, 1, 1), false);
        Borrow borrow = new Borrow(copy, customer, LocalDate.of(2001, 1, 11), LocalDate.of(2002, 2, 12));
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
        Movie movie = new Movie("Title Test", "Director Test", "Description Test", Type.ACTION, 2000);
        Copy copy1 = new Copy(movie, Status.AVAILABLE, MediaType.DVD);
        Copy copy2 = new Copy(movie, Status.AVAILABLE, MediaType.BLU_RAY);
        Customer customer = new Customer("mail@address.com", LocalDate.of(2000, 1, 1), false);
        Borrow borrow1 = new Borrow(copy1, customer, LocalDate.of(2001, 1, 11), LocalDate.of(2002, 2, 12));
        Borrow borrow2 = new Borrow(copy1, customer, LocalDate.of(2002, 2, 12), LocalDate.of(2003, 3, 13));
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
        Movie movie = new Movie("Title Test", "Director Test", "Description Test", Type.ACTION, 2000);
        Copy copy1 = new Copy(movie, Status.AVAILABLE, MediaType.DVD);
        Copy copy2 = new Copy(movie, Status.AVAILABLE, MediaType.BLU_RAY);
        Customer customer = new Customer("mail@address.com", LocalDate.of(2000, 1, 1), false);
        Borrow borrow1 = new Borrow(copy1, customer, LocalDate.of(2001, 1, 11), LocalDate.of(2002, 2, 12));
        Borrow borrow2 = new Borrow(copy2, customer, LocalDate.of(2002, 2, 12), LocalDate.of(2003, 3, 13));
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
