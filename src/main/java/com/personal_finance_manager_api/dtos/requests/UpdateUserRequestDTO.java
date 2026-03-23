package com.personal_finance_manager_api.dtos.requests;

import jakarta.validation.constraints.Email;

public class UpdateUserRequestDTO {
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    public UpdateUserRequestDTO() {}

    public UpdateUserRequestDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
