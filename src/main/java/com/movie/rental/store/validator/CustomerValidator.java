package com.movie.rental.store.validator;

import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.exception.CustomerAlreadyExistException;
import com.movie.rental.store.service.CustomerDbService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class CustomerValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerValidator.class);
    private final CustomerDbService customerDbService;

    public void createCustomerIfPossible(String mailAddress) throws CustomerAlreadyExistException {
        for (Customer customer : customerDbService.getAllCustomers()) {
            if(mailAddress.equals(customer.getCustomerMailAddress())) {
                LOGGER.warn("The customer with this mail address already exist!");
                throw new CustomerAlreadyExistException();
            }
        }
        Customer customer = Customer.builder()
                .customerMailAddress(mailAddress)
                .createAccountDate(LocalDate.now())
                .isBlocked(false)
                .build();
        customerDbService.saveCustomer(customer);
    }
}
