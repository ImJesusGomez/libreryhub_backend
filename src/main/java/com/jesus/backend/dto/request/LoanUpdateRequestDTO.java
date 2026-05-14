package com.jesus.backend.dto.request;

import com.jesus.backend.model.enums.LoanStatus;
import jakarta.validation.constraints.NotNull;

public record LoanUpdateRequestDTO(

        @NotNull(message = "Loan status is required.")
        LoanStatus status

) {
}