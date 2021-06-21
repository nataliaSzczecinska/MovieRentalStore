package com.movie.rental.store.validator;

import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.exception.CustomerAlreadyExistException;
import com.movie.rental.store.exception.CustomerNotFoundException;
import com.movie.rental.store.mapper.archive.ToArchiveMapper;
import com.movie.rental.store.service.CustomerDbService;
import com.movie.rental.store.service.archive.DeleteCustomerDbService;
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
    private final DeleteCustomerDbService deleteCustomerDbService;
    private final ToArchiveMapper toArchiveMapper;

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

    public Customer updateCustomerIfPossible(Customer customer, String mailAddress) throws CustomerAlreadyExistException {
        for (Customer element : customerDbService.getAllCustomers()) {
            if(mailAddress.equals(element.getCustomerMailAddress())) {
                LOGGER.warn("The customer with this mail address already exist!");
                throw new CustomerAlreadyExistException();
            }
        }
        customer.setCustomerMailAddress(mailAddress);
        return customerDbService.saveCustomer(customer);
    }

    public void deleteCustomerIfPossible(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerDbService.getCustomerById(customerId).orElseThrow(CustomerNotFoundException::new);
        if(customer.getBorrows().isEmpty()) {
            deleteCustomerDbService.saveDeletedCustomer(toArchiveMapper.mapToDeleteCustomer(customer, LocalDate.now()));
            customerDbService.deleteCustomerById(customerId);
        } else {
            LOGGER.warn("The customer cannot be deleted! There is/are some borrow/s to finish.");
        }
    }
}
