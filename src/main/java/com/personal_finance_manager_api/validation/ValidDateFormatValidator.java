package com.personal_finance_manager_api.validation;

import com.personal_finance_manager_api.validation.annotations.ValidDateFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidDateFormatValidator implements ConstraintValidator<ValidDateFormat, String> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;
        
        try {
            LocalDate date = LocalDate.parse(value, formatter);
            // Ensure date is not in the past (optional - can be removed if past transactions allowed)
            return !date.isBefore(LocalDate.now().minusYears(1)); // Allow 1 year back
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

