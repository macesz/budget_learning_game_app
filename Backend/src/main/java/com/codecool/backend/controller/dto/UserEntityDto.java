package com.codecool.backend.controller.dto;

import com.codecool.backend.model.entity.UserEntity;
import com.codecool.backend.model.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record UserEntityDto(int id, String name, String email, BigDecimal target, Set<Transaction> transactions) {
    public UserEntityDto(UserEntity userEntity) {
        this(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), userEntity.getTargetAmount(), userEntity.getTransactions());
    }
}
