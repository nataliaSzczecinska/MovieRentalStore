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
@RequestMapping("/movies/users")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerFacade customerFacade;

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDto> getAllCustomers() {
        LOGGER.info("Get all customer");
        return customerFacade.getAllCustomers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{customerId}")
    public CustomerDto getCustomerById(@PathVariable Long customerId) throws CustomerNotFoundException {
        LOGGER.info("Get customer by id");
        return customerFacade.getCustomerById(customerId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createCustomer")
    public void createCustomer(@RequestParam String mailAddress) throws CustomerAlreadyExistException {
        LOGGER.info("The new customer has been created");
        customerFacade.createCustomer(mailAddress);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateCustomer/{customerId}")
    public CustomerDto updateCustomer(@PathVariable Long customerId, @RequestParam String newMailAddress) throws CustomerNotFoundException {
        LOGGER.info("The customer has just been updated");
        return customerFacade.updateCustomer(customerId, newMailAddress);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        LOGGER.info("The customer has just been deleted");
        customerFacade.deleteCustomer(customerId);
    }
}
