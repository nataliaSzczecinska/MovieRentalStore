package com.movie.rental.store.mapper.archive;

import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.dto.DeleteCopyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DeleteCopyMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteCopyMapper.class);

    public DeleteCopy mapToDeleteCopy(final DeleteCopyDto deleteCopyDto, final Movie movie) {
        LOGGER.info("Map DeleteCopyDto to DeleteCopy");
        return new DeleteCopy(deleteCopyDto.getDeleteCopyId(),
                deleteCopyDto.getPreviousCopyId(),
                movie,
                deleteCopyDto.getMediaType(),
                deleteCopyDto.getDeleteDate());
    }

    public DeleteCopyDto mapToDeleteCopyDto(final DeleteCopy deleteCopy) {
        LOGGER.info("Map DeleteCopy to DeleteCopyDto");
        return new DeleteCopyDto(deleteCopy.getDeleteCopyId(),
                deleteCopy.getPreviousCopyId(),
                deleteCopy.getMovie().getMovieId(),
                deleteCopy.getMediaType(),
                deleteCopy.getDeleteDate());
    }

    public List<DeleteCopyDto> mapToDeleteCopyDtoList(final List<DeleteCopy> deleteCopyList) {
        LOGGER.info("Map DeleteCopyList to DeleteCopyDtoList");
        return deleteCopyList.stream()
                .map(deleteCopy -> mapToDeleteCopyDto(deleteCopy))
                .collect(Collectors.toList());
    }
}
