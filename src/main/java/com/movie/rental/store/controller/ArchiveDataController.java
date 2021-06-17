package com.movie.rental.store.controller;

import com.movie.rental.store.domain.dto.BorrowArchiveDto;
import com.movie.rental.store.domain.dto.DeleteCopyDto;
import com.movie.rental.store.domain.dto.DeleteCustomerDto;
import com.movie.rental.store.exception.CopyNotFoundException;
import com.movie.rental.store.facade.ArchiveDataFacade;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies/borrowsArchive")
public class ArchiveDataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowController.class);
    private final ArchiveDataFacade archiveDataFacade;

    @RequestMapping(method = RequestMethod.GET, value = "/user/{customerId}")
    public List<BorrowArchiveDto> getBorrowsArchiveByCustomerId(@PathVariable Long customerId) {
        LOGGER.info("Get borrows archive by customer with id " + customerId);
        return archiveDataFacade.getBorrowsArchiveByCustomerId(customerId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/movie/{movieId}")
    public List<BorrowArchiveDto> getBorrowsArchiveByMovieId(@PathVariable Long movieId) throws CopyNotFoundException {
        LOGGER.info("Get borrows archive by movie with id " + movieId);
        return archiveDataFacade.getBorrowsArchiveByMovieId(movieId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deleteCopies")
    public List<DeleteCopyDto> getDeleteCopies() {
        LOGGER.info("Get all delete copies");
        return archiveDataFacade.getAllDeleteCopies();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deleteCustomers")
    public List<DeleteCustomerDto> getDeleteCustomers() {
        LOGGER.info("Get all delete customers");
        return archiveDataFacade.getAllDeleteCustomers();
    }

    //    //getNumberOfBorrowsForEachMovie (pobiera listę czy coś - pomyśleć i zrobić z wykrozystaniem nameQuery moduł 17.4
    //    //getTop10Movies (pobiera listę najlepszych 10 tytułów filmów do wypożyczenia w ostatnim miesiącu - z borrow i borrowArchive)
}
