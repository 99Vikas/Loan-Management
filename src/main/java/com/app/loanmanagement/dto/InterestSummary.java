package com.app.loanmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class InterestSummary {

    private Integer interestRatePerDay;
    private BigInteger totalRemainingAmount;
    private String lenderIds;
    private String customerIds;
}
