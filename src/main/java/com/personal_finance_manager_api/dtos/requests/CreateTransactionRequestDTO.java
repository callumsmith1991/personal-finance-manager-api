package com.personal_finance_manager_api.dtos.requests;

public class CreateTransactionRequestDTO {
    private String description;
    private float amount;
    private String category;
    private String date;

    public CreateTransactionRequestDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return amount >= 0 ? "income" : "expense";
    }

}

