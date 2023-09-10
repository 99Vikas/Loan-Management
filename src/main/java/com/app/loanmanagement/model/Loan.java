package com.app.loanmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Table(name = "loan")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

    @Id
    @Column(name = "loan_id")
    String loanId;

    @Column(name = "customer_id")
    String customerId;

    @Column(name = "lender_id")
    String lenderId;

    @Column(name = "amount")
    Integer amount;

    @Column(name = "remaining_amount")
    Integer remainingAmount;

    @Column(name = "payment_date")
    LocalDate paymentDate;

    @Column(name = "interest_rate_per_day")
    int interestRatePerDay;

    @Column(name = "due_date")
    LocalDate dueDate;

    @Column(name = "penalty_rate_per_day")
    double penaltyRatePerDay;

    @Column(name = "cancel")
    Boolean cancel;

    public Loan(String loanId, String customerId, String lenderId, Integer amount,
                Integer remainingAmount, LocalDate paymentDate, int interestRatePerDay,
                LocalDate dueDate, double penaltyRatePerDay) {
        this.loanId = loanId;
        this.customerId = customerId;
        this.lenderId = lenderId;
        this.amount = amount;
        this.remainingAmount = remainingAmount;
        this.paymentDate = paymentDate;
        this.interestRatePerDay = interestRatePerDay;
        this.dueDate = dueDate;
        this.penaltyRatePerDay = penaltyRatePerDay;
    }
}
