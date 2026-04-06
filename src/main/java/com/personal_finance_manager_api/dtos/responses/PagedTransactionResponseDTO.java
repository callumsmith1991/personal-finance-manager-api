package com.personal_finance_manager_api.dtos.responses;

public class PagedTransactionResponseDTO {
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private Object transactions;

    public PagedTransactionResponseDTO(int currentPage, int totalPages, long totalItems, Object transactions) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.transactions = transactions;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public Object getTransactions() {
        return transactions;
    }

    public void setTransactions(Object transactions) {
        this.transactions = transactions;
    }
}
