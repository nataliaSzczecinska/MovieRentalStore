package com.movie.rental.store.mapper;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.dto.CopyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CopyMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CopyMapper.class);

    public Copy mapToCopy (final CopyDto copyDto, final Movie movie) {
        LOGGER.info("Map CopyDto to Copy");
        return new Copy(copyDto.getCopyId(),
                movie,
                copyDto.getCopyStatus(),
                copyDto.getMediaType());
    }

    public CopyDto mapToCopyDto (final Copy copy) {
        LOGGER.info("Map Copy to CopyDto");
        return new CopyDto(copy.getCopyId(),
                copy.getMovie().getMovieId(),
                copy.getCopyStatus(),
                copy.getMediaType());
    }

    public List<CopyDto> mapToCopyDtoList (final List<Copy> copies) {
        LOGGER.info("Map CopyList to CopyDtoList");
        return copies.stream()
                .map(copy -> mapToCopyDto(copy))
                .collect(Collectors.toList());
    }

}
