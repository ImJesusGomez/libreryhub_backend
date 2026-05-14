package com.jesus.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record LoanItemUpdateRequestDTO(

        @NotNull(message = "Real return date is required.")
        @PastOrPresent(message = "Real return date cannot be in the future.")
        LocalDate realReturnDate,

        @NotNull(message = "Damaged status is required.")
        Boolean damaged

) {
}