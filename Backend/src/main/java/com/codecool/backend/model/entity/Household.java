package com.codecool.backend.model.entity;
import com.codecool.backend.controller.dto.NewTransactionDto;
import com.codecool.backend.controller.dto.TransactionDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Household {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "house")
    private Set<UserEntity> users;

    @OneToMany(mappedBy = "house")
    private Set<Category> categories;

    @OneToMany(mappedBy = "house")
    private Set<Transaction> transactions;

    public Household() {};

    public Household(Long id) {
        this.id = id;
    }
}
