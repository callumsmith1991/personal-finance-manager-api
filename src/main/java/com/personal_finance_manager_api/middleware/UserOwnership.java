package com.personal_finance_manager_api.middleware;

import com.personal_finance_manager_api.controllers.Responder;
import com.personal_finance_manager_api.models.User;
import com.personal_finance_manager_api.repositories.UserRepository;
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

    public UserOwnership(Responder responder, UserRepository userRepository) {
        this.responder = responder;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();
        String[] parts = uri.split("/");
        Integer pathUserId = Integer.valueOf(parts[parts.length - 1]);
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> authenticatedUser = userRepository.findByEmail(email);

        if(authenticatedUser.isEmpty()) {
            responder.unauthorized("You are not authorized to access this resource");
            return false;
        }
        
        Long authenticatedUserId = authenticatedUser.get().getId();

        if(!authenticatedUserId.equals(pathUserId.longValue())) {
            responder.unauthorized("You are not authorized to access this resource");
            return false;
        }

        return true;
    }
}
