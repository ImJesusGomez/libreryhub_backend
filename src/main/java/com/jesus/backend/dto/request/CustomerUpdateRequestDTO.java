package com.jesus.backend.dto.request;

import com.jesus.backend.model.enums.CustomerStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record CustomerUpdateRequestDTO(
        @Size(min = 1, max = 100, message = "First Name must be between 1 and 100 characters.")
        String firstName,

        @Size(min = 1, max = 100, message = "Last Name must be between 1 and 100 characters.")
        String lastName,

        @Email(message = "Must be a valid email address.")
        String email,

        @Size(min = 10, max = 15, message = "Must be a valid phone number")
        String phoneNumber,

        CustomerStatus status
) {
}
