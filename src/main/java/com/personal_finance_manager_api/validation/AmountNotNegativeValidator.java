package com.personal_finance_manager_api.validation;

import com.personal_finance_manager_api.dtos.requests.CreateTransactionRequestDTO;
import com.personal_finance_manager_api.validation.annotations.AmountNotNegative;
import com.personal_finance_manager_api.validation.interfaces.HasAmount;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class AmountNotNegativeValidator implements ConstraintValidator<AmountNotNegative, BigDecimal> {
    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value.compareTo(BigDecimal.ZERO) >= 0;
    }
}