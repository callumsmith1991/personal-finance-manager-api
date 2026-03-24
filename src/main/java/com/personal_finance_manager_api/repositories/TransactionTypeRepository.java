package com.personal_finance_manager_api.repositories;

import com.personal_finance_manager_api.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer> {
}
