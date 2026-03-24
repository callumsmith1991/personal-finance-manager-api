package com.personal_finance_manager_api.validation.annotations;

import com.personal_finance_manager_api.validation.AmountNotNegativeValidator;
import com.personal_finance_manager_api.validation.TransactionTypeExistsValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TransactionTypeExistsValidator.class)
@Documented
public @interface TransactionTypeMustExist {
    String message() default "Transaction type must be either 0 (expense) or 1 (income)";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
