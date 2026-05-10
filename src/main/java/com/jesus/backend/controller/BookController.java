package com.jesus.backend.controller;

import com.jesus.backend.dto.request.BookCreateRequestDTO;
import com.jesus.backend.dto.request.BookUpdateRequestDTO;
import com.jesus.backend.dto.response.BookResponseDTO;
import com.jesus.backend.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PostMapping()
    public ResponseEntity<BookResponseDTO> registerBook(@Valid @RequestBody BookCreateRequestDTO dto) {
        return new ResponseEntity<>(bookService.register(dto), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> findBookById(@PathVariable UUID id) {
        return new ResponseEntity<>(bookService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping()
    public ResponseEntity<Page<BookResponseDTO>> getAllBooks (
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return new ResponseEntity<>(bookService.getBooks(title, author, page, size, sortBy,sortDir), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PatchMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBookById(@PathVariable UUID id, @Valid @RequestBody BookUpdateRequestDTO dto) {
        return new ResponseEntity<>(bookService.update(id, dto), HttpStatus.OK);
    }
}
