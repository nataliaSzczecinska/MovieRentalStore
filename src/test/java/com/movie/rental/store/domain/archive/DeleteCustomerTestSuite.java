package com.movie.rental.store.domain.archive;

import com.movie.rental.store.repository.archive.DeleteCustomerRepository;
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
public class DeleteCustomerTestSuite {
    @Autowired
    private DeleteCustomerRepository deleteCustomerRepository;

    @Test
    public void saveDeleteCustomerTest() {
        //Given
        DeleteCustomer deleteCustomer = DeleteCustomer.builder()
                .previousCustomerId(12L)
                .customerMailAddress("mail@address.com")
                .createAccountDate(LocalDate.of(2000, 10, 12))
                .deleteAccountDate(LocalDate.of(2001, 9, 11))
                .isBlocked(false)
                .build();

        //When
        deleteCustomerRepository.save(deleteCustomer);
        Long id = deleteCustomer.getDeleteCustomerId();
        Optional<DeleteCustomer> deleteCustomerOptional = deleteCustomerRepository.findById(id);

        //Then
        assertTrue(deleteCustomerOptional.isPresent());

        //Clean-up
        deleteCustomerRepository.deleteAll();
    }

    @Test
    public void getDeleteCustomerByIdTest() {
        //Given
        DeleteCustomer deleteCustomer = DeleteCustomer.builder()
                .previousCustomerId(12L)
                .customerMailAddress("mail@address.com")
                .createAccountDate(LocalDate.of(2000, 10, 12))
                .deleteAccountDate(LocalDate.of(2001, 9, 11))
                .isBlocked(false)
                .build();

        //When
        deleteCustomerRepository.save(deleteCustomer);
        Long id = deleteCustomer.getDeleteCustomerId();
        Optional<DeleteCustomer> deleteCustomerOptional = deleteCustomerRepository.findById(id);

        //Then
        assertTrue(deleteCustomerOptional.isPresent());

        //Clean-up
        deleteCustomerRepository.deleteAll();
    }

    @Test
    public void getAllDeleteCustomerTest() {
        //Given
        DeleteCustomer deleteCustomer1 = DeleteCustomer.builder()
                .previousCustomerId(4L)
                .customerMailAddress("mai1l@address.com")
                .createAccountDate(LocalDate.of(2000, 10, 12))
                .deleteAccountDate(LocalDate.of(2001, 9, 11))
                .isBlocked(false)
                .build();
        DeleteCustomer deleteCustomer2 = DeleteCustomer.builder()
                .previousCustomerId(12L)
                .customerMailAddress("mail2@address.com")
                .createAccountDate(LocalDate.of(2002, 10, 12))
                .deleteAccountDate(LocalDate.of(2006, 9, 11))
                .isBlocked(false)
                .build();

        //When
        deleteCustomerRepository.save(deleteCustomer1);
        deleteCustomerRepository.save(deleteCustomer2);
        List<DeleteCustomer> deleteCopies = deleteCustomerRepository.findAll();

        //Then
        assertEquals(2, deleteCopies.size());

        //Clean-up
        deleteCustomerRepository.deleteAll();
    }
}
