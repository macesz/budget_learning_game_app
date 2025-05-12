package com.codecool.backend.model.entity;

import com.codecool.backend.controller.dto.NewTransactionDto;
import com.codecool.backend.controller.dto.TransactionDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Data
@Entity
public class Transaction {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private int amount;

    @ManyToOne ///  many to many
    @JoinColumn(referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_member_transaction"), nullable = false)
    private UserEntity userEntity;

    // Add date for transaction
    private java.time.LocalDate date;

    public Transaction() {
    }

    public Transaction(Long id, String name, Category category, int amount, UserEntity member) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.userEntity = member;
    }

    public Transaction(TransactionDto dto) {
        id = dto.id();
        name = dto.name();
    }

    public Transaction(NewTransactionDto dto) {
        name = dto.name();
        amount = dto.amount();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id) && amount == that.amount &&
                Objects.equals(name, that.name) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, amount);
    }
}
