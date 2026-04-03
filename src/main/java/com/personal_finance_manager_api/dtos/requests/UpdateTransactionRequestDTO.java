package com.personal_finance_manager_api.dtos.requests;

import com.personal_finance_manager_api.validation.annotations.AmountNotNegative;
import com.personal_finance_manager_api.validation.annotations.TransactionTypeMustExist;
import com.personal_finance_manager_api.validation.annotations.ValidDateFormat;
import com.personal_finance_manager_api.validation.interfaces.HasAmount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class UpdateTransactionRequestDTO implements HasAmount {

    @Size(min = 1, max = 255, message = "Description must be between 1 and 255 characters")
    private String description;

    @NotNull(message = "Amount cannot be null")
    @AmountNotNegative
    private BigDecimal amount;

    @Size(min = 1, max = 50, message = "Category must be between 1 and 50 characters")
    private String category;

    @NotBlank(message = "Date cannot be blank")
    @ValidDateFormat
    private String date;

    @NotNull(message = "Type cannot be null")
    @TransactionTypeMustExist
    private Integer type;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
