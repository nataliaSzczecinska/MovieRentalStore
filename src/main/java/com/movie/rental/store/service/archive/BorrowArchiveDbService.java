package com.movie.rental.store.service.archive;

import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.repository.archive.BorrowArchiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class BorrowArchiveDbService {
    private final BorrowArchiveRepository borrowArchiveRepository;

    public List<BorrowArchive> getAllBorrowArchive() {
        return borrowArchiveRepository.findAll();
    }

    public Optional<BorrowArchive> getBorrowArchiveById(final Long deleteCopyId) {
        return borrowArchiveRepository.findById(deleteCopyId);
    }

    public BorrowArchive saveBorrowArchive(final BorrowArchive borrowArchive) {
        return borrowArchiveRepository.save(borrowArchive);
    }

    public List<BorrowArchive> searchBorrowArchiveByCustomerId(final Long customerId) {
        return borrowArchiveRepository.retrieveBorrowArchiveByCustomerId(customerId);
    }
}
