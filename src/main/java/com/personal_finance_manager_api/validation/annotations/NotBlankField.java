package com.personal_finance_manager_api.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@NotBlank(message = "This field cannot be blank")
@Documented
public @interface NotBlankField {
    String message() default "This field cannot be blank";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

