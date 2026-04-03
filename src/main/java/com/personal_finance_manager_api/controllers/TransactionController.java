package com.personal_finance_manager_api.controllers;

import com.personal_finance_manager_api.dtos.requests.CreateTransactionRequestDTO;
import com.personal_finance_manager_api.dtos.requests.UpdateTransactionRequestDTO;
import com.personal_finance_manager_api.dtos.responses.TransactionResponseDTO;
import com.personal_finance_manager_api.exceptions.ModelNotFoundException;
import com.personal_finance_manager_api.models.Transaction;
import com.personal_finance_manager_api.services.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

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
    public ResponseEntity<ApiResponse<Object>> index() {
        return this.responder.success(this.service.getTransactions());
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
