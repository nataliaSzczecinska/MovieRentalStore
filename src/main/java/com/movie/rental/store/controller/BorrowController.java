package com.movie.rental.store.controller;

import com.movie.rental.store.domain.dto.BorrowDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies/borrows")
public class BorrowController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/movie/{movieId}")
    public List<BorrowDto> getBorrowsByMovieId(@PathVariable Long movieId) {
        LOGGER.info("Get borrows by movie");
        List<BorrowDto> movieList = new ArrayList<>();
        movieList.add(new BorrowDto(1L, 1L, 1L, LocalDate.of(2021,5,1), LocalDate.of(2021,9,1)));
        movieList.add(new BorrowDto(1L, 1L, 1L, LocalDate.of(2021,6,11), LocalDate.of(2021,9,5)));
        movieList.add(new BorrowDto(1L, 1L, 1L, LocalDate.of(2021,7,13), LocalDate.of(2021,12,23)));
        return movieList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    public List<BorrowDto> getBorrowsByUserId(@PathVariable Long userId) {
        LOGGER.info("Get borrows by user");
        List<BorrowDto> movieList = new ArrayList<>();
        movieList.add(new BorrowDto(1L, 1L, 1L, LocalDate.of(2021,5,1), LocalDate.of(2021,9,1)));
        movieList.add(new BorrowDto(1L, 1L, 1L, LocalDate.of(2021,6,11), LocalDate.of(2021,9,5)));
        movieList.add(new BorrowDto(1L, 1L, 1L, LocalDate.of(2021,7,13), LocalDate.of(2021,12,23)));
        return movieList;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createBorrow", consumes = APPLICATION_JSON_VALUE)
    public void createBorrow(@RequestBody BorrowDto borrowDto) {
        LOGGER.info("The borrow has just been created");
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{borrowId}/changeBorrowReturnDate")
    public BorrowDto changeBorrowReturnDate(@RequestParam String newBorrowDate, @PathVariable Long borrowId) {
        LOGGER.info("The borrow has just changed the return date");
        return new BorrowDto(1L, 1L, 1L, LocalDate.of(2010, 12,12), LocalDate.of(2019, 10, 9));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{borrowId}")
    public BorrowDto borrowIsFinish(@PathVariable BorrowDto borrowId, @RequestParam String finishReason) {
        LOGGER.info("The borrow has just been finished");
        return new BorrowDto(1L, 1L, 1L, LocalDate.of(2010, 12,12), LocalDate.of(2019, 10, 9));
    }
}
