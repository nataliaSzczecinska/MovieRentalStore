package com.movie.rental.store.service;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class BorrowDbService {
    private final BorrowRepository borrowRepository;

    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }

    public Optional<Borrow> getBorrowById(final Long borrowId) {
        return borrowRepository.findById(borrowId);
    }

    public Borrow saveBorrow(final Borrow borrow) {
        return borrowRepository.save(borrow);
    }

    public void deleteBorrow(final Long borrowId) {
        borrowRepository.deleteById(borrowId);
    }
}
