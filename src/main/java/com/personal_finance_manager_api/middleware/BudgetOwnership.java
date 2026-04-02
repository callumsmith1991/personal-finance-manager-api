package com.personal_finance_manager_api.middleware;

import com.personal_finance_manager_api.repositories.BudgetRepository;
import com.personal_finance_manager_api.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BudgetOwnership implements HandlerInterceptor {

    private final AuthService authService;
    private final BudgetRepository budgetRepository;

    public BudgetOwnership(AuthService authService, BudgetRepository budgetRepository) {
        this.authService = authService;
        this.budgetRepository = budgetRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();
        String[] parts = uri.split("/");
        Integer pathUserId = Integer.valueOf(parts[parts.length - 1]);

        Long authenticatedUserId = authService.getAuthenticatedUserId();

        if (!budgetRepository.existsByIdAndUserId(pathUserId, authenticatedUserId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("You are not authorized to access this resource");
            return false;
        }


        return true;
    }

}
