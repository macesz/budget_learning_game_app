package com.codecool.backend.model.entity;
import com.codecool.backend.controller.dto.NewTransactionDto;
import com.codecool.backend.controller.dto.TransactionDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Clouser {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "house_id")
    private Household household;

    private LocalDate date;

    private BigDecimal amount;

    public Clouser() {}

    public Clouser(Household household, LocalDate date, BigDecimal amount) {
        this.household = household;
        this.date = date;
        this.amount = amount;
    }


}
