package com.movie.rental.store.mapper.archive;

import com.movie.rental.store.domain.archive.DeleteCustomer;
import com.movie.rental.store.domain.dto.DeleteCustomerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DeleteCustomerMapperTestSuite {
    @Autowired
    private DeleteCustomerMapper deleteCustomerMapper;

    @Test
    public void mapToDeleteCustomer() {
        //Given
        DeleteCustomerDto deleteCustomerDto = new DeleteCustomerDto(1L, 3L, "test@mail.com", LocalDate.of(2001, 1, 3), true, LocalDate.of(2009, 9 ,2));

        //When
        DeleteCustomer deleteCustomer = deleteCustomerMapper.mapToDeleteCustomer(deleteCustomerDto);

        //Then
        assertEquals(1L, deleteCustomer.getDeleteCustomerId());
        assertEquals(3L, deleteCustomer.getPreviousCustomerId());
        assertEquals("test@mail.com", deleteCustomer.getCustomerMailAddress());
        assertEquals(LocalDate.of(2001, 1, 3), deleteCustomer.getCreateAccountDate());
        assertEquals(LocalDate.of(2009, 9 ,2), deleteCustomer.getDeleteAccountDate());
        assertTrue(deleteCustomer.isBlocked());
    }

    @Test
    public void mapToDeleteCustomerDto() {
        //Given
        DeleteCustomer deleteCustomer = new DeleteCustomer(1L, 3L, "test@mail.com", LocalDate.of(2001, 1, 3), true, LocalDate.of(2009, 9 ,2));

        //When
        DeleteCustomerDto deleteCustomerDto = deleteCustomerMapper.mapToDeleteCustomerDto(deleteCustomer);

        //Then
        assertEquals(1L, deleteCustomerDto.getDeleteCustomerId());
        assertEquals(3L, deleteCustomerDto.getPreviousCustomerId());
        assertEquals("test@mail.com", deleteCustomerDto.getCustomerMailAddress());
        assertEquals(LocalDate.of(2001, 1, 3), deleteCustomerDto.getCreateAccountDate());
        assertEquals(LocalDate.of(2009, 9 ,2), deleteCustomerDto.getDeleteAccountDate());
        assertTrue(deleteCustomerDto.isBlocked());
    }

    @Test
    public void mapToDeleteCustomerDtoList() {
        //Given
        DeleteCustomer deleteCustomer1 = new DeleteCustomer(1L, 4L, "test1@mail.com", LocalDate.of(2001, 1, 1), true, LocalDate.of(2004, 7,10));
        DeleteCustomer deleteCustomer2 = new DeleteCustomer(2L, 5L, "test2@mail.com", LocalDate.of(2002, 2, 2), false, LocalDate.of(2005, 8 ,11));
        DeleteCustomer deleteCustomer3 = new DeleteCustomer(3L, 6L, "test3@mail.com", LocalDate.of(2003, 3, 3), false, LocalDate.of(2006, 9 ,12));
        List<DeleteCustomer> deleteCustomers = Arrays.asList(deleteCustomer1, deleteCustomer2, deleteCustomer3);

        //When
        List<DeleteCustomerDto> deleteCustomerDtoList = deleteCustomerMapper.mapToDeleteCustomerDtoList(deleteCustomers);

        //Then
        assertEquals(3, deleteCustomerDtoList.size());
        assertEquals(1L, deleteCustomerDtoList.get(0).getDeleteCustomerId());
        assertEquals(4L, deleteCustomerDtoList.get(0).getPreviousCustomerId());
        assertEquals("test1@mail.com", deleteCustomerDtoList.get(0).getCustomerMailAddress());
        assertEquals(LocalDate.of(2001, 1, 1), deleteCustomerDtoList.get(0).getCreateAccountDate());
        assertEquals(LocalDate.of(2004, 7,10), deleteCustomerDtoList.get(0).getDeleteAccountDate());
        assertTrue(deleteCustomerDtoList.get(0).isBlocked());
        assertEquals(2L, deleteCustomerDtoList.get(1).getDeleteCustomerId());
        assertEquals(5L, deleteCustomerDtoList.get(1).getPreviousCustomerId());
        assertEquals("test2@mail.com", deleteCustomerDtoList.get(1).getCustomerMailAddress());
        assertEquals(LocalDate.of(2002, 2, 2), deleteCustomerDtoList.get(1).getCreateAccountDate());
        assertEquals(LocalDate.of(2005, 8 ,11), deleteCustomerDtoList.get(1).getDeleteAccountDate());
        assertFalse(deleteCustomerDtoList.get(1).isBlocked());
        assertEquals(3L, deleteCustomerDtoList.get(2).getDeleteCustomerId());
        assertEquals(6L, deleteCustomerDtoList.get(2).getPreviousCustomerId());
        assertEquals("test3@mail.com", deleteCustomerDtoList.get(2).getCustomerMailAddress());
        assertEquals(LocalDate.of(2003, 3, 3), deleteCustomerDtoList.get(2).getCreateAccountDate());
        assertEquals(LocalDate.of(2006, 9 ,12), deleteCustomerDtoList.get(2).getDeleteAccountDate());
        assertFalse(deleteCustomerDtoList.get(2).isBlocked());
    }
}
