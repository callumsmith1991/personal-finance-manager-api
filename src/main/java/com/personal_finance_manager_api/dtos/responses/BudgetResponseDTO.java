package com.personal_finance_manager_api.dtos.responses;

import java.math.BigDecimal;

public class BudgetResponseDTO {
    private Integer id;
    private BigDecimal amount;
    private Integer month;

    public BudgetResponseDTO(Integer id, BigDecimal amount, Integer month) {
        this.id = id;
        this.amount = amount;
        this.month = month;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
