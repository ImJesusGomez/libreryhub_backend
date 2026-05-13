package com.jesus.backend.mapper;

import com.jesus.backend.dto.request.BookCreateRequestDTO;
import com.jesus.backend.dto.response.BookResponseDTO;
import com.jesus.backend.dto.response.BookSummaryResponseDTO;
import com.jesus.backend.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookMapper {

    // Entity -> DTO
    BookResponseDTO toBookResponseDTO(Book book);
    BookSummaryResponseDTO toBookSummaryResponseDTO(Book book);

    // DTO -> Entity
    @Mapping(target = "isbn", source = "isbn", qualifiedByName = "trim")
    @Mapping(target = "title", source = "title", qualifiedByName = "trim")
    @Mapping(target = "author", source = "author", qualifiedByName = "trim")
    @Mapping(target = "editorial", source = "editorial", qualifiedByName = "trim")
    @Mapping(target = "synopsis", source = "synopsis", qualifiedByName = "trim")
    Book toEntity(BookCreateRequestDTO dto);

    // Funciones de limpieza
    @Named("trim")
    default String trim(String attribute) {
        return attribute == null ? null : attribute.trim();
    }
}
