package com.app.loanmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class CustomerSummary {

    private String customerId;
    private BigInteger totalRemainingAmount;
    private String lenderIds;
    private BigInteger totalInterestRatePerDay;
    private double totalPenaltyRatePerDay;
}
