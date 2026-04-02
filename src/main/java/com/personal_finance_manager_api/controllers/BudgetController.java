package com.personal_finance_manager_api.controllers;

import com.personal_finance_manager_api.dtos.requests.CreateBudgetRequestDTO;
import com.personal_finance_manager_api.dtos.requests.UpdateBudgetRequestDTO;
import com.personal_finance_manager_api.exceptions.ModelNotFoundException;
import com.personal_finance_manager_api.services.BudgetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BudgetController {

    private final Responder responder;
    private final BudgetService service;

    public BudgetController(Responder responder, BudgetService service) {
        this.responder = responder;
        this.service = service;
    }

    @GetMapping("/api/budgets")
    public ResponseEntity<ApiResponse<Object>> index() {
        return this.responder.success(this.service.getBudgets());
    }

    @PostMapping("/api/budgets")
    public ResponseEntity<ApiResponse<Object>> create(@Valid @RequestBody CreateBudgetRequestDTO request) throws ModelNotFoundException {
        return this.responder.created(this.service.createBudget(request));
    }

    @PatchMapping("/api/budgets/{id}")
    public ResponseEntity<ApiResponse<Object>> update(@PathVariable Integer id, @Valid @RequestBody UpdateBudgetRequestDTO request) throws ModelNotFoundException {
        return this.responder.success(this.service.updateBudget(id, request));
    }

    @DeleteMapping("/api/budgets/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Integer id) throws ModelNotFoundException {
        this.service.deleteBudget(id);
        return this.responder.success("Budget deleted successfully");
    }
}
