package com.movie.rental.store.facade;

import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.dto.CustomerDto;
import com.movie.rental.store.exception.CustomerAlreadyExistException;
import com.movie.rental.store.exception.CustomerNotFoundException;
import com.movie.rental.store.mapper.CustomerMapper;
import com.movie.rental.store.service.CustomerDbService;
import com.movie.rental.store.validator.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CustomerFacade {
    private final CustomerDbService customerDbService;
    private final CustomerMapper customerMapper;
    private final CustomerValidator customerValidator;

    public List<CustomerDto> getAllCustomers() {
        return customerMapper.mapToCustomerDtoList(customerDbService.getAllCustomers());
    }

    public CustomerDto getCustomerById(Long customerId) throws CustomerNotFoundException {
        return customerMapper.mapToCustomerDto(customerDbService.getCustomerById(customerId).orElseThrow(CustomerNotFoundException::new));
    }

    public void createCustomer(String mailAddress) throws CustomerAlreadyExistException {
        customerValidator.createCustomerIfPossible(mailAddress);
    }

    public CustomerDto updateCustomer(Long customerId, String newMailAddress) throws CustomerNotFoundException {
        Customer customer = customerDbService.getCustomerById(customerId).orElseThrow(CustomerNotFoundException::new);
        customer.setCustomerMailAddress(newMailAddress);
        return customerMapper.mapToCustomerDto(customerDbService.saveCustomer(customer));
    }

    public void deleteCustomer(@PathVariable Long customerId) {
        customerDbService.deleteCustomerById(customerId);
    }

}
