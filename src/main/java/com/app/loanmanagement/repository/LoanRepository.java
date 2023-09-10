package com.app.loanmanagement.repository;

import com.app.loanmanagement.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {
    List<Loan> findByDueDateBeforeAndRemainingAmountGreaterThan(LocalDate today, BigDecimal zero);

    @Query(nativeQuery = true,
            value = "select lender_id as lenderId, sum(remaining_amount) as totalRemainingAmount, " +
                    "sum(interest_rate_per_day) as totalInterestRatePerDay, " +
                    "sum(penalty_rate_per_day) as totalPenaltyRatePerDay from loan GROUP BY lender_id")
    List<Object[]> aggregateLoanByLender();

    @Query(nativeQuery = true,
            value = "select interest_rate_per_day as interestRatePerDay, sum(remaining_amount) as " +
                    "totalRemainingAmount, GROUP_CONCAT(lender_id) as lenderIds, " +
                    "GROUP_CONCAT(customer_id) as customerIds from loan GROUP BY interest_rate_per_day")
    List<Object[]> aggregateLoanByInterest();

    @Query(nativeQuery = true,
            value = "select customer_id as customerId, sum(remaining_amount) as totalRemainingAmount, GROUP_CONCAT(lender_id) as lenderIds, " +
                    "sum(interest_rate_per_day) as totalInterestRatePerDay, sum(penalty_rate_per_day) " +
                    "as totalPenaltyRatePerDay from loan GROUP BY customer_id")
    List<Object[]> aggregateLoanByCustomerId();
}
