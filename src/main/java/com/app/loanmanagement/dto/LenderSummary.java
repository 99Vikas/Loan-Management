package com.app.loanmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class LenderSummary {

    private String lenderId;
    private BigInteger totalRemainingAmount;
    private BigInteger totalInterestRatePerDay;
    private double totalPenaltyRatePerDay;
}
