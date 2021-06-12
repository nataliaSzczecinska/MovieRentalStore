package com.movie.rental.store.service.archive;

import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.repository.archive.DeleteCopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class DeleteCopyDbService {
    private DeleteCopyRepository deleteCopyRepository;

    public List<DeleteCopy> getAllDeletedCopies() {
        return deleteCopyRepository.findAll();
    }

    public Optional<DeleteCopy> getDeleteCopyById(final Long deleteCopyId) {
        return deleteCopyRepository.findById(deleteCopyId);
    }

    public DeleteCopy saveDeletedMovie(final DeleteCopy deleteCopy) {
        return deleteCopyRepository.save(deleteCopy);
    }
}
