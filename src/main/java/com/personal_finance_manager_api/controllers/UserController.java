package com.personal_finance_manager_api.controllers;

import com.personal_finance_manager_api.dtos.requests.UpdateUserRequestDTO;
import com.personal_finance_manager_api.dtos.responses.UserDetailsDTO;
import com.personal_finance_manager_api.exceptions.ModelNotFoundException;
import com.personal_finance_manager_api.models.User;
import com.personal_finance_manager_api.repositories.UserRepository;
import com.personal_finance_manager_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    Responder responder;

    private final UserService service;
    private final UserRepository repo;

    public UserController(UserService service, UserRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<ApiResponse<Object>> show(@PathVariable Integer id) throws ModelNotFoundException {
        UserDetailsDTO userDetails = this.service.getUserById(id);
        return this.responder.success(userDetails);
    }

    @PatchMapping("/api/users/{id}")
    public ResponseEntity<ApiResponse<Object>> update(@PathVariable Integer id, @RequestBody @Valid UpdateUserRequestDTO request) throws ModelNotFoundException {
        return responder.success(this.service.updateUser(id, request));
    }


}