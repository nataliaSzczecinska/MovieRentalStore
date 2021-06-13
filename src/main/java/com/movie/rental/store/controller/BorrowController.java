package com.movie.rental.store.controller;

import com.movie.rental.store.domain.dto.BorrowDto;
import com.movie.rental.store.exception.BorrowNotFoundException;
import com.movie.rental.store.exception.CopyNotFoundException;
import com.movie.rental.store.exception.CustomerNotFoundException;
import com.movie.rental.store.facade.BorrowFacade;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies/borrows")
public class BorrowController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowController.class);
    private final BorrowFacade borrowFacade;

    @RequestMapping(method = RequestMethod.GET, value = "/movie/{movieId}")
    public List<BorrowDto> getBorrowsByMovieId(@PathVariable Long movieId) {
        LOGGER.info("Get borrows by movie");
        return borrowFacade.getBorrowsByMovieId(movieId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    public List<BorrowDto> getBorrowsByUserId(@PathVariable Long userId) {
        LOGGER.info("Get borrows by user");
        return borrowFacade.getBorrowsByUserId(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createBorrow", consumes = APPLICATION_JSON_VALUE)
    public void createBorrow(@RequestBody BorrowDto borrowDto) throws CustomerNotFoundException, CopyNotFoundException {
        LOGGER.info("The borrow has just been created");
        borrowFacade.createBorrow(borrowDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{borrowId}/changeBorrowReturnDate")
    public BorrowDto changeBorrowReturnDate(@RequestParam String newBorrowDate, @PathVariable Long borrowId) throws BorrowNotFoundException {
        LOGGER.info("The borrow has just changed the return date");
        return borrowFacade.changeBorrowReturnDate(newBorrowDate, borrowId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{borrowId}")
    public void borrowIsFinish(@PathVariable Long borrowId, @RequestParam String finishReason) throws BorrowNotFoundException {
        LOGGER.info("The borrow has just been finished");
        borrowFacade.borrowIsFinished(borrowId, finishReason);
    }
}
