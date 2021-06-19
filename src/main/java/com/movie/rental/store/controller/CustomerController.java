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

    @PostMapping
    public void createCustomer(@RequestParam String mailAddress) throws CustomerAlreadyExistException {
        LOGGER.info("The new customer has been created");
        customerFacade.createCustomer(mailAddress);
    }

    @PutMapping(value = "/updateCustomer/{customerId}")
    public CustomerDto updateCustomer(@PathVariable Long customerId, @RequestParam String newMailAddress) throws CustomerNotFoundException {
        LOGGER.info("The customer has just been updated");
        return customerFacade.updateCustomer(customerId, newMailAddress);
    }

    @DeleteMapping(value = "/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        LOGGER.info("The customer has just been deleted");
        customerFacade.deleteCustomer(customerId);
    }
}
