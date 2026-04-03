package com.personal_finance_manager_api.validation.annotations;

import com.personal_finance_manager_api.validation.ValidMonthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidMonthValidator.class)
@Documented
public @interface ValidMonth {
    String message() default "Month must be between 1 and 12";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

