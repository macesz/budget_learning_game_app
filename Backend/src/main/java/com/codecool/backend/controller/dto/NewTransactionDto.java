package com.codecool.backend.controller.dto;

public record NewTransactionDto(String name, Long categoryId, int amount) {
}
