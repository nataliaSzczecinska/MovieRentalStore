package com.movie.rental.store.scheduler;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.service.BorrowDbService;
import com.movie.rental.store.service.CustomerDbService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BlockOrUnblockCustomerScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockOrUnblockCustomerScheduler.class);
    private final CustomerDbService customerDbService;
    private final BorrowDbService borrowDbService;

    @Scheduled(cron = "0 0 1 * * *")
    public void blockCustomerIfTheyExceedTheDeadlineOfReturnCopyMoreThen2Days() {
        LOGGER.info("Check if there are any customers who did not return copies on time");
        List<Borrow> borrows = borrowDbService.getAllBorrows();

        for (Borrow borrow : borrows) {
            if(Duration.between(borrow.getReturnDate().atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() >= 2) {
                LOGGER.info("Customer with id {} is blocked now", borrow.getCustomer().getCustomerId());
                Customer customer = borrow.getCustomer();
                customer.setBlocked(true);
                customerDbService.saveCustomer(customer);
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void unblockCustomerIfTheyReturnAllOverdueCopies() {
        List<Customer> customers = customerDbService.getAllCustomers().stream()
                .filter(Customer::isBlocked)
                .collect(Collectors.toList());

        for (Customer customer : customers) {
            if(isCustomershouldBeUnblock(customer)) {
                LOGGER.info("Customer with id {} is unblocked now", customer.getCustomerId());
                customer.setBlocked(false);
                customerDbService.saveCustomer(customer);
            }
        }
    }

    private boolean isCustomershouldBeUnblock(Customer customer) {
        List<Borrow> borrowsByCustomer = customer.getBorrows();
        for (Borrow borrow : borrowsByCustomer) {
            if(Duration.between(borrow.getReturnDate().atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() >= 2) {
                return false;
            }
        }
        return true;
    }
}
