package com.personal_finance_manager_api.services;

import com.personal_finance_manager_api.dtos.requests.RegisterUserRequestDTO;
import com.personal_finance_manager_api.dtos.requests.UpdateUserRequestDTO;
import com.personal_finance_manager_api.dtos.responses.UserDetailsDTO;
import com.personal_finance_manager_api.exceptions.ModelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.personal_finance_manager_api.repositories.UserRepository;
import com.personal_finance_manager_api.models.User;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserDetailsDTO getUserById(Integer id) throws ModelNotFoundException {
        User user = this.repo.findById(id).orElseThrow(() -> new ModelNotFoundException("User not found"));
        return new UserDetailsDTO(user.getId(), user.getName(), user.getEmail());
    }

    public String createUser(RegisterUserRequestDTO request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        this.repo.save(user);
        return "User successfully created";
    }

    public String updateUser(Integer id, UpdateUserRequestDTO request) throws ModelNotFoundException {
        User user = this.repo.findById(id).orElseThrow(() -> new ModelNotFoundException("User not found"));
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        this.repo.save(user);
        return "User successfully updated";
    }

    public boolean loginSuccessful(String email, String password) {
        Optional<User> userOpt = this.repo.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}
