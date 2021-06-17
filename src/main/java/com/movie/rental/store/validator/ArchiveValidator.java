package com.movie.rental.store.validator;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.exception.CopyNotFoundException;
import com.movie.rental.store.service.CopyDbService;
import com.movie.rental.store.service.archive.BorrowArchiveDbService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ArchiveValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveValidator.class);
    private final BorrowArchiveDbService borrowArchiveDbService;
    private final CopyDbService copyDbService;

    public List<BorrowArchive> getBorrowsArchiveByMovieId (Long movieId) throws CopyNotFoundException {
        LOGGER.info("The borrow archive for movie id " + movieId + " has been searching");
        List<BorrowArchive> borrowArchiveList = new ArrayList<>();

        for (BorrowArchive borrowArchive : borrowArchiveDbService.getAllBorrowArchive()) {
            Copy copy = copyDbService.getCopyById(borrowArchive.getCopyId()).orElseThrow(CopyNotFoundException::new);
            if (movieId.equals(copy.getMovie().getMovieId())) {
                borrowArchiveList.add(borrowArchive);
            }
        }
        return borrowArchiveList;
    }
}
