package com.movie.rental.store.controller;

import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.dto.CopyDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies/copies")
public class CopyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CopyController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}")
    public List<CopyDto> getAllCopies(@PathVariable Long movieId) {
        List<CopyDto> movieList = new ArrayList<>();
        movieList.add(new CopyDto(1L, 1L, Status.AVAILABLE, MediaType.DVD));
        movieList.add(new CopyDto(2L, 2L, Status.AVAILABLE, MediaType.DVD));
        movieList.add(new CopyDto(3L, 2L, Status.BORROWED, MediaType.DVD));
        return movieList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}/availableCopies")
    public List<CopyDto> getAvailableCopies(@PathVariable Long movieId) {
        List<CopyDto> movieList = new ArrayList<>();
        movieList.add(new CopyDto(1L, 1L, Status.AVAILABLE, MediaType.BLU_RAY));
        movieList.add(new CopyDto(2L, 2L, Status.AVAILABLE, MediaType.BLU_RAY));
        return movieList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}/dvdCopies")
    public List<CopyDto> getDvdCopies(@PathVariable Long movieId) {
        List<CopyDto> movieList = new ArrayList<>();
        movieList.add(new CopyDto(1L, 1L, Status.AVAILABLE, MediaType.DVD));
        movieList.add(new CopyDto(2L, 2L, Status.AVAILABLE, MediaType.DVD));
        return movieList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}/blu-rayCopies")
    public List<CopyDto> getBluRayCopies(@PathVariable Long movieId) {
        List<CopyDto> movieList = new ArrayList<>();
        movieList.add(new CopyDto(1L, 1L, Status.AVAILABLE, MediaType.BLU_RAY));
        movieList.add(new CopyDto(2L, 2L, Status.AVAILABLE, MediaType.BLU_RAY));
        return movieList;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createCopy(@RequestBody CopyDto copyDto) {
        LOGGER.info("The copy of the film has just created");
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public CopyDto updateCopy(@RequestBody CopyDto copyDto) {
        LOGGER.info("The copy has just been updated");
        return new CopyDto(1L, 1L, Status.AVAILABLE, MediaType.DVD);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{copyId}")
    public void deleteCopy(@PathVariable Long copyId) {
        LOGGER.info("The copy has just been deleted");
    }
}
