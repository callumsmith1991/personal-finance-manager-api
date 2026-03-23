package com.personal_finance_manager_api.validation;

import com.personal_finance_manager_api.dtos.requests.RegisterUserRequestDTO;
import com.personal_finance_manager_api.validation.annotations.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterUserRequestDTO> {
    @Override
    public boolean isValid(RegisterUserRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true; // Let @NotNull handle this case
        }
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();
        return password != null && password.equals(confirmPassword);
    }
}
