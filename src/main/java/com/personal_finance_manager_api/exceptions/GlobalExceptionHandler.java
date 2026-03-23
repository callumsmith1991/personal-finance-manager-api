package com.personal_finance_manager_api.exceptions;

import com.personal_finance_manager_api.controllers.Responder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Responder responder;

    public GlobalExceptionHandler(Responder responder) {
        this.responder = responder;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return responder.badRequest(errorMessage);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public Object handleModelNotFoundException(ModelNotFoundException ex) {
        return responder.notFound(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Object handleRuntimeException(RuntimeException ex) {
        return responder.internalServerError(ex.getMessage());
    }
}
