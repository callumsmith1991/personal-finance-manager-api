package com.personal_finance_manager_api.services;

import com.personal_finance_manager_api.dtos.requests.CreateTransactionRequestDTO;
import com.personal_finance_manager_api.dtos.requests.UpdateTransactionRequestDTO;
import com.personal_finance_manager_api.exceptions.ModelNotFoundException;
import com.personal_finance_manager_api.helpers.Helpers;
import com.personal_finance_manager_api.models.Transaction;
import com.personal_finance_manager_api.models.User;
import com.personal_finance_manager_api.repositories.TransactionRepository;
import com.personal_finance_manager_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class TransactionService {

    @Autowired
    Helpers helpers;

    private final AuthService authService;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    private TransactionService(AuthService authService, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.authService = authService;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public List<Transaction> getTransactions()
    {
        Integer userId = authService.getAuthenticatedUserId().intValue();
        return transactionRepository.findAllByUserId(userId);
    }

    public Transaction createTransaction(CreateTransactionRequestDTO request) throws RuntimeException {
        User user = this.getAuthenticatedUser();
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setCategory(request.getCategory());
        transaction.setUser(user);
        transaction.setType(request.getType());
        transaction.setDate(helpers.formatDate(request.getDate()));

        return transactionRepository.save(transaction);
    }

    private User getAuthenticatedUser() throws RuntimeException
    {
        Integer userId = authService.getAuthenticatedUserId().intValue();
        return this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    public Transaction getTransactionById(Integer id) throws ModelNotFoundException {
        Integer userId = authService.getAuthenticatedUserId().intValue();
        return transactionRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("Transaction not found"));
    }

    public Transaction updateTransaction(Integer id, UpdateTransactionRequestDTO request) throws ModelNotFoundException {
        Transaction transaction = this.transactionRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("Transaction not found"));
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setCategory(request.getCategory());
        transaction.setType(request.getType());
        transaction.setDate(helpers.formatDate(request.getDate()));

        return transactionRepository.save(transaction);
    }
}
