package com.personal_finance_manager_api.services;

import com.personal_finance_manager_api.dtos.requests.CreateTransactionRequestDTO;
import com.personal_finance_manager_api.dtos.requests.UpdateTransactionRequestDTO;
import com.personal_finance_manager_api.dtos.responses.PagedTransactionResponseDTO;
import com.personal_finance_manager_api.dtos.responses.TransactionResponseDTO;
import com.personal_finance_manager_api.exceptions.ModelNotFoundException;
import com.personal_finance_manager_api.helpers.Helpers;
import com.personal_finance_manager_api.models.Transaction;
import com.personal_finance_manager_api.models.User;
import com.personal_finance_manager_api.notifications.OverBudgetNotification;
import com.personal_finance_manager_api.repositories.TransactionRepository;
import com.personal_finance_manager_api.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable; // ✅
import java.time.LocalDate;

@Component
public class TransactionService {

    private final AuthService authService;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TransactionTypeService transactionTypeService;
    private final OverBudgetNotification overBudgetNotification;
    private final Helpers helpers;

    public TransactionService(
            AuthService authService,
            TransactionRepository transactionRepository,
            UserRepository userRepository,
            TransactionTypeService transactionTypeService,
            OverBudgetNotification overBudgetNotification,
            Helpers helpers
    ) {
        this.authService = authService;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.transactionTypeService = transactionTypeService;
        this.overBudgetNotification = overBudgetNotification;
        this.helpers = helpers;
    }

    public PagedTransactionResponseDTO getTransactions(Pageable pageable)
    {
        Long userId = authService.getAuthenticatedUserId();
        Page<Transaction> transactions = transactionRepository.findAllByUserId(userId, pageable);
        return new PagedTransactionResponseDTO(
                transactions.getNumber(),
                transactions.getTotalPages(),
                transactions.getTotalElements(),
                transactions.getContent().stream().map(this::mapToDTO).toList()
        );
    }

    public TransactionResponseDTO createTransaction(CreateTransactionRequestDTO request) throws RuntimeException, ModelNotFoundException {
        User user = getAuthenticatedUser();
        Transaction transaction = new Transaction();
        LocalDate formattedDate = helpers.formatDate(request.getDate());
        int month = formattedDate.getMonthValue();

        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setCategory(request.getCategory());
        transaction.setUser(user);
        transaction.setType(transactionTypeService.getTransactionType(request.getType()));
        transaction.setDate(formattedDate);

        Transaction saved = transactionRepository.save(transaction);

        overBudgetNotification.sendOverBudgetNotification(month, user);

        return this.mapToDTO(saved);
    }

    public TransactionResponseDTO getTransactionById(Integer id) throws ModelNotFoundException {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Transaction not found"));
        return this.mapToDTO(transaction);
    }

    public TransactionResponseDTO updateTransaction(Integer id, UpdateTransactionRequestDTO request) throws ModelNotFoundException {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Transaction not found"));


        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setCategory(request.getCategory());
        transaction.setType(transactionTypeService.getTransactionType(request.getType()));
        transaction.setDate(helpers.formatDate(request.getDate()));

        return this.mapToDTO(transactionRepository.save(transaction));
    }

    public void deleteTransaction(Integer id) throws ModelNotFoundException {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Transaction not found"));


        transactionRepository.delete(transaction);
    }

    private User getAuthenticatedUser() throws RuntimeException {
        Integer userId = authService.getAuthenticatedUserId().intValue();
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    private TransactionResponseDTO mapToDTO(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount().toString(),
                transaction.getCategory(),
                transaction.getDate().toString(),
                transaction.getType().getName()
        );
    }
}
