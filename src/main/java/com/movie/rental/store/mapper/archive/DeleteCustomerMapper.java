package com.movie.rental.store.mapper.archive;

import com.movie.rental.store.domain.archive.DeleteCustomer;
import com.movie.rental.store.domain.dto.DeleteCustomerDto;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DeleteCustomerMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteCustomerMapper.class);

    public DeleteCustomer mapToDeleteCustomer(final DeleteCustomerDto deleteCustomerDto) {
        LOGGER.info("Map DeleteCustomerDto to DeleteCustomer");
        return DeleteCustomer.builder()
                .deleteCustomerId(deleteCustomerDto.getDeleteCustomerId())
                .previousCustomerId(deleteCustomerDto.getPreviousCustomerId())
                .customerMailAddress(deleteCustomerDto.getCustomerMailAddress())
                .createAccountDate(deleteCustomerDto.getCreateAccountDate())
                .isBlocked(deleteCustomerDto.isBlocked())
                .deleteAccountDate(deleteCustomerDto.getDeleteAccountDate())
                .build();
    }

    public DeleteCustomerDto mapToDeleteCustomerDto(final DeleteCustomer deleteCustomer) {
        LOGGER.info("Map DeleteCustomer to DeleteCustomerDto");
        return DeleteCustomerDto.builder()
                .deleteCustomerId(deleteCustomer.getDeleteCustomerId())
                .previousCustomerId(deleteCustomer.getPreviousCustomerId())
                .customerMailAddress(deleteCustomer.getCustomerMailAddress())
                .createAccountDate(deleteCustomer.getCreateAccountDate())
                .isBlocked(deleteCustomer.isBlocked())
                .deleteAccountDate(deleteCustomer.getDeleteAccountDate())
                .build();
    }

    public List<DeleteCustomerDto> mapToDeleteCustomerDtoList(final List<DeleteCustomer> deleteCustomers) {
        LOGGER.info("Map DeleteCustomerList to DeleteCustomerDtoList");
        return deleteCustomers.stream()
                .map(deleteCustomer -> mapToDeleteCustomerDto(deleteCustomer))
                .collect(Collectors.toList());
    }
}
