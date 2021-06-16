package com.movie.rental.store.service;

import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CustomerDbService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(final Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Customer saveCustomer(final Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomerById(final Long customerId) {
        customerRepository.deleteById(customerId);
    }
}
