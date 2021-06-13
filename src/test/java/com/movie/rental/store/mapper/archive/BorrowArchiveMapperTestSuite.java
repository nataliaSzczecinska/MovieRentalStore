package com.movie.rental.store.mapper.archive;

import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.domain.dto.BorrowArchiveDto;
import com.movie.rental.store.domain.enums.BorrowArchiveType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BorrowArchiveMapperTestSuite {
    @Autowired
    private BorrowArchiveMapper borrowArchiveMapper;

    @Test
    public void mapToBorrowArchive() {
        //Given
        BorrowArchiveDto borrowArchiveDto = new BorrowArchiveDto(5L, 1L, 2L, 3L, LocalDate.of(2000, 2, 22), LocalDate.of(2002, 3, 23), LocalDate.of(2010, 6, 25), BorrowArchiveType.RETURN_ON_TIME);

        //When
        BorrowArchive borrowArchive = borrowArchiveMapper.mapToBorrowArchive(borrowArchiveDto);

        //Given
        assertEquals(5L, borrowArchive.getBorrowArchiveId());
        assertEquals(1L, borrowArchive.getPreviousBorrowId());
        assertEquals(2L, borrowArchive.getCopyId());
        assertEquals(3L, borrowArchive.getCustomerId());
        assertEquals(LocalDate.of(2000, 2, 22), borrowArchive.getBorrowDate());
        assertEquals(LocalDate.of(2002, 3, 23), borrowArchive.getReturnDate());
        assertEquals(LocalDate.of(2010, 6, 25), borrowArchive.getRealReturnDate());
        assertEquals(BorrowArchiveType.RETURN_ON_TIME, borrowArchive.getBorrowArchiveType());
    }

    @Test
    public void mapToBorrowArchiveDto() {
        //Given
        BorrowArchive borrowArchive = new BorrowArchive(5L, 1L, 2L, 3L, LocalDate.of(2000, 2, 22), LocalDate.of(2002, 3, 23), LocalDate.of(2010, 6, 25), BorrowArchiveType.RETURN_ON_TIME);

        //When
        BorrowArchiveDto borrowArchiveDto = borrowArchiveMapper.mapToBorrowArchiveDto(borrowArchive);

        //Given
        assertEquals(5L, borrowArchiveDto.getBorrowArchiveId());
        assertEquals(1L, borrowArchiveDto.getPreviousBorrowId());
        assertEquals(2L, borrowArchiveDto.getCopyId());
        assertEquals(3L, borrowArchiveDto.getCustomerId());
        assertEquals(LocalDate.of(2000, 2, 22), borrowArchiveDto.getBorrowDate());
        assertEquals(LocalDate.of(2002, 3, 23), borrowArchiveDto.getReturnDate());
        assertEquals(LocalDate.of(2010, 6, 25), borrowArchiveDto.getRealReturnDate());
        assertEquals(BorrowArchiveType.RETURN_ON_TIME, borrowArchiveDto.getBorrowArchiveType());
    }

    @Test
    public void mapToBorrowArchiveDtoList() {
        //Given
        BorrowArchive borrowArchive1 = new BorrowArchive(5L, 10L, 2L, 1L, LocalDate.of(2001, 1, 21), LocalDate.of(2001, 2, 11), LocalDate.of(2011, 5, 15), BorrowArchiveType.RETURN_ON_TIME);
        BorrowArchive borrowArchive2 = new BorrowArchive(6L, 3L, 5L, 2L, LocalDate.of(2002, 2, 22), LocalDate.of(2001, 3, 12), LocalDate.of(2012, 6, 21), BorrowArchiveType.RETURN_LATE);
        BorrowArchive borrowArchive3 = new BorrowArchive(7L, 5L, 7L, 3L, LocalDate.of(2003, 3, 23), LocalDate.of(2001, 4, 13), LocalDate.of(2013, 7, 26), BorrowArchiveType.COPY_DESTROY_OR_LOST);
        List<BorrowArchive> borrowArchiveList = Arrays.asList(borrowArchive1, borrowArchive2, borrowArchive3);

        //When
        List<BorrowArchiveDto> borrowArchiveDtoList = borrowArchiveMapper.mapToBorrowArchiveDtoList(borrowArchiveList);

        //Given
        assertEquals(3, borrowArchiveDtoList.size());
        assertEquals(5L, borrowArchiveDtoList.get(0).getBorrowArchiveId());
        assertEquals(10L, borrowArchiveDtoList.get(0).getPreviousBorrowId());
        assertEquals(2L, borrowArchiveDtoList.get(0).getCopyId());
        assertEquals(1L, borrowArchiveDtoList.get(0).getCustomerId());
        assertEquals(LocalDate.of(2001, 1, 21), borrowArchiveDtoList.get(0).getBorrowDate());
        assertEquals(LocalDate.of(2001, 2, 11), borrowArchiveDtoList.get(0).getReturnDate());
        assertEquals(LocalDate.of(2011, 5, 15), borrowArchiveDtoList.get(0).getRealReturnDate());
        assertEquals(BorrowArchiveType.RETURN_ON_TIME, borrowArchiveDtoList.get(0).getBorrowArchiveType());
        assertEquals(6L, borrowArchiveDtoList.get(1).getBorrowArchiveId());
        assertEquals(3L, borrowArchiveDtoList.get(1).getPreviousBorrowId());
        assertEquals(5L, borrowArchiveDtoList.get(1).getCopyId());
        assertEquals(2L, borrowArchiveDtoList.get(1).getCustomerId());
        assertEquals(LocalDate.of(2002, 2, 22), borrowArchiveDtoList.get(1).getBorrowDate());
        assertEquals(LocalDate.of(2001, 3, 12), borrowArchiveDtoList.get(1).getReturnDate());
        assertEquals(LocalDate.of(2012, 6, 21), borrowArchiveDtoList.get(1).getRealReturnDate());
        assertEquals(BorrowArchiveType.RETURN_LATE, borrowArchiveDtoList.get(1).getBorrowArchiveType());
        assertEquals(7L, borrowArchiveDtoList.get(2).getBorrowArchiveId());
        assertEquals(5L, borrowArchiveDtoList.get(2).getPreviousBorrowId());
        assertEquals(7L, borrowArchiveDtoList.get(2).getCopyId());
        assertEquals(3L, borrowArchiveDtoList.get(2).getCustomerId());
        assertEquals(LocalDate.of(2003, 3, 23), borrowArchiveDtoList.get(2).getBorrowDate());
        assertEquals(LocalDate.of(2001, 4, 13), borrowArchiveDtoList.get(2).getReturnDate());
        assertEquals(LocalDate.of(2013, 7, 26), borrowArchiveDtoList.get(2).getRealReturnDate());
        assertEquals(BorrowArchiveType.COPY_DESTROY_OR_LOST, borrowArchiveDtoList.get(2).getBorrowArchiveType());
    }
}
