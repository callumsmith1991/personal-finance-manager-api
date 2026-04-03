package com.personal_finance_manager_api.repositories;

import com.personal_finance_manager_api.models.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    List<Budget> findAllByUserId(Integer userId);

    boolean existsByIdAndUserId(Integer pathUserId, Long authenticatedUserId);

    @Query("SELECT b FROM Budget b WHERE b.user.id = :userId AND b.month = :month")
    Budget findByUserIdAndMonth(Integer userId, Integer month);
}
