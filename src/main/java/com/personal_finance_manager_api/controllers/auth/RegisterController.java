package com.personal_finance_manager_api.controllers.auth;

import com.personal_finance_manager_api.controllers.ApiResponse;
import com.personal_finance_manager_api.controllers.Responder;
import com.personal_finance_manager_api.dtos.requests.RegisterUserRequestDTO;
import com.personal_finance_manager_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private UserService userService;

    @Autowired
    private Responder responder;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users/register")
    public ResponseEntity<ApiResponse<Object>> store(@Validated @RequestBody RegisterUserRequestDTO userRequestDTO) {
        return this.responder.created(this.userService.createUser(userRequestDTO));
    }
}
