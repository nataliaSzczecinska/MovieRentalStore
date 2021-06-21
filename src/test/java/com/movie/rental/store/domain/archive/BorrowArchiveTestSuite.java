package com.movie.rental.store.domain.archive;

import com.movie.rental.store.domain.enums.BorrowArchiveType;
import com.movie.rental.store.repository.archive.BorrowArchiveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BorrowArchiveTestSuite {
    @Autowired
    private BorrowArchiveRepository borrowArchiveRepository;

    @Test
    public void findAllArchiveBorrows() {
        //Given
        BorrowArchive borrowArchive1 = BorrowArchive.builder()
                .previousBorrowId(1L)
                .copyId(2L)
                .customerId(3L)
                .borrowDate(LocalDate.of(2001, 1, 1))
                .returnDate(LocalDate.of(2001, 1, 7))
                .realReturnDate(LocalDate.of(2001, 1, 6))
                .borrowArchiveType(BorrowArchiveType.RETURN_ON_TIME)
                .build();
        BorrowArchive borrowArchive2 = BorrowArchive.builder()
                .previousBorrowId(3L)
                .copyId(6L)
                .customerId(13L)
                .borrowDate(LocalDate.of(2002, 2, 2))
                .returnDate(LocalDate.of(2002, 2, 9))
                .realReturnDate(LocalDate.of(2002, 2, 10))
                .borrowArchiveType(BorrowArchiveType.RETURN_LATE)
                .build();
        BorrowArchive borrowArchive3 = BorrowArchive.builder()
                .previousBorrowId(8L)
                .copyId(9L)
                .customerId(19L)
                .borrowDate(LocalDate.of(2003, 3, 2))
                .returnDate(LocalDate.of(2003, 3, 9))
                .realReturnDate(null)
                .borrowArchiveType(BorrowArchiveType.DELETED)
                .build();

        //When
        borrowArchiveRepository.save(borrowArchive1);
        borrowArchiveRepository.save(borrowArchive2);
        borrowArchiveRepository.save(borrowArchive3);
        List<BorrowArchive> borrowArchiveList = borrowArchiveRepository.findAll();

        //Then
        assertEquals(3, borrowArchiveList.size());

        //Clean-up
        borrowArchiveRepository.deleteAll();
    }

    @Test
    public void findArchiveBorrowsById() {
        //Given
        BorrowArchive borrowArchive = BorrowArchive.builder()
                .previousBorrowId(1L)
                .copyId(2L)
                .customerId(3L)
                .borrowDate(LocalDate.of(2001, 1, 1))
                .returnDate(LocalDate.of(2001, 1, 7))
                .realReturnDate(LocalDate.of(2001, 1, 6))
                .borrowArchiveType(BorrowArchiveType.RETURN_ON_TIME)
                .build();

        //When
        borrowArchiveRepository.save(borrowArchive);
        Long id = borrowArchive.getBorrowArchiveId();
        Optional<BorrowArchive> borrowArchiveOptional = borrowArchiveRepository.findById(id);

        //Then
        assertTrue(borrowArchiveOptional.isPresent());

        //Clean-up
        borrowArchiveRepository.deleteAll();
    }

    @Test
    public void saveBorrowArchiveTest() {
        //Given
        BorrowArchive borrowArchive = BorrowArchive.builder()
                .previousBorrowId(1L)
                .copyId(2L)
                .customerId(3L)
                .borrowDate(LocalDate.of(2001, 1, 1))
                .returnDate(LocalDate.of(2001, 1, 7))
                .realReturnDate(LocalDate.of(2001, 1, 6))
                .borrowArchiveType(BorrowArchiveType.RETURN_ON_TIME)
                .build();

        //When
        borrowArchiveRepository.save(borrowArchive);
        Long borrowArchiveId = borrowArchive.getBorrowArchiveId();
        Optional<BorrowArchive> borrowArchiveOptional = borrowArchiveRepository.findById(borrowArchiveId);

        //Then
        assertTrue(borrowArchiveOptional.isPresent());

        //Clean-up
        borrowArchiveRepository.deleteById(borrowArchiveId);
    }

    @Test
    public void findArchiveBorrowsByCustomerId() {
        //Given
        BorrowArchive borrowArchive1 = BorrowArchive.builder()
                .previousBorrowId(1L)
                .copyId(2L)
                .customerId(3L)
                .borrowDate(LocalDate.of(2001, 1, 1))
                .returnDate(LocalDate.of(2001, 1, 7))
                .realReturnDate(LocalDate.of(2001, 1, 6))
                .borrowArchiveType(BorrowArchiveType.RETURN_ON_TIME)
                .build();
        BorrowArchive borrowArchive2 = BorrowArchive.builder()
                .previousBorrowId(3L)
                .copyId(6L)
                .customerId(3L)
                .borrowDate(LocalDate.of(2002, 2, 2))
                .returnDate(LocalDate.of(2002, 2, 9))
                .realReturnDate(LocalDate.of(2002, 2, 10))
                .borrowArchiveType(BorrowArchiveType.RETURN_LATE)
                .build();
        BorrowArchive borrowArchive3 = BorrowArchive.builder()
                .previousBorrowId(8L)
                .copyId(9L)
                .customerId(19L)
                .borrowDate(LocalDate.of(2003, 3, 2))
                .returnDate(LocalDate.of(2003, 3, 9))
                .realReturnDate(null)
                .borrowArchiveType(BorrowArchiveType.DELETED)
                .build();

        borrowArchiveRepository.save(borrowArchive1);
        borrowArchiveRepository.save(borrowArchive2);
        borrowArchiveRepository.save(borrowArchive3);

        //When
        List<BorrowArchive> borrowArchiveList = borrowArchiveRepository.retrieveBorrowArchiveByCustomerId(3L);

        //Then
        assertEquals(2, borrowArchiveList.size());

        //Clean-up
        borrowArchiveRepository.deleteAll();
    }
}
