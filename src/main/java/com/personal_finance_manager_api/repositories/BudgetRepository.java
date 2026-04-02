package com.personal_finance_manager_api.repositories;

import com.personal_finance_manager_api.models.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    List<Budget> findAllByUserId(Integer userId);

    boolean existsByIdAndUserId(Integer pathUserId, Long authenticatedUserId);
}
