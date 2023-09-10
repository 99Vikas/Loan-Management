package com.app.loanmanagement.service;

import com.app.loanmanagement.dto.CustomerSummary;
import com.app.loanmanagement.dto.InterestSummary;
import com.app.loanmanagement.dto.LenderSummary;
import com.app.loanmanagement.model.Loan;
import com.app.loanmanagement.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddLoanPaymentDateBeforeDueDate() {
        // Given
        Loan loan = new Loan();
        loan.setPaymentDate(LocalDate.of(2023, 6, 5));
        loan.setDueDate(LocalDate.of(2023, 6, 1));

        // When
        Exception exception = assertThrows(Exception.class, () -> loanService.createLoanRecord(loan));

        // Then
        assertEquals("Payment date cannot be greater than due date", exception.getMessage());
    }

    @Test
    public void testAddLoanPaymentDateAfterDueDate() {
        // Given
        Loan loan = new Loan();
        loan.setPaymentDate(LocalDate.of(2023, 6, 5));
        loan.setDueDate(LocalDate.of(2023, 6, 6));

        // Mock the repository save method to return the loan
        when(loanRepository.save(loan)).thenReturn(loan);

        // When
        ResponseEntity<String> savedLoan = loanService.createLoanRecord(loan);

        // Then
        assertEquals(ResponseEntity.ok("Loan added to loan records successfully"), savedLoan);
    }

    @Test
    public void testAggregateByLender() {
        // Given
        Object[] lender1Data = new Object[] {"LEN1", BigInteger.valueOf(30000), BigInteger.valueOf(2), 0.02};
        List<Object[]> lenderDataList = new ArrayList<>();
        lenderDataList.add(lender1Data);

        // Mock the repository method to return the lender data
        when(loanRepository.aggregateLoanByLender()).thenReturn(lenderDataList);

        // When
        List<LenderSummary> result = loanService.aggregateLoanByLender();

        // Then
        LenderSummary resultData = result.get(0);
        assertEquals("LEN1", resultData.getLenderId());
        assertEquals(BigInteger.valueOf(30000), resultData.getTotalRemainingAmount());
        assertEquals(BigInteger.valueOf(2), resultData.getTotalInterestRatePerDay());
        assertEquals(0.02, resultData.getTotalPenaltyRatePerDay());
    }

    @Test
    public void testAggregateByInterest() {
        // Given
        Object[] interest1Data = new Object[] {1, BigInteger.valueOf(30000), "LEN1,LEN2", "C1,C2"};
        List<Object[]> interestDataList = new ArrayList<>();
        interestDataList.add(interest1Data);

        // Mock the repository method to return the interest data
        when(loanRepository.aggregateLoanByInterest()).thenReturn(interestDataList);

        // When
        List<InterestSummary> result = loanService.aggregateLoanByInterest();

        // Then
        assertEquals(1, result.size());
        InterestSummary resultData = result.get(0);
        assertEquals(1, resultData.getInterestRatePerDay());
        assertEquals(BigInteger.valueOf(30000), resultData.getTotalRemainingAmount());
        assertEquals("LEN1,LEN2", resultData.getLenderIds());
        assertEquals("C1,C2", resultData.getCustomerIds());
    }

    @Test
    public void testAggregateByCustomerId() {
        // Given
        Object[] customer1Data = new Object[] {"C1", BigInteger.valueOf(30000), "LEN1,LEN2", BigInteger.valueOf(3), 0.03};
        List<Object[]> customerDataList = new ArrayList<>();
        customerDataList.add(customer1Data);

        // Mock the repository method to return the customer data
        when(loanRepository.aggregateLoanByCustomerId()).thenReturn(customerDataList);

        // When
        List<CustomerSummary> result = loanService.aggregateLoanByCustomerId();

        // Then
        assertEquals(1, result.size());
        CustomerSummary resultData = result.get(0);
        assertEquals("C1", resultData.getCustomerId());
        assertEquals(BigInteger.valueOf(30000), resultData.getTotalRemainingAmount());
        assertEquals("LEN1,LEN2", resultData.getLenderIds());
        assertEquals(BigInteger.valueOf(3), resultData.getTotalInterestRatePerDay());
        assertEquals(0.03, resultData.getTotalPenaltyRatePerDay());
    }
}