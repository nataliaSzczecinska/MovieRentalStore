package com.movie.rental.store.mapper.archive;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.archive.DeleteCustomer;
import com.movie.rental.store.domain.enums.BorrowArchiveType;
import com.movie.rental.store.domain.enums.MediaType;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Component
public class ToArchiveMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToArchiveMapper.class);

    public DeleteCopy mapToDeleteCopy(final Copy copy, final LocalDate localDate) {
        LOGGER.info("Map Copy to DeleteCopy");
        return new DeleteCopy(copy.getCopyId(),
                copy.getMovie(),
                copy.getMediaType(),
                localDate);
    }

    public DeleteCustomer mapToDeleteCustomer(final Customer customer, final LocalDate localDate) {
        return new DeleteCustomer(customer.getCustomerId(),
                customer.getCustomerMailAddress(),
                customer.getCreateAccountDate(),
                customer.isBlocked(),
                localDate);
    }

    public BorrowArchive mapToBorrowArchive(final Borrow borrow, final LocalDate localDate, final BorrowArchiveType borrowArchiveType) {
        LOGGER.info("Map Borrow to BorrowArchive");
        return new BorrowArchive(borrow.getBorrowId(),
                borrow.getCopy().getCopyId(),
                borrow.getCustomer().getCustomerId(),
                borrow.getBorrowDate(),
                borrow.getReturnDate(),
                localDate,
                borrowArchiveType);
    }
}
