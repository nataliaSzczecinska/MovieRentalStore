package com.movie.rental.store.repository;

import com.movie.rental.store.domain.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Repository
public interface CopyRepository extends CrudRepository<Copy, Long> {
    @Override
    public List<Copy> findAll();

    @Override
    public Optional<Copy> findById(Long copyId);

    @Override
    public Copy save(Copy copy);

    @Override
    public void deleteById(Long copyId);
}
