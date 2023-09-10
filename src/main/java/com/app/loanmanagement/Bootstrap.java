package com.app.loanmanagement;

import com.app.loanmanagement.model.Loan;
import com.app.loanmanagement.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class Bootstrap implements ApplicationListener<ApplicationReadyEvent> {

    private final LoanRepository loanRepository;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        List<Loan> loans = new ArrayList<>();
        loans.add(new Loan("L1", "C1", "LEN1", 10000, 10000,
                LocalDate.of(2023, 6, 5), 1,
                LocalDate.of(2023, 7, 5), 0.01));

        loans.add(new Loan("L2", "C1", "LEN1", 20000, 5000,
                LocalDate.of(2023, 6, 1), 1,
                LocalDate.of(2023, 8, 5), 0.01));

        loans.add(new Loan("L3", "C2", "LEN2", 50000, 30000,
                LocalDate.of(2023, 4, 4), 2,
                LocalDate.of(2023, 5, 4), 0.02));

        loans.add(new Loan("L4", "C3", "LEN2", 50000, 30000,
                LocalDate.of(2023, 4, 4), 2,
                LocalDate.of(2023, 5, 4), 0.02));

        loanRepository.saveAll(loans);
    }
}
