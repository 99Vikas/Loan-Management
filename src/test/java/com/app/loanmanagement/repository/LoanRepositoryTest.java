package com.app.loanmanagement.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LoanRepositoryTest {

    @Mock
    private LoanRepository loanRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAggregateByLender() {
        // Given
        Object[] lender1Data = new Object[] {"LEN1", 30000.0, 0.02, 0.02};
        List<Object[]> lenderDataList = new ArrayList<>();
        lenderDataList.add(lender1Data);

        // Mock the repository method to return the lender data
        when(loanRepository.aggregateLoanByLender()).thenReturn(lenderDataList);

        // When
        List<Object[]> result = loanRepository.aggregateLoanByLender();

        // Then
        assertEquals(1, result.size());
        Object[] resultData = result.get(0);
        assertEquals("LEN1", resultData[0]);
        assertEquals(30000.0, resultData[1]);
        assertEquals(0.02, resultData[2]);
        assertEquals(0.02, resultData[3]);
    }

    @Test
    public void testAggregateByInterest() {
        // Given
        Object[] interest1Data = new Object[] {0.01, 30000.0, "LEN1,LEN2", "C1,C2"};
        List<Object[]> interestDataList = new ArrayList<>();
        interestDataList.add(interest1Data);

        // Mock the repository method to return the interest data
        when(loanRepository.aggregateLoanByInterest()).thenReturn(interestDataList);

        // When
        List<Object[]> result = loanRepository.aggregateLoanByInterest();

        // Then
        assertEquals(1, result.size());
        Object[] resultData = result.get(0);
        assertEquals(0.01, resultData[0]);
        assertEquals(30000.0, resultData[1]);
        assertEquals("LEN1,LEN2", resultData[2]);
        assertEquals("C1,C2", resultData[3]);
    }

    @Test
    public void testAggregateByCustomerId() {
        // Given
        Object[] customer1Data = new Object[] {"C1", 30000.0, "LEN1,LEN2", 0.03, 0.03};
        List<Object[]> customerDataList = new ArrayList<>();
        customerDataList.add(customer1Data);

        // Mock the repository method to return the customer data
        when(loanRepository.aggregateLoanByCustomerId()).thenReturn(customerDataList);

        // When
        List<Object[]> result = loanRepository.aggregateLoanByCustomerId();

        // Then
        assertEquals(1, result.size());
        Object[] resultData = result.get(0);
        assertEquals("C1", resultData[0]);
        assertEquals(30000.0, resultData[1]);
        assertEquals("LEN1,LEN2", resultData[2]);
        assertEquals(0.03, resultData[3]);
        assertEquals(0.03, resultData[4]);
    }
}