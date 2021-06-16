package com.movie.rental.store.mapper.archive;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.archive.DeleteCustomer;
import com.movie.rental.store.domain.enums.BorrowArchiveType;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Component
public class ToArchiveMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToArchiveMapper.class);

    public DeleteCopy mapToDeleteCopy(final Copy copy, final LocalDate localDate) {
        LOGGER.info("Map Copy to DeleteCopy");
        return  DeleteCopy.builder()
                .previousCopyId(copy.getCopyId())
                .movie(copy.getMovie())
                .mediaType(copy.getMediaType())
                .deleteDate(localDate)
                .build();
    }

    public DeleteCustomer mapToDeleteCustomer(final Customer customer, final LocalDate localDate) {
        return  DeleteCustomer.builder()
                .previousCustomerId(customer.getCustomerId())
                .customerMailAddress(customer.getCustomerMailAddress())
                .createAccountDate(customer.getCreateAccountDate())
                .isBlocked(customer.isBlocked())
                .deleteAccountDate(localDate)
                .build();
    }

    public BorrowArchive mapToBorrowArchive(final Borrow borrow, final LocalDate localDate, final BorrowArchiveType borrowArchiveType) {
        LOGGER.info("Map Borrow to BorrowArchive");
        return  BorrowArchive.builder()
                .previousBorrowId(borrow.getBorrowId())
                .copyId(borrow.getCopy().getCopyId())
                .customerId(borrow.getCustomer().getCustomerId())
                .borrowDate(borrow.getBorrowDate())
                .returnDate(borrow.getReturnDate())
                .realReturnDate(localDate)
                .borrowArchiveType(borrowArchiveType)
                .build();
    }
}
