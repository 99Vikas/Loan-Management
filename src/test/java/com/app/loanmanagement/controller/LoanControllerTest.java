package com.app.loanmanagement.controller;

import com.app.loanmanagement.dto.CustomerSummary;
import com.app.loanmanagement.dto.InterestSummary;
import com.app.loanmanagement.dto.LenderSummary;
import com.app.loanmanagement.model.Loan;
import com.app.loanmanagement.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoanControllerTest {
    private MockMvc mockMvc;
    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @Test
    void addLoan() throws Exception {
        // here is the mock data
        Loan loan = new Loan("L1", "C1", "LEN1", 10000, 10000, LocalDate.of(2023, 6, 5), 1, LocalDate.of(2023, 7, 5), 0.01);

        // Mock the service method to return the loan when save is called
        when(loanService.createLoanRecord(any())).thenReturn(ResponseEntity.ok("Loan added to loan records successfully "));

        // Perform the POST request to your endpoint
        mockMvc.perform(post("/loan/createLoanRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"loanId\": \"L1\",\n" +
                                "  \"customerId\": \"C1\",\n" +
                                "  \"lenderId\": \"LEN1\",\n" +
                                "  \"amount\": 10000,\n" +
                                "  \"remainingAmount\": 10000,\n" +
                                "  \"paymentDate\": \"2023-06-05\",\n" +
                                "  \"interestPerDay\": 1,\n" +
                                "  \"dueDate\": \"2023-07-05\",\n" +
                                "  \"penaltyPerDay\": 0.01\n" +
                                "}"))
                .andExpect(status().isOk());

        // Verify that the addLoan method in the service is called with the loan argument
        verify(loanService, times(1)).createLoanRecord(any());
    }

    @Test
    public void testAggregateByLender() throws Exception {
        // here is the mock data
        LenderSummary lenderData = new LenderSummary("LEN1", BigInteger.valueOf(50000), BigInteger.valueOf(1), 0.02);
        List<LenderSummary> lenderSummaries = List.of(lenderData);

        // Mock the service method to return the lender data
        when(loanService.aggregateLoanByLender()).thenReturn(lenderSummaries);

        // Perform the GET request to your endpoint
        mockMvc.perform(get("/loan/aggregateLoanByLender"))
                .andExpect(status().isOk())
                // You can add more assertions for the response JSON if needed
                .andExpect(jsonPath("$[0].lenderId").value(lenderSummaries.get(0).getLenderId()))
                .andExpect(jsonPath("$[0].totalRemainingAmount").value(lenderSummaries.get(0).getTotalRemainingAmount()))
                .andExpect(jsonPath("$[0].totalInterestRatePerDay").value(lenderSummaries.get(0).getTotalInterestRatePerDay()))
                .andExpect(jsonPath("$[0].totalPenaltyRatePerDay").value(lenderSummaries.get(0).getTotalPenaltyRatePerDay()));
    }

    @Test
    public void testAggregateByInterest() throws Exception {
        // here is the mock data
        InterestSummary lenderData = new InterestSummary(1, BigInteger.valueOf(10000), "LEN1, LEN2", "C1, C2");
        List<InterestSummary> interestSummaries = List.of(lenderData);

        // Mock the service method to return the lender data
        when(loanService.aggregateLoanByInterest()).thenReturn(interestSummaries);

        // Perform the GET request to your endpoint
        mockMvc.perform(get("/loan/aggregateLoanByInterest"))
                .andExpect(status().isOk())
                // You can add more assertions for the response JSON if needed
                .andExpect(jsonPath("$[0].interestRatePerDay").value(interestSummaries.get(0).getInterestRatePerDay()))
                .andExpect(jsonPath("$[0].totalRemainingAmount").value(interestSummaries.get(0).getTotalRemainingAmount()))
                .andExpect(jsonPath("$[0].lenderIds").value(interestSummaries.get(0).getLenderIds()))
                .andExpect(jsonPath("$[0].customerIds").value(interestSummaries.get(0).getCustomerIds()));
    }

    @Test
    public void testAggregateByCustomerId() throws Exception {
        // here is the mock data
        CustomerSummary customer1Data = new CustomerSummary("C1", BigInteger.valueOf(10000),
                "LEN1, LEN2", BigInteger.valueOf(3), 0.02);
        List<CustomerSummary> customerSummaries = List.of(customer1Data);

        // Mock the service method to return the customer data
        when(loanService.aggregateLoanByCustomerId()).thenReturn(customerSummaries);

        // Perform the GET request to your endpoint
        mockMvc.perform(get("/loan/aggregateLoanByCustomerId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(customerSummaries.get(0).getCustomerId()))
                .andExpect(jsonPath("$[0].totalRemainingAmount").value(customerSummaries.get(0).getTotalRemainingAmount()))
                .andExpect(jsonPath("$[0].lenderIds").value(customerSummaries.get(0).getLenderIds()))
                .andExpect(jsonPath("$[0].totalPenaltyRatePerDay").value(customerSummaries.get(0).getTotalPenaltyRatePerDay()))
                .andExpect(jsonPath("$[0].totalInterestRatePerDay").value(customerSummaries.get(0).getTotalInterestRatePerDay()));
    }
}