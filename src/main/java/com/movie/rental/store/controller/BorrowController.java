package com.movie.rental.store.controller;

import com.movie.rental.store.domain.dto.BorrowDto;
import com.movie.rental.store.exception.BorrowNotFoundException;
import com.movie.rental.store.exception.CustomerNotFoundException;
import com.movie.rental.store.exception.MovieNotFoundException;
import com.movie.rental.store.facade.BorrowFacade;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies/borrows")
public class BorrowController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowController.class);
    private final BorrowFacade borrowFacade;

    @GetMapping
    public List<BorrowDto> getAllBorrows() {
        LOGGER.info("Get all borrows");
        return borrowFacade.getAllBorrows();
    }

    @GetMapping(value = "/{borrowId}")
    public BorrowDto getBorrowById(@PathVariable  final Long borrowId) throws BorrowNotFoundException {
        LOGGER.info("Get all borrows");
        return borrowFacade.getBorrowById(borrowId);
    }

    @GetMapping(value = "/movie/{movieId}")
    public List<BorrowDto> getBorrowsByMovieId(@PathVariable Long movieId) {
        LOGGER.info("Get borrows by movie");
        return borrowFacade.getBorrowsByMovieId(movieId);
    }

    @GetMapping(value = "/customer/{customerId}")
    public List<BorrowDto> getBorrowsByCustomerId(@PathVariable Long customerId) {
        LOGGER.info("Get borrows by customer");
        return borrowFacade.getBorrowsByCustomerId(customerId);
    }

    @PostMapping(value = "/movie={movieId}/customer={customerId}/{mediaType}")
    public void createBorrow(@PathVariable Long movieId, @PathVariable Long customerId, @PathVariable String mediaType) throws CustomerNotFoundException, MovieNotFoundException {
        borrowFacade.createBorrow(movieId, customerId, mediaType);
    }

    @PutMapping(value = "/{borrowId}/{newBorrowDate}")
    public BorrowDto changeBorrowReturnDate(@PathVariable String newBorrowDate, @PathVariable Long borrowId) throws BorrowNotFoundException {
        LOGGER.info("The borrow has just changed the return date");
        return borrowFacade.changeBorrowReturnDate(newBorrowDate, borrowId);
    }

    @DeleteMapping(value = "/{borrowId}/{finishReason}")
    public void borrowIsFinish(@PathVariable Long borrowId, @PathVariable String finishReason) throws BorrowNotFoundException {
        LOGGER.info("The borrow has just been finished");
        borrowFacade.borrowIsFinished(borrowId, finishReason);
    }
}
