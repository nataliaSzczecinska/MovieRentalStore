package com.movie.rental.store.service.archive;

import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.repository.archive.BorrowArchiveRepository;
import com.movie.rental.store.repository.archive.DeleteCopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class BorrowArchiveDbService {
    private BorrowArchiveRepository borrowArchiveRepository;

    public List<BorrowArchive> getAllBorrowArchive() {
        return borrowArchiveRepository.findAll();
    }

    public Optional<BorrowArchive> getBorrowArchiveById(final Long deleteCopyId) {
        return borrowArchiveRepository.findById(deleteCopyId);
    }

    public BorrowArchive saveBorrowArchive(final BorrowArchive borrowArchive) {
        return borrowArchiveRepository.save(borrowArchive);
    }

}
