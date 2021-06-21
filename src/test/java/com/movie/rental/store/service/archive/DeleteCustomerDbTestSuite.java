package com.movie.rental.store.service.archive;

import com.movie.rental.store.domain.archive.DeleteCustomer;
import com.movie.rental.store.repository.archive.DeleteCustomerRepository;
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
public class DeleteCustomerDbTestSuite {
    @Autowired
    private DeleteCustomerDbService deleteCustomerDbService;

    @Autowired
    private DeleteCustomerRepository deleteCustomerRepository;

    private List<DeleteCustomer> exampleDeleteCustomer() {
        List<DeleteCustomer> deleteCustomers = new ArrayList<>();
        deleteCustomers.add(DeleteCustomer.builder()
                .previousCustomerId(4L)
                .customerMailAddress("delete@mail.com")
                .createAccountDate(LocalDate.of(2001, 9, 10))
                .isBlocked(false)
                .deleteAccountDate(LocalDate.of(2019, 9, 8))
                .build());
        deleteCustomers.add(DeleteCustomer.builder()
                .previousCustomerId(6L)
                .customerMailAddress("delete.customer@mail.com")
                .createAccountDate(LocalDate.of(2002, 6, 14))
                .isBlocked(true)
                .deleteAccountDate(LocalDate.of(2020, 10, 5))
                .build());
        return deleteCustomers;
    }

    private List<Long> saveDeleteCustomersInDatabase(List<DeleteCustomer> deleteCustomers) {
        List<Long> ids = new ArrayList<>();

        for (DeleteCustomer deleteCustomer : deleteCustomers) {
            deleteCustomerRepository.save(deleteCustomer);
            ids.add(deleteCustomer.getDeleteCustomerId());
        }
        return ids;
    }

    @Test
    public void getAllDeleteCopiesTest() {
        //Given
        List<DeleteCustomer> deleteCustomers= exampleDeleteCustomer();
        saveDeleteCustomersInDatabase(deleteCustomers);

        //When
        List<DeleteCustomer> deleteCustomerList = deleteCustomerDbService.getAllDeletedCustomers();

        //Then
        assertEquals(2, deleteCustomerList.size());

        //Clean-up
        deleteCustomerRepository.deleteAll();
    }

    @Test
    public void getDeleteCustomerByIdTest() {
        //Given
        List<DeleteCustomer> deleteCustomers= exampleDeleteCustomer();
        List<Long> ids = saveDeleteCustomersInDatabase(deleteCustomers);

        //When
        Optional<DeleteCustomer> deleteCustomer1Optional = deleteCustomerDbService.getDeleteCustomerById(ids.get(1));
        Optional<DeleteCustomer> deleteCustomer2Optional = deleteCustomerDbService.getDeleteCustomerById(6L);

        //Then
        assertEquals(ids.get(1), deleteCustomer1Optional.get().getDeleteCustomerId());
        assertTrue(deleteCustomer1Optional.isPresent());
        assertFalse(deleteCustomer2Optional.isPresent());

        //Clean-up
        deleteCustomerRepository.deleteAll();
    }

    @Test
    public void saveDeleteCopyTest() {
        //Given
        List<DeleteCustomer> deleteCustomers= exampleDeleteCustomer();
        List<Long> ids = saveDeleteCustomersInDatabase(deleteCustomers);
        DeleteCustomer deleteCustomer = DeleteCustomer.builder()
                .previousCustomerId(6L)
                .customerMailAddress("add.delete.customer@mail.com")
                .createAccountDate(LocalDate.of(2012, 9, 10))
                .isBlocked(false)
                .deleteAccountDate(LocalDate.of(2021, 1, 9))
                .build();

        //When
        List<DeleteCustomer> deleteCustomersBeforeAdd = deleteCustomerDbService.getAllDeletedCustomers();
        deleteCustomerDbService.saveDeletedCustomer(deleteCustomer);
        Long id = deleteCustomer.getDeleteCustomerId();
        Optional<DeleteCustomer> deleteCustomerOptional = deleteCustomerDbService.getDeleteCustomerById(id);
        List<DeleteCustomer> deleteCustomersAfterAdd = deleteCustomerDbService.getAllDeletedCustomers();


        //Then
        assertEquals(id, deleteCustomerOptional.get().getDeleteCustomerId());
        assertTrue(deleteCustomerOptional.isPresent());
        assertEquals(2, deleteCustomersBeforeAdd.size());
        assertEquals(3, deleteCustomersAfterAdd.size());

        //Clean-up
        deleteCustomerRepository.deleteAll();
    }
}
