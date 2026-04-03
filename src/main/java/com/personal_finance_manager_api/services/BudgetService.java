package com.personal_finance_manager_api.services;

import com.personal_finance_manager_api.dtos.requests.CreateBudgetRequestDTO;
import com.personal_finance_manager_api.dtos.requests.UpdateBudgetRequestDTO;
import com.personal_finance_manager_api.dtos.responses.BudgetResponseDTO;
import com.personal_finance_manager_api.exceptions.ModelNotFoundException;
import com.personal_finance_manager_api.models.Budget;
import com.personal_finance_manager_api.models.User;
import com.personal_finance_manager_api.repositories.BudgetRepository;
import com.personal_finance_manager_api.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BudgetService {

    private final AuthService authService;
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetService(AuthService authService, BudgetRepository budgetRepository, UserRepository userRepository) {
        this.authService = authService;
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }

    public List<Budget> getBudgets() {
        Integer userId = authService.getAuthenticatedUserId().intValue();
        return budgetRepository.findAllByUserId(userId);
    }

    public BudgetResponseDTO createBudget(CreateBudgetRequestDTO request) throws ModelNotFoundException {
        User user = getAuthenticatedUser();
        Budget budget = new Budget();
        budget.setAmount(request.getAmount());
        budget.setMonth(request.getMonth());
        budget.setUser(user);
        return mapToDTO(budgetRepository.save(budget));
    }

    public BudgetResponseDTO updateBudget(Integer id, UpdateBudgetRequestDTO request) throws ModelNotFoundException {
        Budget existingBudget = budgetRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Budget not found"));
        existingBudget.setAmount(request.getAmount());
        existingBudget.setMonth(request.getMonth());
        return mapToDTO(budgetRepository.save(existingBudget));
    }

    public void deleteBudget(Integer id) throws ModelNotFoundException {
        Budget existingBudget = budgetRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Budget not found"));
        budgetRepository.delete(existingBudget);
    }

    private User getAuthenticatedUser() throws ModelNotFoundException {
        Integer userId = authService.getAuthenticatedUserId().intValue();
        return userRepository.findById(userId)
                .orElseThrow(() -> new ModelNotFoundException("Authenticated user not found"));
    }

    private BudgetResponseDTO mapToDTO(Budget budget) {
        return new BudgetResponseDTO(budget.getId(), budget.getAmount(), budget.getMonth());
    }
}
