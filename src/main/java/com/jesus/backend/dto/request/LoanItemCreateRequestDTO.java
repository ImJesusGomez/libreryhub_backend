package com.jesus.backend.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record LoanItemCreateRequestDTO(

        @NotNull(message = "Estimated return date is required.")
        @Future(message = "Estimated return date must be in the future.")
        LocalDate estimatedReturnDate,

        @NotNull(message = "Quantity is required.")
        @Min(value = 1, message = "Quantity must be at least 1.")
        Integer quantity,

        @NotNull(message = "Book ID is required.")
        UUID bookId

) {
}