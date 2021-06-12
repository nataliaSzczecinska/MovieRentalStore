package com.movie.rental.store.repository;

import com.movie.rental.store.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Override
    public List<Customer> findAll();

    @Override
    public Optional<Customer> findById(Long customerId);

    @Override
    public Customer save(Customer customer);

    @Override
    public void deleteById(Long customerId);
}
