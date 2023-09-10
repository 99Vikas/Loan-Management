package com.app.loanmanagement.controller;

import com.app.loanmanagement.dto.CustomerSummary;
import com.app.loanmanagement.dto.InterestSummary;
import com.app.loanmanagement.dto.LenderSummary;
import com.app.loanmanagement.model.Loan;
import com.app.loanmanagement.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
@Slf4j
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    /** An Endpoint to create a loan record in database*/
    @PostMapping("/createLoanRecord")
    public ResponseEntity<String> createLoanRecord(@RequestBody Loan loan) {
        return loanService.createLoanRecord(loan);
    }

    /** An Endpoint to give Aggregation of Loans By INTEREST */

    @GetMapping("/aggregateLoanByInterest")
    List<InterestSummary> aggregateLoanByInterest(){
        return loanService.aggregateLoanByInterest();
    }

    /** An Endpoint to give Aggregation of Loans By LENDER */
    @GetMapping("/aggregateLoanByLender")
    List<LenderSummary> aggregateLoanByLender(){
        return loanService.aggregateLoanByLender();
    }

    /** An Endpoint to give Aggregation of Loans By CUSTOMER-ID */
    @GetMapping("/aggregateLoanByCustomerId")
    List<CustomerSummary> aggregateLoanByCustomerId(){
        return loanService.aggregateLoanByCustomerId();
    }


}
