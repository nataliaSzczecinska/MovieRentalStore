package com.movie.rental.store.domain;

import com.movie.rental.store.domain.archive.BorrowArchive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

import java.util.*;

@Entity
@Table(name = "CUSTOMERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    private Long customerId;
    private String customerMailAddress;
    private LocalDate createAccountDate;
    private boolean isBlocked;

    @OneToMany(
            targetEntity = Borrow .class,
            mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Borrow> borrows;

    //@OneToMany
    //private List<BorrowArchive> borrowArchives;


    public Customer(Long customerId, String customerMailAddress, LocalDate createAccountDate, boolean isBlocked) {
        this.customerId = customerId;
        this.customerMailAddress = customerMailAddress;
        this.createAccountDate = createAccountDate;
        this.isBlocked = isBlocked;
    }
}
