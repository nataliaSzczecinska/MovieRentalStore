package com.movie.rental.store.domain;

import org.junit.jupiter.api.Test;
import com.movie.rental.store.repository.CustomerRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerTestSuite {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void saveCustomerTest() {
        //Given
        Customer customer = Customer.builder()
                .customerMailAddress("mail@address.com")
                .createAccountDate(LocalDate.of(2000, 1, 1))
                .isBlocked(false)
                .build();

        //When
        customerRepository.save(customer);
        Long customerId = customer.getCustomerId();

        //Then
        assertNotEquals(0L, customerId);

        //Clean-up
        customerRepository.deleteById(customerId);
    }

    @Test
    public void getCustomerByIdTest() {
        //Given
        Customer customer = Customer.builder()
                .customerMailAddress("mail@address.com")
                .createAccountDate(LocalDate.of(2000, 1, 1))
                .isBlocked(false)
                .build();

        //When
        customerRepository.save(customer);
        Long customerId = customer.getCustomerId();
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        //Then
        assertTrue(customerOptional.isPresent());
        assertEquals(customerId, customerOptional.get().getCustomerId());

        //Clean-up
        customerRepository.deleteById(customerId);
    }

    @Test
    public void deleteCustomerTest() {
        //Given
        Customer customer = Customer.builder()
                .customerMailAddress("mail@address.com")
                .createAccountDate(LocalDate.of(2000, 1, 1))
                .isBlocked(false)
                .build();

        //When
        customerRepository.save(customer);
        Long customerId = customer.getCustomerId();
        Optional<Customer> customer1Optional = customerRepository.findById(customerId);
        customerRepository.deleteById(customerId);
        Optional<Customer> customer2Optional = customerRepository.findById(customerId);

        //Then
        assertTrue(customer1Optional.isPresent());
        assertEquals(customerId, customer1Optional.get().getCustomerId());
        assertFalse(customer2Optional.isPresent());

        //Clean-up
    }

    @Test
    public void getAllCustomersTest() {
        //Given
        Customer customer1 = Customer.builder()
                .customerMailAddress("mail1@address.com")
                .createAccountDate(LocalDate.of(2001, 1, 1))
                .isBlocked(false)
                .build();
        Customer customer2 = Customer.builder()
                .customerMailAddress("mail2@address.com")
                .createAccountDate(LocalDate.of(2002, 1, 1))
                .isBlocked(false)
                .build();
        Customer customer3 = Customer.builder()
                .customerMailAddress("mail3@address.com")
                .createAccountDate(LocalDate.of(2003, 1, 1))
                .isBlocked(true)
                .build();

        //When
        customerRepository.save(customer1);
        Long customer1Id = customer1.getCustomerId();
        customerRepository.save(customer2);
        Long customer2Id = customer2.getCustomerId();
        customerRepository.save(customer3);
        Long customer3Id = customer3.getCustomerId();
        List<Customer> customers = customerRepository.findAll();

        //Then
        assertEquals(3, customers.size());
        assertEquals(customer1Id, customers.get(0).getCustomerId());
        assertEquals(customer2Id, customers.get(1).getCustomerId());
        assertEquals(customer3Id, customers.get(2).getCustomerId());
        assertEquals("mail1@address.com", customers.get(0).getCustomerMailAddress());
        assertEquals(LocalDate.of(2002, 1, 1), customers.get(1).getCreateAccountDate());
        assertTrue(customers.get(2).isBlocked());

        //Clean-up
        customerRepository.deleteById(customer1Id);
        customerRepository.deleteById(customer2Id);
        customerRepository.deleteById(customer3Id);
    }
}
