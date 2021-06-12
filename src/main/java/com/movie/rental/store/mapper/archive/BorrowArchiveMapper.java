package com.movie.rental.store.mapper.archive;

import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.domain.dto.BorrowArchiveDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class BorrowArchiveMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowArchiveMapper.class);

    public BorrowArchive mapToBorrowArchive(final BorrowArchiveDto borrowArchiveDto) {
        LOGGER.info("Map BorrowArchiveDto to BorrowArchive");
        return new BorrowArchive(borrowArchiveDto.getBorrowArchiveId(),
                borrowArchiveDto.getPreviousBorrowId(),
                borrowArchiveDto.getCopyId(),
                borrowArchiveDto.getCustomerId(),
                borrowArchiveDto.getBorrowDate(),
                borrowArchiveDto.getReturnDate(),
                borrowArchiveDto.getRealReturnDate(),
                borrowArchiveDto.getBorrowArchiveType());
    }

    public BorrowArchiveDto mapToBorrowArchiveDto(final BorrowArchive borrowArchive) {
        LOGGER.info("Map BorrowArchive to BorrowArchiveDto");
        return new BorrowArchiveDto(borrowArchive.getBorrowArchiveId(),
                borrowArchive.getPreviousBorrowId(),
                borrowArchive.getCopyId(),
                borrowArchive.getCustomerId(),
                borrowArchive.getBorrowDate(),
                borrowArchive.getReturnDate(),
                borrowArchive.getRealReturnDate(),
                borrowArchive.getBorrowArchiveType());
    }

    public List<BorrowArchiveDto> mapToBorrowArchiveDtoList(final List<BorrowArchive> borrowArchiveList) {
        LOGGER.info("Map BorrowArchiveList to BorrowArchiveDtoList");
        return borrowArchiveList.stream()
                .map(borrowArchive -> mapToBorrowArchiveDto(borrowArchive))
                .collect(Collectors.toList());
    }
}
