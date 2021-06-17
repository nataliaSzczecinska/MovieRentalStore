package com.movie.rental.store.facade;

import com.movie.rental.store.domain.Copy;
import com.movie.rental.store.domain.archive.BorrowArchive;
import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.archive.DeleteCustomer;
import com.movie.rental.store.domain.dto.BorrowArchiveDto;
import com.movie.rental.store.domain.dto.DeleteCopyDto;
import com.movie.rental.store.domain.dto.DeleteCustomerDto;
import com.movie.rental.store.exception.CopyNotFoundException;
import com.movie.rental.store.mapper.archive.BorrowArchiveMapper;
import com.movie.rental.store.mapper.archive.DeleteCopyMapper;
import com.movie.rental.store.mapper.archive.DeleteCustomerMapper;
import com.movie.rental.store.service.CopyDbService;
import com.movie.rental.store.service.archive.BorrowArchiveDbService;
import com.movie.rental.store.service.archive.DeleteCopyDbService;
import com.movie.rental.store.service.archive.DeleteCustomerDbService;
import com.movie.rental.store.validator.ArchiveValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArchiveDataFacade {
    private final DeleteCopyMapper deleteCopyMapper;
    private final DeleteCustomerMapper deleteCustomerMapper;
    private final BorrowArchiveMapper borrowArchiveMapper;
    private final DeleteCopyDbService deleteCopyDbService;
    private final BorrowArchiveDbService borrowArchiveDbService;
    private final DeleteCustomerDbService deleteCustomerDbService;
    private final CopyDbService copyDbService;
    private final ArchiveValidator archiveValidator;

    public List<BorrowArchiveDto> getBorrowsArchiveByCustomerId(Long customerId) {
        return borrowArchiveMapper.mapToBorrowArchiveDtoList(borrowArchiveDbService.searchBorrowArchiveByCustomerId(customerId));
    }

    public List<BorrowArchiveDto> getBorrowsArchiveByMovieId(Long movieId) throws CopyNotFoundException {
        return borrowArchiveMapper.mapToBorrowArchiveDtoList(archiveValidator.getBorrowsArchiveByMovieId(movieId));
    }

    public List<DeleteCopyDto> getAllDeleteCopies() {
        return deleteCopyMapper.mapToDeleteCopyDtoList(deleteCopyDbService.getAllDeletedCopies());
    }

    public List<DeleteCustomerDto> getAllDeleteCustomers() {
        return deleteCustomerMapper.mapToDeleteCustomerDtoList(deleteCustomerDbService.getAllDeletedCustomers());
    }
}
