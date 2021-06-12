package com.movie.rental.store.facade;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.dto.CopyDto;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.exception.CopyNotFoundException;
import com.movie.rental.store.exception.MovieNotFoundException;
import com.movie.rental.store.mapper.CopyMapper;
import com.movie.rental.store.service.CopyDbService;
import com.movie.rental.store.service.MovieDbService;
import com.movie.rental.store.service.archive.DeleteCopyDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CopyFacade {
    private final CopyDbService copyDbService;
    private final CopyMapper copyMapper;
    private final MovieDbService movieDbService;
    private final DeleteCopyDbService deleteCopyDbService;


    public List<CopyDto> getAllCopies() {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies());
    }

    public List<CopyDto> getAllCopiesByMovie(final Long movieId) {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
        .filter(copy -> copy.getMovie().getMovieId() == movieId)
        .collect(Collectors.toList()));
    }

    public List<CopyDto> getAvailableCopies() {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
                .filter(copy -> copy.getCopyStatus().equals(Status.AVAILABLE))
                .collect(Collectors.toList()));
    }

    public List<CopyDto> getAvailableCopiesByMovie(final Long movieId) {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
                .filter(copy -> copy.getMovie().getMovieId() == movieId)
                .filter(copy -> copy.getCopyStatus().equals(Status.AVAILABLE))
                .collect(Collectors.toList()));
    }

    public List<CopyDto> getDVDCopiesByMovie(final Long movieId) {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
                .filter(copy -> copy.getMediaType().equals(MediaType.DVD))
                .filter(copy -> copy.getMovie().getMovieId() == movieId)
                .collect(Collectors.toList()));
    }

    public List<CopyDto> getDVDAvailableCopiesByMovie(final Long movieId) {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
                .filter(copy -> copy.getMediaType().equals(MediaType.DVD))
                .filter(copy -> copy.getCopyStatus().equals(Status.AVAILABLE))
                .filter(copy -> copy.getMovie().getMovieId() == movieId)
                .collect(Collectors.toList()));
    }

    public List<CopyDto> getBluRayCopiesByMovie(final Long movieId) {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
                .filter(copy -> copy.getMediaType().equals(MediaType.BLU_RAY))
                .filter(copy -> copy.getMovie().getMovieId() == movieId)
                .collect(Collectors.toList()));
    }

    public List<CopyDto> getBluRayAvailableCopiesByMovie(final Long movieId) {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
                .filter(copy -> copy.getMediaType().equals(MediaType.BLU_RAY))
                .filter(copy -> copy.getCopyStatus().equals(Status.AVAILABLE))
                .filter(copy -> copy.getMovie().getMovieId() == movieId)
                .collect(Collectors.toList()));
    }

    public CopyDto getCopyById(final Long copyId) throws CopyNotFoundException {
        return copyMapper.mapToCopyDto(copyDbService.getCopyById(copyId).orElseThrow(CopyNotFoundException::new));
    }

    public void createCopy(final CopyDto copyDto) throws MovieNotFoundException {
        copyDbService.saveCopy(copyMapper
                .mapToCopy(copyDto, movieDbService.getMovieById(copyDto
                        .getMovieId()).orElseThrow(MovieNotFoundException::new)));
    }

    public CopyDto updateCopy(final CopyDto copyDto) throws MovieNotFoundException {
        return copyMapper.mapToCopyDto(copyDbService.saveCopy(copyMapper
                .mapToCopy(copyDto, movieDbService.getMovieById(copyDto
                        .getMovieId()).orElseThrow(MovieNotFoundException::new))));
    }

    public void deleteCopy(final Long copyId) throws CopyNotFoundException, MovieNotFoundException {
        Copy copy = copyDbService.getCopyById(copyId).orElseThrow(CopyNotFoundException::new);
        Movie movie = movieDbService.getMovieById(copy.getMovie().getMovieId()).orElseThrow(MovieNotFoundException::new);
        DeleteCopy deleteCopy = new DeleteCopy(copyId, movie, copy.getMediaType(), LocalDate.now());
        deleteCopyDbService.saveDeletedMovie(deleteCopy);
        copyDbService.deleteCopyById(copyId);
    }
}
