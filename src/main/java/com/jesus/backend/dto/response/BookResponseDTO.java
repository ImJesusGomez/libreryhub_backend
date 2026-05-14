package com.jesus.backend.dto.response;

import com.jesus.backend.model.enums.BookGenre;

import java.util.UUID;

public record BookResponseDTO (
        UUID id,
        String isbn,
        String title,
        String author,
        String editorial,
        String synopsis,
        BookGenre genre,
        Integer availableCopies,
        String imageUrl
) {
}
