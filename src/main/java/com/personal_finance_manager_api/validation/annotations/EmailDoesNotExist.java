package com.personal_finance_manager_api.validation.annotations;

import com.personal_finance_manager_api.validation.EmailExistsValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailExistsValidator.class)
@Documented
public @interface EmailDoesNotExist {
    String message() default "Email already exists";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
