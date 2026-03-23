package com.personal_finance_manager_api.controllers;

public class ApiResponse<T> {

    private final T data;
    private final String error;
    private final int statusCode;

    public ApiResponse(T data, String error, int statusCode) {
        this.data = data;
        this.error = error;
        this.statusCode = statusCode;
    }

    public static <Object> ApiResponse<Object> success(Object data, int statusCode) {
        return new ApiResponse<>(data, null, statusCode);
    }

    public static <Object> ApiResponse<Object> error(String error, int statusCode) {
        return new ApiResponse<>(null, error, statusCode);
    }

    public T getData() { return data; }
    public String getError() { return error; }
    public int getStatusCode() { return statusCode; }
}