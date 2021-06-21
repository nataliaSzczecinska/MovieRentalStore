package com.movie.rental.store.controller;

import com.google.gson.Gson;
import com.movie.rental.store.domain.Borrow;
import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.Customer;
import com.movie.rental.store.domain.dto.CopyDto;
import com.movie.rental.store.domain.dto.CustomerDto;
import com.movie.rental.store.facade.CustomerFacade;
import com.movie.rental.store.mapper.CustomerMapper;
import com.movie.rental.store.service.CustomerDbService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(CustomerController.class)
public class CustomerControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerMapper customerMapper;

    @MockBean
    private CustomerFacade customerFacade;

    @MockBean
    private CustomerDbService customerDbService;

    private Customer customer1 = Customer.builder()
            .customerId(1L)
            .customerMailAddress("mail1@address.com")
            .createAccountDate(LocalDate.of(2000, 1, 1))
            .isBlocked(false)
            .build();
    private Customer customer2 = Customer.builder()
            .customerId(2L)
            .customerMailAddress("mail2@address.com")
            .createAccountDate(LocalDate.of(2001, 3, 12))
            .isBlocked(false)
            .build();
    private Customer customer3 = Customer.builder()
            .customerId(3L)
            .customerMailAddress("mail3@address.com")
            .createAccountDate(LocalDate.of(2003, 10, 17))
            .isBlocked(false)
            .build();
    private CustomerDto customer1Dto = CustomerDto.builder()
            .customerId(1L)
            .customerMailAddress("mail1@address.com")
            .createAccountDate(LocalDate.of(2000, 1, 1))
            .isBlocked(false)
            .build();
    private CustomerDto customer2Dto = CustomerDto.builder()
            .customerId(2L)
            .customerMailAddress("mail2@address.com")
            .createAccountDate(LocalDate.of(2001, 3, 12))
            .isBlocked(false)
            .build();
    private CustomerDto customer3Dto = CustomerDto.builder()
            .customerId(3L)
            .customerMailAddress("mail3@address.com")
            .createAccountDate(LocalDate.of(2003, 10, 17))
            .isBlocked(false)
            .build();

    @Test
    public void getAllCustomersTest() throws Exception {
        //Given
        List<Customer> customers = Arrays.asList(customer1, customer2, customer3);
        List<CustomerDto> customersDto = Arrays.asList(customer1Dto, customer2Dto, customer3Dto);

        when(customerMapper.mapToCustomerDtoList(customers)).thenReturn(customersDto);
        when(customerFacade.getAllCustomers()).thenReturn(customersDto);
        when(customerDbService.getAllCustomers()).thenReturn(customers);

        //When & Then
        mockMvc.perform(get("/movies/customers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].customerId", is(1)))
                .andExpect(jsonPath("$[0].customerMailAddress", is("mail1@address.com")))
                .andExpect(jsonPath("$[0].createAccountDate", is("2000-01-01")))
                .andExpect(jsonPath("$[0].blocked", is(false)))
                .andExpect(jsonPath("$[1].customerId", is(2)))
                .andExpect(jsonPath("$[1].customerMailAddress", is("mail2@address.com")))
                .andExpect(jsonPath("$[1].createAccountDate", is("2001-03-12")))
                .andExpect(jsonPath("$[1].blocked", is(false)))
                .andExpect(jsonPath("$[2].customerId", is(3)))
                .andExpect(jsonPath("$[2].customerMailAddress", is("mail3@address.com")))
                .andExpect(jsonPath("$[2].createAccountDate", is("2003-10-17")))
                .andExpect(jsonPath("$[2].blocked", is(false)));
    }

    @Test
    public void getCustomerByIdTest() throws Exception {
        //Given
        when(customerMapper.mapToCustomerDto(customer1)).thenReturn(customer1Dto);
        when(customerFacade.getCustomerById(1L)).thenReturn(customer1Dto);
        when(customerDbService.getCustomerById(1L)).thenReturn(Optional.of(customer1));

        //When & Then
        mockMvc.perform(get("/movies/customers/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.customerMailAddress", is("mail1@address.com")))
                .andExpect(jsonPath("$.createAccountDate", is("2000-01-01")))
                .andExpect(jsonPath("$.blocked", is(false)));
    }

    @Test
    public void createCustomerTest() throws Exception {
        //Given
        List<Borrow> borrows = new ArrayList<>();
        when(customerMapper.mapToCustomerDto(ArgumentMatchers.any(Customer.class))).thenReturn(customer1Dto);
        when(customerMapper.mapToCustomer(ArgumentMatchers.any(CustomerDto.class), ArgumentMatchers.eq(borrows))).thenReturn(customer1);
        when(customerDbService.saveCustomer(ArgumentMatchers.any(Customer.class))).thenReturn(customer1);

        Gson gson = new Gson();
        String jsContent = gson.toJson(customer1Dto);

        //When & Then
        mockMvc.perform(post("/movies/customers/new@mail.com").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept("application/json")
                .content(jsContent))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCustomerTest() throws Exception {
        //Given
        List<Borrow> borrows = new ArrayList<>();
        when(customerMapper.mapToCustomerDto(ArgumentMatchers.any(Customer.class))).thenReturn(customer1Dto);
        when(customerMapper.mapToCustomer(ArgumentMatchers.any(CustomerDto.class), ArgumentMatchers.eq(borrows))).thenReturn(customer1);
        when(customerDbService.saveCustomer(ArgumentMatchers.any(Customer.class))).thenReturn(customer1);
        when(customerFacade.updateCustomer(1L, "new@mail.com")).thenReturn(customer1Dto);

        Gson gson = new Gson();
        String jsContent = gson.toJson(customer1Dto);

        //When & Then
        mockMvc.perform(put("/movies/customers/1/new@mail.com").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept("application/json")
                .content(jsContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.customerMailAddress", is("mail1@address.com")))
                .andExpect(jsonPath("$.createAccountDate", is("2000-01-01")))
                .andExpect(jsonPath("$.blocked", is(false)));
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        //When & Then
        mockMvc.perform(delete("/movies/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }
}
