package com.codecool.backend.controller.dto;

import com.codecool.backend.model.entity.Category;
import com.codecool.backend.model.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public record TransactionDto(
        Long id,
        String name,
        Set<CategoryDto> categories, // Changed from a single category to a set
        BigDecimal amount,
        int userId,  // Changed from memberId to userId
        Long houseId,
        LocalDate date) {

    public TransactionDto(Transaction transaction) {
        this(
                transaction.getId(),
                transaction.getName(),
                transaction.getCategories().stream()
                        .map(CategoryDto::new)
                        .collect(Collectors.toSet()),
                transaction.getAmount(),
                transaction.getUserEntity().getId(), // Changed from getMember to getUserEntity
                transaction.getHousehold() != null ? transaction.getHousehold().getId() : null,
                transaction.getDate()
        );
    }


}
