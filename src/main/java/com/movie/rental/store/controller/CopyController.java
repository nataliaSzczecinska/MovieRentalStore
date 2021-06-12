package com.movie.rental.store.controller;

import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.domain.dto.CopyDto;
import com.movie.rental.store.exception.CopyNotFoundException;
import com.movie.rental.store.exception.MovieNotFoundException;
import com.movie.rental.store.facade.CopyFacade;
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
    private final CopyFacade copyFacade;

    @RequestMapping(method = RequestMethod.GET)
    public List<CopyDto> getAllCopies() {
        LOGGER.info("Get all copies");
        return copyFacade.getAllCopies();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}")
    public List<CopyDto> getAllCopiesByMovie(@PathVariable Long movieId) {
        LOGGER.info("Get copies by movie id " + movieId);
        return copyFacade.getAllCopiesByMovie(movieId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/availableCopies")
    public List<CopyDto> getAvailableCopies() {
        LOGGER.info("Get available copies");
        return copyFacade.getAvailableCopies();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}/availableCopies")
    public List<CopyDto> getAvailableCopiesByMovie(@PathVariable Long movieId) {
        LOGGER.info("Get available copies by movie id " + movieId);
        return copyFacade.getAvailableCopiesByMovie(movieId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}/dvdCopies")
    public List<CopyDto> getDVDCopiesByMovie(@PathVariable Long movieId) {
        LOGGER.info("Get DVD copies by movie id " + movieId);
        return copyFacade.getDVDCopiesByMovie(movieId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}/dvdAvailableCopies")
    public List<CopyDto> getDVDAvailableCopiesByMovie(@PathVariable Long movieId) {
        LOGGER.info("Get DVD available copies by movie id " + movieId);
        return copyFacade.getDVDAvailableCopiesByMovie(movieId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}/blu-rayCopies")
    public List<CopyDto> getBluRayCopiesByMovie(@PathVariable Long movieId) {
        LOGGER.info("Get Blu-Ray copies by movie id " + movieId);
        return copyFacade.getBluRayCopiesByMovie(movieId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}/blu-rayAvailableCopies")
    public List<CopyDto> getBluRayAvailableCopiesByMovie(@PathVariable Long movieId) {
        LOGGER.info("Get Blu-Ray available copies by movie id " + movieId);
        return copyFacade.getBluRayAvailableCopiesByMovie(movieId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/copy{copyId}")
    public CopyDto getCopyById(@PathVariable Long copyId) throws CopyNotFoundException {
        LOGGER.info("Get copy by id " + copyId);
        return copyFacade.getCopyById(copyId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createCopy(@RequestBody CopyDto copyDto) throws MovieNotFoundException {
        LOGGER.info("The copy of the film has just created");
        copyFacade.createCopy(copyDto);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public CopyDto updateCopy(@RequestBody CopyDto copyDto) throws MovieNotFoundException {
        LOGGER.info("The copy has just been updated");
        return copyFacade.updateCopy(copyDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{copyId}")
    public void deleteCopy(@PathVariable Long copyId) throws MovieNotFoundException, CopyNotFoundException {
        LOGGER.info("The copy has just been deleted");
        copyFacade.deleteCopy(copyId);
    }
}
