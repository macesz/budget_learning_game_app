package com.codecool.backend.controller.dto;

import com.codecool.backend.model.entity.Category;
import com.codecool.backend.model.entity.Transaction;

import java.time.LocalDate;

public record TransactionDto(Long id, String name, Category category, int amount, int memberId, LocalDate date) {
    public TransactionDto(Transaction transaction){
        this(transaction.getId(), transaction.getName(), transaction.getCategory(), transaction.getAmount(), transaction.getUserEntity().getId(), transaction.getDate());
    }
}
