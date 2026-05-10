package com.jesus.backend.dto.request;

import com.jesus.backend.model.enums.BookGenre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookCreateRequestDTO (
        @NotBlank(message = "ISBN is required.")
        @Size(min = 13, max = 13, message = "ISBN must has 13 characters.")
        String isbn,

        @NotBlank(message = "Title is required.")
        @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters.")
        String title,

        @NotBlank(message = "Author is required.")
        @Size(min = 1, max = 100, message = "Author must be between 1 and 100 characters.")
        String author,

        @NotBlank(message = "Editorial is required.")
        @Size(min = 1, max = 50, message = "Editorial must be between 1 and 50 characters.")
        String editorial,

        @NotBlank(message = "Synopsis is required.")
        @Size(min = 10, max = 255, message = "Synopsis must be between 10 and 255 characters.")
        String synopsis,

        @NotNull(message = "Genre is required.")
        BookGenre genre,

        @NotNull(message = "Available Copies is required.")
        @Min(value = 0, message = "Min valor is 0.")
        Integer availableCopies
) {
}
