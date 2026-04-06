package com.personal_finance_manager_api.controllers;

import com.personal_finance_manager_api.dtos.requests.CreateTransactionRequestDTO;
import com.personal_finance_manager_api.dtos.requests.UpdateTransactionRequestDTO;
import com.personal_finance_manager_api.dtos.responses.TransactionResponseDTO;
import com.personal_finance_manager_api.exceptions.ModelNotFoundException;
import com.personal_finance_manager_api.models.Transaction;
import com.personal_finance_manager_api.services.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable; // ✅import java.text.ParseException;

import java.text.ParseException;
import java.util.Map;
import java.util.Set;

@RestController
@Validated
public class TransactionController {

    private final TransactionService service;
    private final Responder responder;

    public TransactionController(TransactionService service, Responder responder) {
        this.service = service;
        this.responder = responder;
    }

    @GetMapping("/api/transactions")
    public ResponseEntity<ApiResponse<Object>> index(
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page number must be zero or a positive integer") Integer page,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        // Validate sortBy against allowed fields to prevent PropertyReferenceException
        Set<String> allowedSortFields = Set.of("date", "amount", "id");
        if (!allowedSortFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sortBy field: " + sortBy);
        }

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, 10, sort);
        return this.responder.success(this.service.getTransactions(pageable));
    }

    @PostMapping("/api/transactions")
    public ResponseEntity<ApiResponse<Object>> create(@RequestBody @Valid CreateTransactionRequestDTO request) throws ParseException, ModelNotFoundException {
        TransactionResponseDTO transaction = this.service.createTransaction(request);
        return this.responder.created(transaction);
    }

    @GetMapping("/api/transactions/{id}")
    public ResponseEntity<ApiResponse<Object>> show(@PathVariable @Positive(message = "ID must be a positive number") Integer id) throws ModelNotFoundException {
        return this.responder.success(this.service.getTransactionById(id));
    }

    @PatchMapping("/api/transactions/{id}")
    public ResponseEntity<ApiResponse<Object>> update(@PathVariable @Positive(message = "ID must be a positive number") Integer id, @RequestBody @Valid UpdateTransactionRequestDTO request) throws ParseException, ModelNotFoundException {
        return this.responder.success(this.service.updateTransaction(id, request));
    }

    @DeleteMapping("/api/transactions/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable @Positive(message = "ID must be a positive number") Integer id) throws ModelNotFoundException {
        this.service.deleteTransaction(id);
        return this.responder.success(Map.of("message", "Transaction deleted successfully"));
    }
}
