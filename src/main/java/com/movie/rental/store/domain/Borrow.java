package com.movie.rental.store.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Entity
@Table(name ="BORROW")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Borrow {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "BORROW_ID")
    private Long borrowId;

    @NotNull
    @ManyToOne
    @JoinColumn (name = "COPY_ID")
    private Copy copy;

    @NotNull
    @ManyToOne
    @JoinColumn (name = "CUSTOMER_ID")
    private Customer customer;

    @NotNull
    @Column (name = "BORROW_DATE")
    private LocalDate borrowDate;

    @NotNull
    @Column (name = "RETURN_DATE")
    private LocalDate returnDate;

    public Borrow(@NotNull Copy copy, @NotNull Customer customer, @NotNull LocalDate borrowDate, @NotNull LocalDate returnDate) {
        this.copy = copy;
        this.customer = customer;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
}
