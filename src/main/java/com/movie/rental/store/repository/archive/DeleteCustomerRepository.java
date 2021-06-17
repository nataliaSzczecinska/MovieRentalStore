package com.movie.rental.store.repository.archive;

import com.movie.rental.store.domain.archive.DeleteCustomer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface DeleteCustomerRepository extends CrudRepository<DeleteCustomer, Long> {
    @Override
    public List<DeleteCustomer> findAll();

    @Override
    public Optional<DeleteCustomer> findById(Long deleteCustomerId);

    @Override
    public DeleteCustomer save(DeleteCustomer deleteCustomer);
}
