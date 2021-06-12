package com.movie.rental.store.repository;

import com.movie.rental.store.domain.Borrow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BorrowRepository extends CrudRepository<Borrow, Long> {
    @Override
    public List<Borrow> findAll();

    @Override
    public Optional<Borrow> findById(Long borrowId);

    @Override
    public Borrow save(Borrow borrow);

    @Override
    public void deleteById(Long borrowId);
}
