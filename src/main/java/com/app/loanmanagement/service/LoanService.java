package com.app.loanmanagement.service;

import com.app.loanmanagement.dto.CustomerSummary;
import com.app.loanmanagement.dto.InterestSummary;
import com.app.loanmanagement.dto.LenderSummary;
import com.app.loanmanagement.exception.BadRequest400Exception;
import com.app.loanmanagement.model.Loan;
import com.app.loanmanagement.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService {

    private final LoanRepository loanRepository;

    /** Methods to create a loan record in database*/
    public ResponseEntity<String> createLoanRecord(Loan loan) {
        if (loan.getPaymentDate().isAfter(loan.getDueDate())) {
            throw new BadRequest400Exception("Payment date cannot be greater than due date");
        }
        loanRepository.save(loan);
        return ResponseEntity.ok("Loan added to loan records successfully");
    }

    /** Method giving Aggregation of Loans By INTEREST */

    public List<InterestSummary> aggregateLoanByInterest() {
        List<Object[]> interestAggregation = loanRepository.aggregateLoanByInterest();
        List<InterestSummary> interestSummaries = new ArrayList<>();
        interestAggregation.forEach(interest -> {
            interestSummaries.add(new InterestSummary((Integer) interest[0],
                    (BigInteger) interest[1], (String) interest[2], (String) interest[3]));
        });
        return interestSummaries;
    }

    /** Method giving Aggregation of Loans By LENDER */
    public List<LenderSummary> aggregateLoanByLender() {
        List<Object[]> lenderAggregation = loanRepository.aggregateLoanByLender();
        List<LenderSummary> lenderSummaries = new ArrayList<>();
        lenderAggregation.forEach(lender -> {
            lenderSummaries.add(new LenderSummary((String) lender[0], (BigInteger) lender[1], (BigInteger) lender[2], (Double) lender[3]));
        });
        return lenderSummaries;
    }

    /** Method giving Aggregation of Loans By CUSTOMER-ID */
    public List<CustomerSummary> aggregateLoanByCustomerId() {
        List<Object[]> customerAggregation =  loanRepository.aggregateLoanByCustomerId();
        List<CustomerSummary> customerSummaries = new ArrayList<>();
        customerAggregation.forEach(customer -> {
            customerSummaries.add(new CustomerSummary((String) customer[0], (BigInteger) customer[1],
                    (String) customer[2], (BigInteger) customer[3], (double) customer[4]));
        });
        return customerSummaries;
    }
}
