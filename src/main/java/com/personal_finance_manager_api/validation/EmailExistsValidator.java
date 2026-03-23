package com.personal_finance_manager_api.validation;

import com.personal_finance_manager_api.dtos.requests.RegisterUserRequestDTO;
import com.personal_finance_manager_api.repositories.UserRepository;
import com.personal_finance_manager_api.validation.annotations.EmailDoesNotExist;
import jakarta.validation.ConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailExistsValidator implements ConstraintValidator<EmailDoesNotExist, RegisterUserRequestDTO> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(RegisterUserRequestDTO dto, jakarta.validation.ConstraintValidatorContext context) {
        String email = dto.getEmail();
        return email != null && !userRepository.findByEmail(email).isPresent();
    }
}
