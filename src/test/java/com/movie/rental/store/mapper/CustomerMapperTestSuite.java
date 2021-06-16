package com.movie.rental.store.mapper;

import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.dto.CustomerDto;
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
public class CustomerMapperTestSuite {
    @Autowired
    private CustomerMapper customerMapper;

    @Test
    public void mapToCustomerDtoTest() {
        //Given
        Customer customer = new Customer(1L, "mail@address.com", LocalDate.of(2000, 1, 1), false);

        //When
        CustomerDto customerDto = customerMapper.mapToCustomerDto(customer);

        //Then
        assertEquals(1L, customerDto.getCustomerId());
        assertEquals("mail@address.com", customerDto.getCustomerMailAddress());
        assertEquals(LocalDate.of(2000, 1, 1), customerDto.getCreateAccountDate());
        assertFalse(customerDto.isBlocked());
    }

    @Test
    public void mapToCustomerTest() {
        //Given
        CustomerDto customerDto = new CustomerDto(1L, "mail@address.com", LocalDate.of(2000, 1, 1), false);

        //When
        Customer customer = customerMapper.mapToCustomer(customerDto, null);

        //Then
        assertEquals(1L, customer.getCustomerId());
        assertEquals("mail@address.com", customer.getCustomerMailAddress());
        assertEquals(LocalDate.of(2000, 1, 1), customer.getCreateAccountDate());
        assertFalse(customer.isBlocked());
    }

    @Test
    public void mapToCustomerDtoListTest() {
        //Given
        Customer customer1 = new Customer(1L, "mail1@address.com", LocalDate.of(2001, 1, 1), false);
        Customer customer2 = new Customer(2L, "mail2@address.com", LocalDate.of(2002, 1, 1), false);
        Customer customer3 = new Customer(3L, "mail3@address.com", LocalDate.of(2003, 1, 1), true);
        List<Customer> customers = Arrays.asList(customer1, customer2, customer3);

        //When
        List<CustomerDto> customerDtos = customerMapper.mapToCustomerDtoList(customers);

        //Then
        assertEquals(3, customerDtos.size());
        assertEquals(1L, customerDtos.get(0).getCustomerId());
        assertEquals("mail1@address.com", customerDtos.get(0).getCustomerMailAddress());
        assertEquals(LocalDate.of(2001, 1, 1), customerDtos.get(0).getCreateAccountDate());
        assertFalse(customerDtos.get(0).isBlocked());
        assertEquals(2L, customerDtos.get(1).getCustomerId());
        assertEquals("mail2@address.com", customerDtos.get(1).getCustomerMailAddress());
        assertEquals(LocalDate.of(2002, 1, 1), customerDtos.get(1).getCreateAccountDate());
        assertFalse(customerDtos.get(1).isBlocked());
        assertEquals(3L, customerDtos.get(2).getCustomerId());
        assertEquals("mail3@address.com", customerDtos.get(2).getCustomerMailAddress());
        assertEquals(LocalDate.of(2003, 1, 1), customerDtos.get(2).getCreateAccountDate());
        assertTrue(customerDtos.get(2).isBlocked());
    }
}
