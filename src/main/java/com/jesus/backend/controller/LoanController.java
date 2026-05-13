package com.jesus.backend.controller;

import com.jesus.backend.dto.request.LoanCreateRequestDTO;
import com.jesus.backend.dto.request.LoanItemUpdateRequestDTO;
import com.jesus.backend.dto.request.LoanUpdateRequestDTO;
import com.jesus.backend.dto.response.LoanItemResponseDTO;
import com.jesus.backend.dto.response.LoanResponseDTO;
import com.jesus.backend.model.enums.LoanStatus;
import com.jesus.backend.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PostMapping
    public ResponseEntity<LoanResponseDTO> createLoan(@Valid @RequestBody LoanCreateRequestDTO dto) {
        return new ResponseEntity<>(loanService.create(dto), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> findLoanById(@PathVariable UUID id) {
        return new ResponseEntity<>(loanService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/{id}/loan-item")
    public ResponseEntity<LoanItemResponseDTO> findLoanItemById(@PathVariable UUID id) {
        return new ResponseEntity<>(loanService.findLoanItemById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping()
    public ResponseEntity<Page<LoanResponseDTO>> getLoans (
            @RequestParam(required = false) LoanStatus status,
            @RequestParam(required = false) String customerFirstName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "loanDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return new ResponseEntity<>(loanService.getLoans(status, customerFirstName, page, size, sortBy, sortDir), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PutMapping("loan-items/{id}/return")
    public ResponseEntity<LoanItemResponseDTO> returnLoanItemById(@PathVariable UUID id, LoanItemUpdateRequestDTO dto) {
        return new ResponseEntity<>(loanService.returnLoanItem(id, dto), HttpStatus.OK);
    }

}
