package com.movie.rental.store.validator;

import com.movie.rental.store.domain.dto.CopyDto;
import com.movie.rental.store.domain.enums.Status;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CopyValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(CopyValidator.class);

    public CopyDto checkCorrectStatusToCreate(CopyDto copyDto) {
        if (Status.BORROWED.equals(copyDto.getCopyStatus())) {
            LOGGER.warn("The copy cannot be BORROWED during the creation!");
        }
        copyDto.setCopyStatus(Status.AVAILABLE);
        return copyDto;
    }
}
