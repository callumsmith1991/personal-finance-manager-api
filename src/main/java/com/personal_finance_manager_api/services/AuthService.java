package com.personal_finance_manager_api.services;

import com.personal_finance_manager_api.models.User;
import com.personal_finance_manager_api.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getAuthenticatedUserId() throws RuntimeException
    {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Authenticated user not found"));
        return authenticatedUser.getId();
    }
}
