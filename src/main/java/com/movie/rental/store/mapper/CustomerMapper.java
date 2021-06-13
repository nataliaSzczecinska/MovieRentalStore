package com.movie.rental.store.mapper;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.domain.dto.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerMapper.class);

    public Customer mapToCustomer (final CustomerDto customerDto, List<Borrow> borrows) {
        LOGGER.info("Map CustomerDto to Customer");
        return new Customer(customerDto.getCustomerId(),
                customerDto.getCustomerMailAddress(),
                customerDto.getCreateAccountDate(),
                customerDto.isBlocked(),
                borrows);
    }

    public CustomerDto mapToCustomerDto (final Customer customer) {
        LOGGER.info("Map Customer to CustomerDto");
        return new CustomerDto(customer.getCustomerId(),
                customer.getCustomerMailAddress(),
                customer.getCreateAccountDate(),
                customer.isBlocked());
    }

    public List<CustomerDto> mapToCustomerDtoList (final List<Customer> customers) {
        LOGGER.info("Map CustomerList to CustomerDtoList");
        return customers.stream()
                .map(customer -> mapToCustomerDto(customer))
                .collect(Collectors.toList());
    }
}
