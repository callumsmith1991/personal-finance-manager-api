package com.personal_finance_manager_api.validation.annotations;

import com.personal_finance_manager_api.validation.ValidDateFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDateFormatValidator.class)
@Documented
public @interface ValidDateFormat {
    String message() default "Date must be in format yyyy-MM-dd";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

