package com.personal_finance_manager_api.budget;

import com.personal_finance_manager_api.exceptions.ModelNotFoundException;
import com.personal_finance_manager_api.models.Budget;
import com.personal_finance_manager_api.models.Transaction;
import com.personal_finance_manager_api.repositories.BudgetRepository;
import com.personal_finance_manager_api.repositories.TransactionRepository;
import com.personal_finance_manager_api.services.AuthService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class OverBudgetCalculator {

    private final AuthService authService;
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    private static final Integer EXPENSE_ID = 2;
    private static final Integer INCOME_ID = 1;

    public OverBudgetCalculator(
            AuthService authService,
            TransactionRepository transactionRepository,
            BudgetRepository budgetRepository
    ) {
        this.authService = authService;
        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
    }

    public Map<String, Object> isOverBudget(Integer month) {
        Budget budget = getCurrentMonthBudget(month);

        if (budget == null) {
            return Map.of("overBudget", false, "amount", BigDecimal.ZERO);
        }

        BigDecimal budgetAmount = budget.getAmount();
        List<Transaction> transactions = getTransactionsForCurrentMonth(month);
        BigDecimal amountOverBudget = calculateSpendings(budgetAmount, transactions);

        return Map.of(
                "overBudget", amountOverBudget.compareTo(BigDecimal.ZERO) < 0,
                "amount", amountOverBudget
        );
    }

    private BigDecimal calculateSpendings(BigDecimal budgetAmount, List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            if (transaction.getType().getId().equals(EXPENSE_ID)) {
                budgetAmount = budgetAmount.subtract(transaction.getAmount());
            } else if (transaction.getType().getId().equals(INCOME_ID)) {
                budgetAmount = budgetAmount.add(transaction.getAmount());
            }
        }
        return budgetAmount;
    }

    private Budget getCurrentMonthBudget(Integer month) {
        return budgetRepository.findByUserIdAndMonth(authService.getAuthenticatedUserId().intValue(), month);
    }

    private List<Transaction> getTransactionsForCurrentMonth(Integer month) {
        return transactionRepository.findAllByUserIdAndMonth(authService.getAuthenticatedUserId().intValue(), month);
    }
}