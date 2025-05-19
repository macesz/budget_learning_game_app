package com.codecool.backend.controller.dto;

import java.math.BigDecimal;

public record UserEntityProfileDto(Long id, String username, String email, BigDecimal targetAmount) {
}
