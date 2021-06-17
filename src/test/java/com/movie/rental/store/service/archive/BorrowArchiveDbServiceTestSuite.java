package com.movie.rental.store.service.archive;

import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.domain.enums.BorrowArchiveType;
import com.movie.rental.store.repository.archive.BorrowArchiveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BorrowArchiveDbServiceTestSuite {
    @Autowired
    private BorrowArchiveRepository borrowArchiveRepository;

    @Autowired
    private BorrowArchiveDbService borrowArchiveDbService;

    private List<BorrowArchive> exampleBorrowArchiveList() {
        List<BorrowArchive> borrowArchiveList = new ArrayList<>();
        borrowArchiveList.add(BorrowArchive.builder()
                .previousBorrowId(1L)
                .copyId(3L)
                .customerId(5L)
                .borrowDate(LocalDate.of(2009, 9, 12))
                .returnDate(LocalDate.of(2009, 9, 19))
                .realReturnDate(LocalDate.of(2009, 9, 15))
                .borrowArchiveType(BorrowArchiveType.RETURN_ON_TIME)
                .build());
        borrowArchiveList.add(BorrowArchive.builder()
                .previousBorrowId(3L)
                .copyId(7L)
                .customerId(9L)
                .borrowDate(LocalDate.of(2010, 10, 2))
                .returnDate(LocalDate.of(2010, 10, 9))
                .realReturnDate(LocalDate.of(2010, 10, 15))
                .borrowArchiveType(BorrowArchiveType.RETURN_LATE)
                .build());
        borrowArchiveList.add(BorrowArchive.builder()
                .previousBorrowId(5L)
                .copyId(9L)
                .customerId(2L)
                .borrowDate(LocalDate.of(2010, 10, 2))
                .returnDate(LocalDate.of(2010, 10, 9))
                .borrowArchiveType(BorrowArchiveType.DELETED)
                .build());
        borrowArchiveList.add(BorrowArchive.builder()
                .previousBorrowId(11L)
                .copyId(5L)
                .customerId(2L)
                .borrowDate(LocalDate.of(2011, 5, 22))
                .returnDate(LocalDate.of(2011, 6, 29))
                .borrowArchiveType(BorrowArchiveType.COPY_DESTROY_OR_LOST)
                .build());
        borrowArchiveList.add(BorrowArchive.builder()
                .previousBorrowId(19L)
                .copyId(1L)
                .customerId(2L)
                .borrowDate(LocalDate.of(2011, 5, 22))
                .returnDate(LocalDate.of(2011, 6, 29))
                .realReturnDate(LocalDate.of(2011, 5, 27))
                .borrowArchiveType(BorrowArchiveType.RETURN_ON_TIME)
                .build());
        return borrowArchiveList;
    }

    private List<Long> saveBorrowArchiveInDatabase(List<BorrowArchive> borrowArchiveList) {
        List<Long> ids = new ArrayList<>();

        for (BorrowArchive borrowArchive : borrowArchiveList) {
            borrowArchiveDbService.saveBorrowArchive(borrowArchive);
            ids.add(borrowArchive.getBorrowArchiveId());
        }

        return ids;
    }

    @Test
    public void getAllBorrowArchiveTest() {
        //Given
        List<BorrowArchive> borrowArchives = exampleBorrowArchiveList();
        saveBorrowArchiveInDatabase(borrowArchives);

        //When
        List<BorrowArchive> getBorrowArchiveList = borrowArchiveDbService.getAllBorrowArchive();

        //Then
        assertEquals(5, getBorrowArchiveList.size());

        //Clean-up
        borrowArchiveRepository.deleteAll();
    }

    @Test
    public void getBorrowArchiveByIdTest() {
        //Given
        List<BorrowArchive> borrowArchives = exampleBorrowArchiveList();
        List<Long> ids = saveBorrowArchiveInDatabase(borrowArchives);

        //When
        Optional<BorrowArchive> borrowArchive1Optional = borrowArchiveDbService.getBorrowArchiveById(ids.get(2));
        Optional<BorrowArchive> borrowArchive2Optional = borrowArchiveDbService.getBorrowArchiveById(189L);

        //Then
        assertTrue(borrowArchive1Optional.isPresent());
        assertFalse(borrowArchive2Optional.isPresent());
        assertEquals(ids.get(2), borrowArchive1Optional.get().getBorrowArchiveId());

        //Clean-up
        borrowArchiveRepository.deleteAll();
    }

    @Test
    public void saveBorrowArchiveTest() {
        //Given
        List<BorrowArchive> borrowArchives = exampleBorrowArchiveList();
        saveBorrowArchiveInDatabase(borrowArchives);
        BorrowArchive borrowArchive = BorrowArchive.builder()
                .previousBorrowId(29L)
                .copyId(2L)
                .customerId(2L)
                .borrowDate(LocalDate.of(2020, 5, 22))
                .returnDate(LocalDate.of(2020, 6, 29))
                .realReturnDate(LocalDate.of(2021, 5, 27))
                .borrowArchiveType(BorrowArchiveType.RETURN_LATE)
                .build();

        //When
        List<BorrowArchive> borrowArchiveListBeforeAdd = borrowArchiveDbService.getAllBorrowArchive();
        borrowArchiveDbService.saveBorrowArchive(borrowArchive);
        Long borrowArchiveId = borrowArchive.getBorrowArchiveId();
        Optional<BorrowArchive> borrowArchiveOptional = borrowArchiveDbService.getBorrowArchiveById(borrowArchiveId);
        List<BorrowArchive> borrowArchiveListAfterAdd = borrowArchiveDbService.getAllBorrowArchive();

        //Then
        assertTrue(borrowArchiveOptional.isPresent());
        assertEquals(borrowArchiveId, borrowArchiveOptional.get().getBorrowArchiveId());
        assertEquals(5, borrowArchiveListBeforeAdd.size());
        assertEquals(6, borrowArchiveListAfterAdd.size());

        //Clean-up
        borrowArchiveRepository.deleteAll();
    }

    @Test
    public void searchBorrowArchiveByCustomerIdTest() {
        //Given
        List<BorrowArchive> borrowArchives = exampleBorrowArchiveList();
        saveBorrowArchiveInDatabase(borrowArchives);

        //When
        List<BorrowArchive> borrowArchiveListByCustomer = borrowArchiveDbService.searchBorrowArchiveByCustomerId(2L);

        for (BorrowArchive borrowArchive : borrowArchiveListByCustomer) {
            System.out.println(borrowArchive);
        }

        //Then
        assertEquals(3, borrowArchiveListByCustomer.size());

        //Clean-up
        borrowArchiveRepository.deleteAll();
    }
}
