package com.jesus.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerCreateRequestDTO (
        @NotBlank(message = "First Name is required.")
        @Size(min = 1, max = 100, message = "First Name must be between 1 and 100 characters.")
        String firstName,

        @NotBlank(message = "Last Name is required.")
        @Size(min = 1, max = 100, message = "Last Name must be between 1 and 100 characters.")
        String lastName,

        @NotBlank(message = "Email Address is required.")
        @Email(message = "Must be a valid email address.")
        String email,

        @NotBlank(message = "Phone Nomber is required.")
        @Size(min = 10, max = 15, message = "Must be a valid phone number")
        String phoneNumber
) {
}
