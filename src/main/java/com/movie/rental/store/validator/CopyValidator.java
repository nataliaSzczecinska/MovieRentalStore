package com.movie.rental.store.validator;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.dto.CopyDto;
import com.movie.rental.store.domain.enums.BorrowArchiveType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.exception.CopyNotFoundException;
import com.movie.rental.store.mapper.archive.ToArchiveMapper;
import com.movie.rental.store.service.archive.BorrowArchiveDbService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class CopyValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(CopyValidator.class);
    private final BorrowArchiveDbService borrowArchiveDbService;
    private final ToArchiveMapper toArchiveMapper;

    public CopyDto checkCorrectStatusToCreate(CopyDto copyDto) {
        if (Status.BORROWED.equals(copyDto.getCopyStatus())) {
            LOGGER.warn("The copy cannot be BORROWED during the creation!");
        }
        copyDto.setCopyStatus(Status.AVAILABLE);
        return copyDto;
    }

    public void createBorrowArchiveIfDeleteTheCopyWithIsBorrowNow(Copy copy) throws CopyNotFoundException {
        if (copy.getCopyStatus().equals(Status.BORROWED)) {
            LOGGER.info("The copy is borrowed now. The borrow will be archive with note \"COPY_DESTROY_OR_LOST\"");
            borrowArchiveDbService.saveBorrowArchive(toArchiveMapper.mapToBorrowArchive(copy.getBorrow(), LocalDate.now(), BorrowArchiveType.COPY_DESTROY_OR_LOST));
        }
    }
}
