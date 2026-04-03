package com.personal_finance_manager_api.repositories;

import com.personal_finance_manager_api.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllByUserId(Integer userId);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND MONTH(t.date) = :month")
    List<Transaction> findAllByUserIdAndMonth(Integer userId, Integer month);
}
