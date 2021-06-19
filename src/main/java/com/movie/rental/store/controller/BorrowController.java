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

    @GetMapping(value = "/movie/{movieId}")
    public List<BorrowDto> getBorrowsByMovieId(@PathVariable Long movieId) {
        LOGGER.info("Get borrows by movie");
        return borrowFacade.getBorrowsByMovieId(movieId);
    }

    @GetMapping(value = "/user/{userId}")
    public List<BorrowDto> getBorrowsByUserId(@PathVariable Long userId) {
        LOGGER.info("Get borrows by user");
        return borrowFacade.getBorrowsByUserId(userId);
    }

    @PostMapping(value = "/movie{movieId}/user{userId}/{mediaType}")
    public void createBorrow(@PathVariable Long movieId, @PathVariable Long userId, @PathVariable String mediaType) throws CustomerNotFoundException, MovieNotFoundException {
        borrowFacade.createBorrow(movieId, userId, mediaType);
    }

    @PutMapping(value = "/{borrowId}/changeBorrowReturnDate")
    public BorrowDto changeBorrowReturnDate(@RequestParam String newBorrowDate, @PathVariable Long borrowId) throws BorrowNotFoundException {
        LOGGER.info("The borrow has just changed the return date");
        return borrowFacade.changeBorrowReturnDate(newBorrowDate, borrowId);
    }

    @DeleteMapping(value = "/{borrowId}")
    public void borrowIsFinish(@PathVariable Long borrowId, @RequestParam String finishReason) throws BorrowNotFoundException {
        LOGGER.info("The borrow has just been finished");
        borrowFacade.borrowIsFinished(borrowId, finishReason);
    }
}
