package com.jesus.backend.model;

import com.jesus.backend.model.enums.LoanStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "loans")
public class Loan extends Auditable {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "loan_date")
    private LocalDateTime loanDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(
            mappedBy = "loan",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LoanItem> items = new ArrayList<>();

    // Constructor
    public Loan() {
    }

    public Loan(UUID id, LocalDateTime loanDate, LoanStatus status, Customer customer, List<LoanItem> items) {
        this.id = id;
        this.loanDate = loanDate;
        this.status = status;
        this.customer = customer;
        this.items = items;
    }

    // Getters & Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<LoanItem> getItems() {
        return items;
    }

    public void setItems(List<LoanItem> items) {
        this.items = items;
    }
}
