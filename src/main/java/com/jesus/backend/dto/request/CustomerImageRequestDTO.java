package com.jesus.backend.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record CustomerImageRequestDTO (
        MultipartFile image
) {
}
