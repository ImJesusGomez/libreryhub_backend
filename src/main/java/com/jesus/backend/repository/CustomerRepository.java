package com.jesus.backend.repository;

import com.jesus.backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
}
