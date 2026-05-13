package com.jesus.backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "loan_items")
public class LoanItem extends Auditable {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "estimated_return_date")
    private LocalDate estimatedReturnDate;

    @Column(name = "real_return_date")
    private LocalDate realReturnDate;

    @Column(name = "damaged")
    private Boolean damaged = false;

    @Column(name = "quantity")
    private Integer quantity = 1;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    // Constructor
    public LoanItem() {
    }

    public LoanItem(UUID id, LocalDate estimatedReturnDate, LocalDate realReturnDate, Boolean damaged, Integer quantity, Loan loan, Book book) {
        this.id = id;
        this.estimatedReturnDate = estimatedReturnDate;
        this.realReturnDate = realReturnDate;
        this.damaged = damaged;
        this.quantity = quantity;
        this.loan = loan;
        this.book = book;
    }

    // Getters & Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getEstimatedReturnDate() {
        return estimatedReturnDate;
    }

    public void setEstimatedReturnDate(LocalDate estimatedReturnDate) {
        this.estimatedReturnDate = estimatedReturnDate;
    }

    public LocalDate getRealReturnDate() {
        return realReturnDate;
    }

    public void setRealReturnDate(LocalDate realReturnDate) {
        this.realReturnDate = realReturnDate;
    }

    public Boolean getDamaged() {
        return damaged;
    }

    public void setDamaged(Boolean damaged) {
        this.damaged = damaged;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
