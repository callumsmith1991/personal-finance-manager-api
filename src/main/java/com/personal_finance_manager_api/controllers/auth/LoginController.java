package com.personal_finance_manager_api.controllers.auth;

import com.personal_finance_manager_api.controllers.ApiResponse;
import com.personal_finance_manager_api.controllers.Responder;
import com.personal_finance_manager_api.dtos.requests.LoginRequestDTO;
import com.personal_finance_manager_api.jwt.JwtTokenGenerator;
import com.personal_finance_manager_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    Responder responder;

    private final UserService userService;
    private final JwtTokenGenerator jwtTokenGenerator;

    public LoginController(UserService userService, JwtTokenGenerator jwtTokenGenerator) {
        this.userService = userService;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @PostMapping("/api/login")
    public ResponseEntity<ApiResponse<Object>> login(LoginRequestDTO loginRequestDTO) {
        boolean isLoginSuccessful = userService.loginSuccessful(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        if (isLoginSuccessful) {
            return responder.success(Map.of(
                    "message", "Login successfull",
                    "authToken", this.jwtTokenGenerator.generateToken(loginRequestDTO.getEmail())
            ));
        }

        return responder.unauthorized("Invalid email or password");
    }
}
