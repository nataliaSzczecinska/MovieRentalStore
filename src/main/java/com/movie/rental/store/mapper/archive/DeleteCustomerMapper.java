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
        return new DeleteCustomer(deleteCustomerDto.getDeleteCustomerId(),
                deleteCustomerDto.getPreviousCustomerId(),
                deleteCustomerDto.getCustomerMailAddress(),
                deleteCustomerDto.getCreateAccountDate(),
                deleteCustomerDto.isBlocked(),
                deleteCustomerDto.getDeleteAccountDate());
    }

    public DeleteCustomerDto mapToDeleteCustomerDto(final DeleteCustomer deleteCustomer) {
        LOGGER.info("Map DeleteCustomer to DeleteCustomerDto");
        return new DeleteCustomerDto (deleteCustomer.getDeleteCustomerId(),
                deleteCustomer.getPreviousCustomerId(),
                deleteCustomer.getCustomerMailAddress(),
                deleteCustomer.getCreateAccountDate(),
                deleteCustomer.isBlocked(),
                deleteCustomer.getDeleteAccountDate());
    }

    public List<DeleteCustomerDto> mapToDeleteCustomerDtoList(final List<DeleteCustomer> deleteCustomers) {
        LOGGER.info("Map DeleteCustomerList to DeleteCustomerDtoList");
        return deleteCustomers.stream()
                .map(deleteCustomer -> mapToDeleteCustomerDto(deleteCustomer))
                .collect(Collectors.toList());
    }
}
