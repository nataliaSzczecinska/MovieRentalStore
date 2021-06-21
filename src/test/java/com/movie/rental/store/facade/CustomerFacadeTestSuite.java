package com.movie.rental.store.facade;

import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.dto.CustomerDto;
import com.movie.rental.store.exception.CustomerAlreadyExistException;
import com.movie.rental.store.exception.CustomerNotFoundException;
import com.movie.rental.store.repository.CustomerRepository;
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
public class CustomerFacadeTestSuite {
    @Autowired
    private CustomerFacade customerFacade;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer1 = Customer.builder()
            .customerMailAddress("customer1@mail.com")
            .createAccountDate(LocalDate.of(2020, 10,7))
            .isBlocked(false)
            .build();
    private Customer customer2 = Customer.builder()
            .customerMailAddress("customer2@mail.com")
            .createAccountDate(LocalDate.of(2010, 11,17))
            .isBlocked(false)
            .build();
    private Customer customer3 = Customer.builder()
            .customerMailAddress("customer3@mail.com")
            .createAccountDate(LocalDate.of(2009, 9,12))
            .isBlocked(true)
            .build();

    @BeforeEach
    public void saveCustomers() {
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
    }

    @AfterEach
    public void cleanDb() {
        customerRepository.deleteAll();
    }

    @Test
    public void getAllCustomersTest(){
        //Given

        //When
        List<CustomerDto> customers = customerFacade.getAllCustomers();

        //Then
        assertEquals(3, customers.size());
    }

    @Test
    public void getCustomerByIdTest() throws CustomerNotFoundException {
        //Given

        //When
        CustomerDto customer = customerFacade.getCustomerById(customer2.getCustomerId());

        //Then
        assertEquals(customer2.getCustomerId(), customer.getCustomerId());
    }

    @Test
    public void createCustomerTest() throws CustomerAlreadyExistException {
        //Given

        //When
        customerFacade.createCustomer("new@customer.pl");
        List<CustomerDto> customers = customerFacade.getAllCustomers();

        //Then
        assertEquals(4, customers.size());
    }

    @Test
    public void updateCustomerTest() throws CustomerNotFoundException, CustomerAlreadyExistException {
        //Given

        //When
        CustomerDto customerDto = customerFacade.updateCustomer(customer1.getCustomerId(), "new@customer.pl");

        //Then
        assertEquals(customer1.getCustomerId(), customerDto.getCustomerId());
        assertEquals("new@customer.pl", customerDto.getCustomerMailAddress());
    }

    /*@Test
    public void deleteCustomerByIdIfBorrowsIsNullTest() throws CustomerNotFoundException {
        //Given

        //When
        Long id = customer2.getCustomerId();
        customerFacade.deleteCustomer(id);
        Optional<Customer> customerOptional = customerRepository.findById(id);

        //Then
        assertFalse(customerOptional.isPresent());
    }*/
}
