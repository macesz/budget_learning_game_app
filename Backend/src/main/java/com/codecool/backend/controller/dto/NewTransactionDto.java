package com.codecool.backend.controller.dto;

import java.util.List;

public record NewTransactionDto(String name, List<Long> categoryIds, int amount) {
}
