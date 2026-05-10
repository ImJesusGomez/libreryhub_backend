package com.jesus.backend.service.impl;

import com.jesus.backend.dto.request.BookCreateRequestDTO;
import com.jesus.backend.dto.request.BookUpdateRequestDTO;
import com.jesus.backend.dto.response.BookResponseDTO;
import com.jesus.backend.exception.ResourceAlreadyExistsException;
import com.jesus.backend.exception.ResourceNotFoundException;
import com.jesus.backend.mapper.BookMapper;
import com.jesus.backend.model.Book;
import com.jesus.backend.repository.BookRepository;
import com.jesus.backend.service.BookService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    @Override
    public BookResponseDTO register(BookCreateRequestDTO dto) {
        // Verificamos que sea un ISBN único
        if (bookRepository.existsByIsbn(dto.isbn())) throw new ResourceAlreadyExistsException("Book with ISBN " + dto.isbn() + " already registered.");

        // Creamos el libro
        Book bookToCreate = bookMapper.toEntity(dto);

        // Guardamos en la BD el libro
        Book bookCreated = bookRepository.save(bookToCreate);

        // Retornamos la respuesta
        return bookMapper.toBookResponseDTO(bookCreated);
    }

    @Transactional(readOnly = true)
    @Override
    public BookResponseDTO findById(UUID id) {
        return bookMapper.toBookResponseDTO(bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book Not Found.")));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookResponseDTO> getBooks(String title, String author, int page, int size, String sortBy, String sortDir) {
        Specification<Book> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            if (author != null && !author.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return bookRepository.findAll(spec, pageable).map(bookMapper::toBookResponseDTO);
    }

    @Transactional
    @Override
    public BookResponseDTO update(UUID id, BookUpdateRequestDTO dto) {
        // Buscamos el libro a actualizar
        Book bookToUpdate = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book Not Found with id: " + id));

        // Verificamos que el ISBN sea único
        if (dto.isbn() != null && !Objects.equals(dto.isbn(), bookToUpdate.getIsbn()) && bookRepository.existsByIsbn(dto.isbn()) ) {
            throw new ResourceAlreadyExistsException("Book with ISBN: " + dto.isbn() + " already exists");
        }

        // Actualizamos las propiedades del libro
        if (dto.isbn() != null) bookToUpdate.setIsbn(dto.isbn());
        if (dto.title() != null) bookToUpdate.setTitle(dto.title());
        if (dto.genre() != null) bookToUpdate.setGenre(dto.genre());
        if (dto.author() != null) bookToUpdate.setAuthor(dto.author());
        if (dto.synopsis() != null) bookToUpdate.setSynopsis(dto.synopsis());
        if (dto.editorial() != null) bookToUpdate.setEditorial(dto.editorial());
        if (dto.availableCopies() != null) bookToUpdate.setAvailableCopies(dto.availableCopies());

        // Guardamos los cambios
        Book bookUpdated = bookRepository.save(bookToUpdate);

        // Retornamos la respuesta
        return bookMapper.toBookResponseDTO(bookUpdated);
    }

}
