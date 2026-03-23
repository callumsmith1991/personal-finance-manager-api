package com.personal_finance_manager_api.middleware;

import com.personal_finance_manager_api.models.Transaction;
import com.personal_finance_manager_api.repositories.TransactionRepository;
import com.personal_finance_manager_api.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TransactionOwnership implements HandlerInterceptor {

    private final AuthService authService;
    private final TransactionRepository transactionRepository;

    public TransactionOwnership(AuthService authService, TransactionRepository transactionRepository) {
        this.authService = authService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String uri = request.getRequestURI();
        String[] parts = uri.split("/");
        Integer pathTransactionId = Integer.valueOf(parts[parts.length - 1]);

        Transaction transaction = this.transactionRepository.findById(pathTransactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));
        Long authenticatedUserId = authService.getAuthenticatedUserId();

        if (!transaction.getUserId().equals(authenticatedUserId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("You are not authorized to access this resource");
            return false;
        }

        return true;
    }
}
