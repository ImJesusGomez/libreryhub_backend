package com.jesus.backend.security.dto;

import java.util.Set;

public record RegisterRequestDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber,
        Set<String> roles
) {
}
