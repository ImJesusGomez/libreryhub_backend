package com.jesus.backend.mapper;

import com.jesus.backend.dto.request.BookCreateRequestDTO;
import com.jesus.backend.dto.response.BookResponseDTO;
import com.jesus.backend.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    // DTO -> Entity
    Book toEntity(BookCreateRequestDTO dto);

    // Entity -> DTO
    BookResponseDTO toBookResponseDTO(Book book);
}
