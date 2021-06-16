package com.movie.rental.store.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import java.util.*;

@Entity
@Table(name = "CUSTOMERS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "DELETE_CUSTOMER_ID", unique = true)
    private Long customerId;

    @NotNull
    @Column(name = "CUSTOMER_MAIL_ADRESS", unique = true)
    private String customerMailAddress;

    @NotNull
    @Column(name = "CREATE_ACCOUNT_DATE")
    private LocalDate createAccountDate;

    @NotNull
    @Column(name = "IS_BLOCKED")
    private boolean isBlocked;

    @OneToMany(
            targetEntity = Borrow.class,
            mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Borrow> borrows;

    public Customer(@NotNull String customerMailAddress, @NotNull LocalDate createAccountDate, @NotNull boolean isBlocked) {
        this.customerMailAddress = customerMailAddress;
        this.createAccountDate = createAccountDate;
        this.isBlocked = isBlocked;
    }

    public Customer(@NotNull Long customerId, @NotNull String customerMailAddress, @NotNull LocalDate createAccountDate, @NotNull boolean isBlocked) {
        this.customerId = customerId;
        this.customerMailAddress = customerMailAddress;
        this.createAccountDate = createAccountDate;
        this.isBlocked = isBlocked;
    }
}
