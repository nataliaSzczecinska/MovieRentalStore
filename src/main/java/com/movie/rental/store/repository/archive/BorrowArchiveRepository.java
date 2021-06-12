package com.movie.rental.store.repository.archive;

import com.movie.rental.store.domain.archive.BorrowArchive;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BorrowArchiveRepository extends CrudRepository<BorrowArchive, Long> {
    @Override
    public List<BorrowArchive> findAll();

    @Override
    public Optional<BorrowArchive> findById(Long borrowArchiveId);

    @Override
    public BorrowArchive save(BorrowArchive borrowArchive);
}
