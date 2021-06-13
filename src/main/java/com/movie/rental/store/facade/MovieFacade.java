package com.movie.rental.store.facade;

import com.movie.rental.store.domain.dto.MovieDto;
import com.movie.rental.store.domain.enums.Type;
import com.movie.rental.store.exception.MovieNotFoundException;
import com.movie.rental.store.mapper.MovieMapper;
import com.movie.rental.store.repository.MovieRepository;
import com.movie.rental.store.service.CopyDbService;
import com.movie.rental.store.service.MovieDbService;
import com.movie.rental.store.service.archive.DeleteCopyDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MovieFacade {
    private final MovieDbService movieDbService;
    private final MovieMapper movieMapper;
    private final CopyDbService copyDbService;
    private final DeleteCopyDbService deleteCopyDbService;

    public List<MovieDto> getAllMovies() {
        return movieMapper.mapToMovieDtoList(movieDbService.getAllMovie());
    }

    public MovieDto getMovieById(final Long movieId) throws MovieNotFoundException {
        return movieMapper.mapToMovieDto(movieDbService.getMovieById(movieId).orElseThrow(MovieNotFoundException::new));
    }

    public List<MovieDto> getMoviesByTitle(final String title) {
        return movieMapper.mapToMovieDtoList(movieDbService.searchMovieByTitle(title));
    }

    public List<MovieDto> getMoviesByDirector(final String director) {
        return movieMapper.mapToMovieDtoList(movieDbService.searchMovieByDirector(director));
    }

    public List<MovieDto> getMoviesByDescription(final String description) {
        return movieMapper.mapToMovieDtoList(movieDbService.searchMovieByDescription(description));
    }

    public List<MovieDto> getMoviesByYear(final int year) {
        return movieMapper.mapToMovieDtoList(movieDbService.getAllMovie().stream()
                .filter(movie -> movie.getMovieYear() == year)
                .collect(Collectors.toList()));
    }

    public List<MovieDto> getMoviesByType(final String type) {
        return movieMapper.mapToMovieDtoList(movieDbService.getAllMovie().stream()
                .filter(movie -> movie.getMovieType().equals(Type.valueOf(type)))
                .collect(Collectors.toList()));
    }

    public void createMovie(final MovieDto movieDto) {
        movieDbService.saveMovie(movieMapper.mapToMovie(movieDto, new ArrayList<>(), new ArrayList<>()));
    }

    public MovieDto updateMovie(final MovieDto movieDto) {
        return movieMapper.mapToMovieDto(movieDbService.saveMovie(movieMapper.mapToMovie(movieDto,
                copyDbService.getAllCopies().stream()
                        .filter(copy -> copy.getMovie().getMovieId() == movieDto.getMovieId())
                        .collect(Collectors.toList()),
                deleteCopyDbService.getAllDeletedCopies().stream()
                        .filter(deleteCopy -> deleteCopy.getMovie().getMovieId() == movieDto.getMovieId())
                        .collect(Collectors.toList()))));
    }
}
