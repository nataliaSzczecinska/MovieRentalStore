package com.movie.rental.store.facade;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.dto.CopyDto;
import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import com.movie.rental.store.exception.CopyNotFoundException;
import com.movie.rental.store.exception.MovieNotFoundException;
import com.movie.rental.store.mapper.CopyMapper;
import com.movie.rental.store.mapper.archive.ToArchiveMapper;
import com.movie.rental.store.service.BorrowDbService;
import com.movie.rental.store.service.CopyDbService;
import com.movie.rental.store.service.MovieDbService;
import com.movie.rental.store.service.archive.DeleteCopyDbService;
import com.movie.rental.store.validator.CopyValidator;
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
    private final ToArchiveMapper toArchiveMapper;
    private final BorrowDbService borrowDbService;
    private final CopyValidator copyValidator;

    public List<CopyDto> getAllCopies() {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies());
    }

    public List<CopyDto> getAllCopiesByMovie(final Long movieId) {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
        .filter(copy -> copy.getMovie().getMovieId().equals(movieId))
        .collect(Collectors.toList()));
    }

    public List<CopyDto> getAvailableCopies() {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
                .filter(copy -> copy.getCopyStatus().equals(Status.AVAILABLE))
                .collect(Collectors.toList()));
    }

    public List<CopyDto> getAvailableCopiesByMovie(final Long movieId) {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
                .filter(copy -> copy.getMovie().getMovieId().equals(movieId))
                .filter(copy -> copy.getCopyStatus().equals(Status.AVAILABLE))
                .collect(Collectors.toList()));
    }

    public List<CopyDto> getDVDCopiesByMovie(final Long movieId) {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
                .filter(copy -> copy.getMediaType().equals(MediaType.DVD))
                .filter(copy -> copy.getMovie().getMovieId().equals(movieId))
                .collect(Collectors.toList()));
    }

    public List<CopyDto> getDVDAvailableCopiesByMovie(final Long movieId) {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
                .filter(copy -> copy.getMediaType().equals(MediaType.DVD))
                .filter(copy -> copy.getCopyStatus().equals(Status.AVAILABLE))
                .filter(copy -> copy.getMovie().getMovieId().equals(movieId))
                .collect(Collectors.toList()));
    }

    public List<CopyDto> getBluRayCopiesByMovie(final Long movieId) {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
                .filter(copy -> copy.getMediaType().equals(MediaType.BLU_RAY))
                .filter(copy -> copy.getMovie().getMovieId().equals(movieId))
                .collect(Collectors.toList()));
    }

    public List<CopyDto> getBluRayAvailableCopiesByMovie(final Long movieId) {
        return copyMapper.mapToCopyDtoList(copyDbService.getAllCopies().stream()
                .filter(copy -> copy.getMediaType().equals(MediaType.BLU_RAY))
                .filter(copy -> copy.getCopyStatus().equals(Status.AVAILABLE))
                .filter(copy -> copy.getMovie().getMovieId().equals(movieId))
                .collect(Collectors.toList()));
    }

    public CopyDto getCopyById(final Long copyId) throws CopyNotFoundException {
        return copyMapper.mapToCopyDto(copyDbService.getCopyById(copyId).orElseThrow(CopyNotFoundException::new));
    }

    public void createCopy(final CopyDto copyDto) throws MovieNotFoundException {
        copyDbService.saveCopy(copyMapper
                .mapToCopy(copyValidator.checkCorrectStatusToCreate(copyDto), movieDbService.getMovieById(copyDto
                        .getMovieId()).orElseThrow(MovieNotFoundException::new),
                        new ArrayList<>()));
    }

    public CopyDto updateCopy(final CopyDto copyDto) throws MovieNotFoundException {
        List<Borrow> borrows = borrowDbService.getAllBorrows().stream()
                .filter(borrow -> borrow.getCopy().getCopyId().equals(copyDto.getCopyId()))
                .collect(Collectors.toList());
        return copyMapper.mapToCopyDto(copyDbService.saveCopy(copyMapper
                .mapToCopy(copyDto, movieDbService.getMovieById(copyDto
                        .getMovieId()).orElseThrow(MovieNotFoundException::new),
                        borrows)));
    }

    public void deleteCopy(final Long copyId) throws CopyNotFoundException {
        Copy copy = copyDbService.getCopyById(copyId).orElseThrow(CopyNotFoundException::new);
        DeleteCopy deleteCopy = toArchiveMapper.mapToDeleteCopy(copy, LocalDate.now());
        deleteCopyDbService.saveDeletedCopy(deleteCopy);
        copyDbService.deleteCopyById(copyId);
    }
}
