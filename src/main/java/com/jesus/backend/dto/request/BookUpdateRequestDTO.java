package com.jesus.backend.dto.request;

import com.jesus.backend.model.enums.BookGenre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookUpdateRequestDTO(
        @Size(min = 13, max = 13, message = "ISBN must has 13 characters.")
        String isbn,

        @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters.")
        String title,

        @Size(min = 1, max = 100, message = "Author must be between 1 and 100 characters.")
        String author,

        @Size(min = 1, max = 50, message = "Title must be between 1 and 100 characters.")
        String editorial,

        @Size(min = 10, max = 100, message = "Title must be between 10 and 100 characters.")
        String synopsis,

        BookGenre genre,

        @Min(value = 0, message = "Min valor is 0.")
        Integer availableCopies
) {
}
