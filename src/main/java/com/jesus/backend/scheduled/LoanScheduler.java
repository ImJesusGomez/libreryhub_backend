package com.jesus.backend.scheduled;

import com.jesus.backend.mail.EmailService;
import com.jesus.backend.model.Loan;
import com.jesus.backend.model.LoanItem;
import com.jesus.backend.model.enums.LoanStatus;
import com.jesus.backend.repository.LoanRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanScheduler {
    private final LoanRepository loanRepository;
    private final EmailService emailService;

    public LoanScheduler(LoanRepository loanRepository, EmailService emailService) {
        this.loanRepository = loanRepository;
        this.emailService = emailService;
    }
    
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateExpiredLoans() {

        LocalDate today = LocalDate.now();

        List<Loan> activeLoans = loanRepository.findByStatus(LoanStatus.ACTIVE);

        for (Loan loan : activeLoans) {

            boolean hasExpiredItems = false;
            boolean hasItemsExpiringTomorrow = false;

            for (LoanItem item : loan.getItems()) {

                LocalDate estimatedReturnDate = item.getEstimatedReturnDate();

                // Vence hoy o ya venció
                if (!estimatedReturnDate.isAfter(today)) {
                    hasExpiredItems = true;
                }

                // Vence mañana
                if (estimatedReturnDate.minusDays(1).isEqual(today)) {
                    hasItemsExpiringTomorrow = true;
                }
            }

            // Actualizamos estado si corresponde
            if (hasExpiredItems) {
                loan.setStatus(LoanStatus.OVERDUE);
            }

            // Enviar correo de vencido
            if (hasExpiredItems) {
                try {
                    emailService.sendEmail(
                            loan.getCustomer().getEmail(),
                            loan.getCustomer().getFirstName(),
                            loan.getCustomer().getLastName(),
                            "Hoy"
                    );
                } catch (Exception e) {
                    System.err.println("Error sending overdue email: " + e.getMessage());
                }
            }

            // Enviar correo preventivo
            else if (hasItemsExpiringTomorrow) {
                try {
                    emailService.sendEmail(
                            loan.getCustomer().getEmail(),
                            loan.getCustomer().getFirstName(),
                            loan.getCustomer().getLastName(),
                            "Mañana"
                    );
                } catch (Exception e) {
                    System.err.println("Error sending reminder email: " + e.getMessage());
                }
            }
        }
    }
}
