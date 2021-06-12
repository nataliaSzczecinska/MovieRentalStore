package com.movie.rental.store.repository;

import com.movie.rental.store.domain.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    @Override
    public List<Movie> findAll();

    @Override
    public Optional<Movie> findById(Long movieId);

    @Override
    public Movie save(Movie movie);
}
