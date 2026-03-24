package com.personal_finance_manager_api.validation.annotations;

import com.personal_finance_manager_api.validation.AmountNotNegativeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AmountNotNegativeValidator.class)
@Documented
public @interface AmountNotNegative {
    String message() default "Amount must not be negative";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
