package com.codecool.backend.controller.dto;

import com.codecool.backend.model.entity.UserEntity;
import com.codecool.backend.model.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public record UserEntityDto(int id, String name, String email, BigDecimal target, List<Transaction> transactions) {
    public UserEntityDto(UserEntity member) {
        this(member.getId(), member.getName(), member.getEmail(), member.getTargetAmount(), member.getTransactions());
    }
}
