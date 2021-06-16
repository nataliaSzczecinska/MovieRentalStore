package com.movie.rental.store.mapper;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.dto.BorrowDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BorrowMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowMapper.class);

    public Borrow mapToBorrow (final BorrowDto borrowDto, final Copy copy, final Customer customer) {
        LOGGER.info("Map BorrowDto to Borrow");
        return  Borrow.builder()
                .borrowId(borrowDto.getBorrowId())
                .copy(copy)
                .customer(customer)
                .borrowDate(borrowDto.getBorrowDate())
                .returnDate(borrowDto.getReturnDate())
                .build();
    }

    public BorrowDto mapToBorrowDto (final Borrow borrow) {
        LOGGER.info("Map Borrow to BorrowDto");
        return  BorrowDto.builder()
                .borrowId(borrow.getBorrowId())
                .copyId(borrow.getCopy().getCopyId())
                .customerId(borrow.getCustomer().getCustomerId())
                .borrowDate(borrow.getBorrowDate())
                .returnDate(borrow.getReturnDate())
                .build();
    }

    public List<BorrowDto> mapToBorrowDtoList (final List<Borrow> borrowList) {
        LOGGER.info("Map BorrowList to BorrowDtoList");
        return borrowList.stream()
                .map(borrow -> mapToBorrowDto(borrow))
                .collect(Collectors.toList());
    }
}
