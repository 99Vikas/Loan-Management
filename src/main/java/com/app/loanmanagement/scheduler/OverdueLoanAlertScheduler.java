package com.app.loanmanagement.scheduler;

import com.app.loanmanagement.model.Loan;
import com.app.loanmanagement.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OverdueLoanAlertScheduler {

    private final LoanRepository loanRepository;

    @Scheduled(cron = "0 0 0 * * *") // Runs daily at midnight and looks for overdue Loans and create alerts
    public void alertForOverdueLoans() {
        log.info("Extracting all overdue loans");
        LocalDate currentDate = LocalDate.now();
        List<Loan> allLoans = loanRepository.findAll();

        for (Loan loan : allLoans) {
            if (currentDate.isAfter(loan.getDueDate())) {
                log.warn("Loan with loan Id {} is overdue and remaining amount is {}.",
                        loan.getLoanId(), loan.getRemainingAmount());
            }
        }
    }
}
