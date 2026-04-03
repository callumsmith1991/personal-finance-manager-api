package com.personal_finance_manager_api.validation;

import com.personal_finance_manager_api.validation.annotations.ValidMonth;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidMonthValidator implements ConstraintValidator<ValidMonth, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value >= 1 && value <= 12;
    }
}

