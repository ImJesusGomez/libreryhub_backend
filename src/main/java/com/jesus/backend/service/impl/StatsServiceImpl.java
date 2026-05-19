package com.jesus.backend.service.impl;

import com.jesus.backend.dto.response.StatsResponseDTO;
import com.jesus.backend.repository.BookRepository;
import com.jesus.backend.repository.CustomerRepository;
import com.jesus.backend.repository.LoanRepository;
import com.jesus.backend.service.StatsService;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;

    public StatsServiceImpl(BookRepository bookRepository, LoanRepository loanRepository, CustomerRepository customerRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public StatsResponseDTO getStats() {

        return new StatsResponseDTO(
                bookRepository.count(),
                customerRepository.count(),
                loanRepository.count(),
                loanRepository.countAllLoansOverdue()
        );
    }
}
