package com.jesus.backend.controller;

import com.jesus.backend.dto.request.CustomerCreateRequestDTO;
import com.jesus.backend.dto.request.CustomerUpdateRequestDTO;
import com.jesus.backend.dto.response.CustomerResponseDTO;
import com.jesus.backend.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PostMapping()
    public ResponseEntity<CustomerResponseDTO> registerCustomer(@Valid @RequestBody CustomerCreateRequestDTO dto) {
        return new ResponseEntity<>(customerService.register(dto), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findCustomerById(@PathVariable UUID id) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping()
    public ResponseEntity<Page<CustomerResponseDTO>> getCustomers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return new ResponseEntity<>(customerService.getCustomers(firstName, lastName, page, size, sortBy, sortDir), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomerById(@PathVariable UUID id, @Valid @RequestBody CustomerUpdateRequestDTO dto) {
        return new ResponseEntity<>(customerService.update(id, dto), HttpStatus.OK);
    }

}
