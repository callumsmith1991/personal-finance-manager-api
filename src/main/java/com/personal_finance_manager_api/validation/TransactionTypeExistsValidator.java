package com.personal_finance_manager_api.validation;

import com.personal_finance_manager_api.validation.annotations.TransactionTypeMustExist;
import jakarta.validation.ConstraintValidator;

public class TransactionTypeExistsValidator implements ConstraintValidator<TransactionTypeMustExist, Integer> {
    @Override
    public boolean isValid(Integer value, jakarta.validation.ConstraintValidatorContext context) {
        if (value == null) return true;
        return value == 0 || value == 1;
    }
}
