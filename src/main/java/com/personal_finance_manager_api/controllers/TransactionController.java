package com.personal_finance_manager_api.controllers;

import com.personal_finance_manager_api.dtos.requests.CreateTransactionRequestDTO;
import com.personal_finance_manager_api.dtos.requests.UpdateTransactionRequestDTO;
import com.personal_finance_manager_api.models.Transaction;
import com.personal_finance_manager_api.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
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
    public ResponseEntity<ApiResponse<Object>> create(@RequestBody @Valid CreateTransactionRequestDTO request) throws ParseException {
        Transaction transaction = this.service.createTransaction(request);
        return this.responder.created(transaction);
    }

    @GetMapping("/api/transactions/{id}")
    public ResponseEntity<ApiResponse<Object>> show(@PathVariable Integer id) {
        return this.responder.success(this.service.getTransactionById(id));
    }

    @PatchMapping("/api/transactions/{id}")
    public ResponseEntity<ApiResponse<Object>> update(@PathVariable Integer id, @RequestBody @Valid UpdateTransactionRequestDTO request) throws ParseException {

    }


}
