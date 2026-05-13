package com.jesus.backend.repository;

import com.jesus.backend.model.LoanItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanItemRepository extends JpaRepository<LoanItem, UUID> {
}
