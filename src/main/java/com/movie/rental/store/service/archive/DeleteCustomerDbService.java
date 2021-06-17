package com.movie.rental.store.service.archive;

import com.movie.rental.store.domain.archive.DeleteCustomer;

import com.movie.rental.store.repository.archive.DeleteCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class DeleteCustomerDbService {
    private final DeleteCustomerRepository deleteCustomerRepository;

    public List<DeleteCustomer> getAllDeletedCustomers() {
        return deleteCustomerRepository.findAll();
    }

    public Optional<DeleteCustomer> getDeleteCustomerById(final Long deleteCustomerId) {
        return deleteCustomerRepository.findById(deleteCustomerId);
    }

    public DeleteCustomer saveDeletedCustomer(final DeleteCustomer deleteCustomer) {
        return deleteCustomerRepository.save(deleteCustomer);
    }
}
