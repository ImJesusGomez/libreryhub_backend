package com.jesus.backend.service.impl;

import com.jesus.backend.dto.request.LoanCreateRequestDTO;
import com.jesus.backend.dto.request.LoanItemCreateRequestDTO;
import com.jesus.backend.dto.request.LoanItemUpdateRequestDTO;
import com.jesus.backend.dto.request.LoanUpdateRequestDTO;
import com.jesus.backend.dto.response.LoanItemResponseDTO;
import com.jesus.backend.dto.response.LoanResponseDTO;
import com.jesus.backend.exception.ResourceNotAvailableException;
import com.jesus.backend.exception.ResourceNotFoundException;
import com.jesus.backend.mapper.LoanItemMapper;
import com.jesus.backend.mapper.LoanMapper;
import com.jesus.backend.model.Book;
import com.jesus.backend.model.Customer;
import com.jesus.backend.model.Loan;
import com.jesus.backend.model.LoanItem;
import com.jesus.backend.model.enums.LoanStatus;
import com.jesus.backend.repository.BookRepository;
import com.jesus.backend.repository.CustomerRepository;
import com.jesus.backend.repository.LoanItemRepository;
import com.jesus.backend.repository.LoanRepository;
import com.jesus.backend.service.LoanService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LoanServiceImpl implements LoanService {

    private final CustomerRepository customerRepository;
    private final LoanItemRepository loanItemRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;
    private final LoanItemMapper loanItemMapper;

    public LoanServiceImpl(CustomerRepository customerRepository, LoanItemRepository loanItemRepository, LoanRepository loanRepository, LoanMapper loanMapper, BookRepository bookRepository, LoanItemMapper loanItemMapper) {
        this.customerRepository = customerRepository;
        this.loanItemRepository = loanItemRepository;
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.loanMapper = loanMapper;
        this.loanItemMapper = loanItemMapper;
    }

    @Transactional
    @Override
    public LoanResponseDTO create(LoanCreateRequestDTO dto) {
        // Buscamos el cliente
        Customer customer = customerRepository.findById(dto.customerId()).orElseThrow(() -> new ResourceNotFoundException("Customer Not Found"));

        // Creamos el préstamo
        Loan loanToCreate = new Loan();
        loanToCreate.setLoanDate(LocalDateTime.now());
        loanToCreate.setStatus(LoanStatus.ACTIVE);
        loanToCreate.setCustomer(customer);


        // Procesar sus items
        for (LoanItemCreateRequestDTO item: dto.items()) {
            // Buscamos el libro
            Book book = bookRepository.findById(item.bookId()).orElseThrow(() -> new ResourceNotFoundException("Book Not Found"));

            // Verificamos que la cantidad sea válida
            if (item.quantity() > book.getAvailableCopies()) {
                throw new ResourceNotAvailableException("Insufficient available copies");
            }

            // Creamos el item
            LoanItem loanItem = new LoanItem();
            loanItem.setEstimatedReturnDate(item.estimatedReturnDate());
            loanItem.setQuantity(item.quantity());

            // Relacionar entidades
            loanItem.setLoan(loanToCreate);
            loanItem.setBook(book);

            // Almacenamos el item en el préstamo
            loanToCreate.getItems().add(loanItem);

            // Guardamos los cambios en el libro
            book.setAvailableCopies(book.getAvailableCopies() - item.quantity());
        }

        // Guardamos el préstamo
        Loan loanCreated = loanRepository.save(loanToCreate);

        // Retornamos la respuesta
        return loanMapper.toLoanResponseDTO(loanCreated);
    }

    @Transactional(readOnly = true)
    @Override
    public LoanResponseDTO findById(UUID id) {
        return loanMapper.toLoanResponseDTO(loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan Not Found.")));
    }

    @Transactional(readOnly = true)
    @Override
    public LoanItemResponseDTO findLoanItemById(UUID id) {
        return loanItemMapper.toLoanItemResponseDTO(loanItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan Item Not Found.")));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<LoanResponseDTO> getLoans(LoanStatus status, String customerFirstName, int page, int size, String sortBy, String sortDir) {
        Specification<Loan> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (customerFirstName != null && !customerFirstName.isEmpty()) {
                Join<Loan, Customer> loanCustomerJoin = root.join("customer");
                predicates.add(cb.like(cb.lower(loanCustomerJoin.get("firstName")), "%" + customerFirstName.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return loanRepository.findAll(spec, pageable).map(loanMapper::toLoanResponseDTO);
    }


    @Transactional
    @Override
    public LoanItemResponseDTO returnLoanItem(UUID id, LoanItemUpdateRequestDTO dto) {
        // Obtenemos el item
        LoanItem loanItemToUpdate = loanItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan Item Not Found."));

        if (loanItemToUpdate.getRealReturnDate() != null) {
            throw  new IllegalStateException("This loan item was already returned.");
        }

        // Marcar devolución
        loanItemToUpdate.setRealReturnDate(dto.realReturnDate());
        loanItemToUpdate.setDamaged(dto.damaged());

        // Actualizamos el libro
        Book book = loanItemToUpdate.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + loanItemToUpdate.getQuantity());

        // Revisar el estado del préstamo
        Loan loan = loanItemToUpdate.getLoan();

        boolean allReturned = loan.getItems().stream()
                .allMatch(item -> item.getRealReturnDate() != null);

        if (allReturned) {
            loan.setStatus(LoanStatus.RETURNED);
        } else {
            loan.setStatus(LoanStatus.ACTIVE);
        }

        // Retornar respuesta
        return loanItemMapper.toLoanItemResponseDTO(loanItemToUpdate);
    }
}
