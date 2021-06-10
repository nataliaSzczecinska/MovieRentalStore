package com.movie.rental.store.controller;

import com.movie.rental.store.domain.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies/users")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDto> getAllCustomers() {
        LOGGER.info("Get all customer");
        List<CustomerDto> customerList = new ArrayList<>();
        customerList.add(new CustomerDto(1L, "adres@mail.com", LocalDate.of(2018, 12, 13), false));
        customerList.add(new CustomerDto(2L, "adres2@mail.com", LocalDate.of(2018, 12, 13), false));
        customerList.add(new CustomerDto(3L, "adres3@mail.com", LocalDate.of(2018, 12, 13), false));
        return customerList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{customerId}")
    public CustomerDto getCustomerById(@PathVariable Long customerId) {
        return new CustomerDto(1L, "getByIdCustomer@mail.com", LocalDate.of(2019,2, 8), false);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createCustomer", consumes = APPLICATION_JSON_VALUE)
    public void createCustomer(@RequestBody CustomerDto customerDto) {
        LOGGER.info("The new customer has been created");
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateCustomer", consumes = APPLICATION_JSON_VALUE)
    public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto) {
        LOGGER.info("The customer has just been updated");
        return new CustomerDto(1L, "updateCustomer@mail.com", LocalDate.of(2020, 3,14), true);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        LOGGER.info("The customer has just been deleted");
    }
}
