package com.movie.rental.store.service;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.repository.CopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CopyDbService {
    private final CopyRepository copyRepository;

    public List<Copy> getAllCopies() {
        return copyRepository.findAll();
    }

    public Optional<Copy> getCopyById(final Long copyId) {
        return copyRepository.findById(copyId);
    }

    public Copy saveCopy(final Copy copy) {
        return copyRepository.save(copy);
    }

    public void deleteCopyById(final Long copyId) {
        copyRepository.deleteById(copyId);
    }
}
