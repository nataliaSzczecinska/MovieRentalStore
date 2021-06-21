package com.movie.rental.store.facade;

import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.dto.CustomerDto;
import com.movie.rental.store.exception.CustomerAlreadyExistException;
import com.movie.rental.store.exception.CustomerNotFoundException;
import com.movie.rental.store.mapper.CustomerMapper;
import com.movie.rental.store.mapper.archive.ToArchiveMapper;
import com.movie.rental.store.service.CustomerDbService;
import com.movie.rental.store.service.archive.DeleteCustomerDbService;
import com.movie.rental.store.validator.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class CustomerFacade {
    private final CustomerDbService customerDbService;
    private final CustomerMapper customerMapper;
    private final CustomerValidator customerValidator;
    private final DeleteCustomerDbService deleteCustomerDbService;
    private final ToArchiveMapper toArchiveMapper;

    public List<CustomerDto> getAllCustomers() {
        return customerMapper.mapToCustomerDtoList(customerDbService.getAllCustomers());
    }

    public CustomerDto getCustomerById(Long customerId) throws CustomerNotFoundException {
        return customerMapper.mapToCustomerDto(customerDbService.getCustomerById(customerId).orElseThrow(CustomerNotFoundException::new));
    }

    public void createCustomer(String mailAddress) throws CustomerAlreadyExistException {
        customerValidator.createCustomerIfPossible(mailAddress);
    }

    public CustomerDto updateCustomer(Long customerId, String newMailAddress) throws CustomerNotFoundException, CustomerAlreadyExistException {
        Customer customer = customerDbService.getCustomerById(customerId).orElseThrow(CustomerNotFoundException::new);
        return customerMapper.mapToCustomerDto(customerValidator.updateCustomerIfPossible(customer, newMailAddress));
    }

    public void deleteCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        customerValidator.deleteCustomerIfPossible(customerId);
    }

}
