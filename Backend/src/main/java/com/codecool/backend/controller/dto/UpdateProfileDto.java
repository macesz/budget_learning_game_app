package com.codecool.backend.controller.dto;

import java.math.BigDecimal;

public record UpdateProfileDto(String currentPassword, String email, String newPassword, String username, BigDecimal newTargetAmount, String connectToUserEmail ) {
}
