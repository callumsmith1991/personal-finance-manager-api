package com.personal_finance_manager_api.services;

import com.personal_finance_manager_api.exceptions.ModelNotFoundException;
import com.personal_finance_manager_api.models.TransactionType;
import com.personal_finance_manager_api.repositories.TransactionTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionTypeService {

    private final TransactionTypeRepository transactionTypeRepository;

    public TransactionTypeService(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    public TransactionType getTransactionType(Integer typeId) throws ModelNotFoundException {
        return transactionTypeRepository.findById(typeId)
                .orElseThrow(() -> new ModelNotFoundException("Transaction type not found"));
    }
}

