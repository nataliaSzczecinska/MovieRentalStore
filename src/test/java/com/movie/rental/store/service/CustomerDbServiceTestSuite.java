package com.movie.rental.store.service;

import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.repository.CustomerRepository;
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
public class CustomerDbServiceTestSuite {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerDbService customerDbService;

    private List<Customer> customers = customerExamples();

    private List<Customer> customerExamples() {
        List<Customer> list = new ArrayList<>();
        list.add(Customer.builder()
                .customerMailAddress("customer1@mail.com")
                .createAccountDate(LocalDate.of(2001, 12, 12))
                .isBlocked(false)
                .build());
        list.add(Customer.builder()
                .customerMailAddress("customer2@mail.com")
                .createAccountDate(LocalDate.of(2002, 2, 7))
                .isBlocked(false)
                .build());
        list.add(Customer.builder()
                .customerMailAddress("customer3@mail.com")
                .createAccountDate(LocalDate.of(2003, 10, 9))
                .isBlocked(false)
                .build());
        list.add(Customer.builder()
                .customerMailAddress("customer4@mail.com")
                .createAccountDate(LocalDate.of(2004, 4, 12))
                .isBlocked(false)
                .build());
        return list;
    }

    private List<Long> saveCustomerInDatabase(List<Customer> customerList) {
        List<Long> idLists = new ArrayList<>();
        for (Customer customer : customerList) {
            customerRepository.save(customer);
            idLists.add(customer.getCustomerId());
        }
        return idLists;
    }

    @Test
    public void getAllCustomersTest() {
        //Given
        List<Long> ids = saveCustomerInDatabase(customers);

        //When
        List<Customer> customerList = customerDbService.getAllCustomers();

        //Then
        assertEquals(4, customerList.size());

        //Clean-up
        customerRepository.deleteAll();
    }

    @Test
    public void getCustomerByIdTest() {
        //Given
        List<Long> ids = saveCustomerInDatabase(customers);

        //When
        Optional<Customer> customer1Optional = customerDbService.getCustomerById(ids.get(1));
        Optional<Customer> customer2Optional = customerDbService.getCustomerById(5L);
        customerDbService.getAllCustomers();

        //Then
        assertTrue(customer1Optional.isPresent());
        assertFalse(customer2Optional.isPresent());
        assertEquals(ids.get(1), customer1Optional.get().getCustomerId());
        assertEquals("customer2@mail.com", customer1Optional.get().getCustomerMailAddress());

        //Clean-up
        customerRepository.deleteAll();
    }

    @Test
    public void saveCustomerTest() {
        //Given
        List<Long> ids = saveCustomerInDatabase(customers);
        Customer customer = Customer.builder()
                .customerMailAddress("add@mail.com")
                .createAccountDate(LocalDate.of(2019, 11, 12))
                .isBlocked(false)
                .build();

        //When
        List<Customer> customersBeforeAdd = customerDbService.getAllCustomers();
        customerDbService.saveCustomer(customer);
        Long customerId = customer.getCustomerId();
        List<Customer> customersAfterAdd = customerDbService.getAllCustomers();
        Optional<Customer> customerOptional = customerDbService.getCustomerById(customerId);

        //Then
        assertEquals(4, customersBeforeAdd.size());
        assertEquals(5, customersAfterAdd.size());
        assertEquals(customerId, customersAfterAdd.get(customersAfterAdd.size() - 1).getCustomerId());
        assertTrue(customerOptional.isPresent());

        //Clean-up
        customerRepository.deleteAll();
    }

    @Test
    public void deleteCustomerTest() {
        //Given
        List<Long> ids = saveCustomerInDatabase(customers);

        //When
        List<Customer> customersBeforeDelete = customerDbService.getAllCustomers();
        customerDbService.deleteCustomerById(ids.get(1));
        List<Customer> customersAfterDelete = customerDbService.getAllCustomers();
        Optional<Customer> customerOptional = customerDbService.getCustomerById(ids.get(1));

        //Then
        assertEquals(4, customersBeforeDelete.size());
        assertEquals(3, customersAfterDelete.size());
        assertFalse(customerOptional.isPresent());

        //Clean-up
        customerRepository.deleteAll();
    }
}
