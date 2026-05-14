package com.jesus.backend.repository;

import com.jesus.backend.model.Loan;
import com.jesus.backend.model.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID>, JpaSpecificationExecutor<Loan> {
    List<Loan> findByStatus(LoanStatus status);
}
