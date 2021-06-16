package com.movie.rental.store.mapper.archive;

import com.movie.rental.store.domain.Customer;
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
        return BorrowArchive.builder()
                .borrowArchiveId(borrowArchiveDto.getBorrowArchiveId())
                .previousBorrowId(borrowArchiveDto.getPreviousBorrowId())
                .copyId(borrowArchiveDto.getCopyId())
                .customerId(borrowArchiveDto.getCustomerId())
                .borrowDate(borrowArchiveDto.getBorrowDate())
                .returnDate(borrowArchiveDto.getReturnDate())
                .realReturnDate(borrowArchiveDto.getRealReturnDate())
                .borrowArchiveType(borrowArchiveDto.getBorrowArchiveType())
                .build();
    }

    public BorrowArchiveDto mapToBorrowArchiveDto(final BorrowArchive borrowArchive) {
        LOGGER.info("Map BorrowArchive to BorrowArchiveDto");
        return  BorrowArchiveDto.builder()
                .borrowArchiveId(borrowArchive.getBorrowArchiveId())
                .previousBorrowId(borrowArchive.getPreviousBorrowId())
                .copyId(borrowArchive.getCopyId())
                .customerId(borrowArchive.getCustomerId())
                .borrowDate(borrowArchive.getBorrowDate())
                .returnDate(borrowArchive.getReturnDate())
                .realReturnDate(borrowArchive.getRealReturnDate())
                .borrowArchiveType(borrowArchive.getBorrowArchiveType())
                .build();
    }

    public List<BorrowArchiveDto> mapToBorrowArchiveDtoList(final List<BorrowArchive> borrowArchiveList) {
        LOGGER.info("Map BorrowArchiveList to BorrowArchiveDtoList");
        return borrowArchiveList.stream()
                .map(borrowArchive -> mapToBorrowArchiveDto(borrowArchive))
                .collect(Collectors.toList());
    }
}
