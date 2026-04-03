package com.personal_finance_manager_api.dtos.requests;

import com.personal_finance_manager_api.validation.annotations.AmountNotNegative;
import com.personal_finance_manager_api.validation.annotations.ValidMonth;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateBudgetRequestDTO {

    @NotNull(message = "Amount cannot be null")
    @AmountNotNegative
    private BigDecimal amount;

    @NotNull(message = "Month cannot be null")
    @ValidMonth
    private Integer month;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}
