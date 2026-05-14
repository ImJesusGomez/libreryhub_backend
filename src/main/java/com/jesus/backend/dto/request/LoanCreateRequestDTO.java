package com.jesus.backend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LoanCreateRequestDTO(

        @NotNull(message = "Customer ID is required.")
        UUID customerId,

        @NotEmpty(message = "Loan items cannot be empty.")
        @Valid
        LoanItemCreateRequestDTO[] items

) {
}
