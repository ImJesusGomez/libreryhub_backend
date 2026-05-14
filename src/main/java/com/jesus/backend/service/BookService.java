package com.jesus.backend.service;

import com.jesus.backend.dto.request.BookCreateRequestDTO;
import com.jesus.backend.dto.request.BookUpdateRequestDTO;
import com.jesus.backend.dto.response.BookResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface BookService {
    BookResponseDTO register(BookCreateRequestDTO dto);
    BookResponseDTO findById(UUID id);
    Page<BookResponseDTO> getBooks(String title, String author, int page, int size, String sortBy, String sortDir);
    BookResponseDTO update(UUID id, BookUpdateRequestDTO dto);
    void uploadBookImage(UUID id, MultipartFile image);
}
