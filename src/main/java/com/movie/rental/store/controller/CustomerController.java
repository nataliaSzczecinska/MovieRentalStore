package com.movie.rental.store.controller;

import com.movie.rental.store.domain.dto.CustomerDto;
import com.movie.rental.store.exception.CustomerAlreadyExistException;
import com.movie.rental.store.exception.CustomerNotFoundException;
import com.movie.rental.store.facade.CustomerFacade;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies/customers")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerFacade customerFacade;

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        LOGGER.info("Get all customer");
        return customerFacade.getAllCustomers();
    }

    @GetMapping(value = "/{customerId}")
    public CustomerDto getCustomerById(@PathVariable Long customerId) throws CustomerNotFoundException {
        LOGGER.info("Get customer by id {}", customerId);
        return customerFacade.getCustomerById(customerId);
    }

    @PostMapping("/{mailAddress}")
    public void createCustomer(@PathVariable String mailAddress) throws CustomerAlreadyExistException {
        LOGGER.info("The new customer has been created if possible");
        customerFacade.createCustomer(mailAddress);
    }

    @PutMapping(value = "/{customerId}/{newMailAddress}")
    public CustomerDto updateCustomer(@PathVariable Long customerId, @PathVariable String newMailAddress) throws CustomerNotFoundException, CustomerAlreadyExistException {
        LOGGER.info("The customer will be updated if possible");
        return customerFacade.updateCustomer(customerId, newMailAddress);
    }

    @DeleteMapping(value = "/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        LOGGER.info("The customer will be deleted soon...");
        customerFacade.deleteCustomer(customerId);
    }
}
