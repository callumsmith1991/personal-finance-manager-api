package com.personal_finance_manager_api.controllers;

import com.personal_finance_manager_api.controllers.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Responder {

    public ResponseEntity<ApiResponse<Object>> success(Object data) {
        return ResponseEntity.ok(ApiResponse.success(data, 200));
    }

    public ResponseEntity<ApiResponse<Object>> created(Object data) {
        return ResponseEntity.status(201)
                .body(ApiResponse.success(data, 201));
    }

    public ResponseEntity<ApiResponse<Object>> badRequest(String message) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(message, 400));
    }

    public ResponseEntity<ApiResponse<Object>> unauthorized(String message) {
        return ResponseEntity.status(401)
                .body(ApiResponse.error(message, 401));
    }

    public ResponseEntity<ApiResponse<Object>> notFound(String message) {
        return ResponseEntity.status(404)
                .body(ApiResponse.error(message, 404));
    }

    public Object internalServerError(String message) {
        return ResponseEntity.status(500)
                .body(ApiResponse.error(message, 500));
    }
}