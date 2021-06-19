package com.movie.rental.store.validator;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.service.CopyDbService;
import com.movie.rental.store.service.archive.BorrowArchiveDbService;
import com.movie.rental.store.service.archive.DeleteCopyDbService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class ArchiveValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveValidator.class);
    private final BorrowArchiveDbService borrowArchiveDbService;
    private final CopyDbService copyDbService;
    private final DeleteCopyDbService deleteCopyDbService;

    public List<BorrowArchive> getBorrowsArchiveByMovieId (Long movieId) {
        LOGGER.info("The borrow archive for movie id {} has been searching", movieId);
        List<BorrowArchive> borrowArchiveList = new ArrayList<>();

        for (BorrowArchive borrowArchive : borrowArchiveDbService.getAllBorrowArchive()) {
            Optional<Copy> copyOptional = copyDbService.getCopyById(borrowArchive.getCopyId());
            Optional<DeleteCopy> deleteCopyOptional = deleteCopyDbService.searchDeleteCopyByPreviousCopyId(borrowArchive.getCopyId());

            if ((copyOptional.isPresent() && movieId.equals(copyOptional.get().getMovie().getMovieId()))
            || (deleteCopyOptional.isPresent() && movieId.equals(deleteCopyOptional.get().getMovie().getMovieId()))) {
                borrowArchiveList.add(borrowArchive);
            }
        }
        return borrowArchiveList;
    }
}
