package com.movie.rental.store.service;

import com.movie.rental.store.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieDbService {
    private final MovieRepository movieRepository;
}
