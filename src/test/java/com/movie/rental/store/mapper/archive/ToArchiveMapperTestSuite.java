package com.movie.rental.store.mapper.archive;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.archive.DeleteCustomer;
import com.movie.rental.store.domain.enums.BorrowArchiveType;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.enums.Type;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ToArchiveMapperTestSuite {
    @Autowired
    private ToArchiveMapper toArchiveMapper;

    @Test
    public void mapToDeleteCopyTest() {
        //Given
        Movie movie = new Movie(1L, "Test title", "Test director", "Test description", Type.ACTION, 2000);
        Copy copy = new Copy(1L, movie, Status.AVAILABLE, MediaType.BLU_RAY);
        LocalDate localDate = LocalDate.of(2021, 1, 23);

        //When
        DeleteCopy deleteCopy = toArchiveMapper.mapToDeleteCopy(copy, localDate);

        //Then
        assertEquals(1L, deleteCopy.getPreviousCopyId());
        assertEquals(1L, deleteCopy.getMovie().getMovieId());
        assertEquals("Test title", deleteCopy.getMovie().getMovieTitle());
        assertEquals(MediaType.BLU_RAY, deleteCopy.getMediaType());
        assertEquals(LocalDate.of(2021, 1, 23), deleteCopy.getDeleteDate());
    }

    @Test
    public void mapToBorrowArchiveTest() {
        //Given
        Movie movie = new Movie(1L, "Test title", "Test director", "Test description", Type.ACTION, 2000);
        Copy copy = new Copy(1L, movie, Status.AVAILABLE, MediaType.BLU_RAY);
        Customer customer = new Customer(1L, "mail@address.com", LocalDate.of(2000, 1, 1), false);
        Borrow borrow = new Borrow(1L, copy, customer, LocalDate.of(2000, 10, 10), LocalDate.of(2000, 10, 13));
        LocalDate localDate = LocalDate.of(2021, 1, 23);

        //When
        BorrowArchive borrowArchive = toArchiveMapper.mapToBorrowArchive(borrow, localDate, BorrowArchiveType.DELETED);

        //Then
        assertEquals(1L, borrowArchive.getPreviousBorrowId());
        assertEquals(1L, borrowArchive.getCopyId());
        assertEquals(LocalDate.of(2000, 10, 10), borrowArchive.getBorrowDate());
        assertEquals(LocalDate.of(2000, 10, 13), borrowArchive.getReturnDate());
        assertEquals(LocalDate.of(2021, 1, 23), borrowArchive.getRealReturnDate());
        assertEquals(BorrowArchiveType.DELETED, borrowArchive.getBorrowArchiveType());
    }

    @Test
    public void mapToDeleteCustomerTest() {
        //Given
        Customer customer = new Customer(1L, "mail@address.com", LocalDate.of(2000, 1, 1), false);
        LocalDate localDate = LocalDate.of(2021, 1, 23);

        //When
        DeleteCustomer deleteCustomer = toArchiveMapper.mapToDeleteCustomer(customer, localDate);

        //Then
        assertEquals(1L, deleteCustomer.getPreviousCustomerId());
        assertEquals("mail@address.com", deleteCustomer.getCustomerMailAddress());
        assertEquals(LocalDate.of(2000, 1, 1), deleteCustomer.getCreateAccountDate());
        assertEquals(LocalDate.of(2021, 1, 23), deleteCustomer.getDeleteAccountDate());
        assertFalse(deleteCustomer.isBlocked());
    }
}
