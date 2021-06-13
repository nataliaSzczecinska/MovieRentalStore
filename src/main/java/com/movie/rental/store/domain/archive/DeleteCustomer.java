package com.movie.rental.store.domain.archive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "DELETED_CUSTOMERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeleteCustomer {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "DELETE_CUSTOMER_ID", unique = true)
    private Long deleteCustomerId;

    @NotNull
    @Column(name = "PREVIOUS_CUSTOMER_ID")
    private Long previousCustomerId;

    @NotNull
    @Column(name = "CUSTOMER_MAIL_ADRESS")
    private String customerMailAddress;

    @NotNull
    @Column(name = "CREATE_ACCOUNT_DATE")
    private LocalDate createAccountDate;

    @NotNull
    @Column(name = "IS_BLOCKED")
    private boolean isBlocked;

    @NotNull
    @Column(name = "DELETE_ACCOUNT_DATE")
    private LocalDate deleteAccountDate;

    public DeleteCustomer(@NotNull Long customerId, @NotNull String customerMailAddress, @NotNull LocalDate createAccountDate, @NotNull boolean isBlocked, @NotNull LocalDate deleteAccountDate) {
        this.previousCustomerId = customerId;
        this.customerMailAddress = customerMailAddress;
        this.createAccountDate = createAccountDate;
        this.isBlocked = isBlocked;
        this.deleteAccountDate = deleteAccountDate;
    }
}
