package com.personal_finance_manager_api.middleware;

import com.personal_finance_manager_api.controllers.Responder;
import com.personal_finance_manager_api.models.User;
import com.personal_finance_manager_api.repositories.UserRepository;
import com.personal_finance_manager_api.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Component
public class UserOwnership implements HandlerInterceptor {

    private final Responder responder;
    private final UserRepository userRepository;
    private final AuthService authService;

    public UserOwnership(Responder responder, UserRepository userRepository, AuthService authService) {
        this.responder = responder;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();
        String[] parts = uri.split("/");
        Integer pathUserId = Integer.valueOf(parts[parts.length - 1]);
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long authenticatedUserId = authService.getAuthenticatedUserId();

        if(!authenticatedUserId.equals(pathUserId.longValue())) {
            responder.unauthorized("You are not authorized to access this resource");
            return false;
        }

        return true;
    }
}
