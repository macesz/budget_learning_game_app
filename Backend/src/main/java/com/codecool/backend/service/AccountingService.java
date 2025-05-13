package com.codecool.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class AccountingService {

    private final TransactionService transactionService;


    @Autowired
    public AccountingService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public BigDecimal getTotalIncome(Long householdId, LocalDate startDate, LocalDate endDate) {

        //TODO

        return new BigDecimal(0);

    }

    public BigDecimal getTotalExpense(Long householdId, LocalDate startDate, LocalDate endDate) {

        return new BigDecimal(0);
        //TODO
    }

    public BigDecimal getBalance(Long householdId, LocalDate balanceDate) {
        return new BigDecimal(0);

        // find the last clouser before the ibalanceDate
        //get the sum of total income between the closer date and the balanceDate
        //get the sum of total expense between the closer date and the balanceDate
        //sum income and expense, and clouser
    }

    public BigDecimal createCloser(Long householdId, LocalDate closerDate) {
        // find the last clouser before the closerDate
        // save to closer repository

        return new BigDecimal(0);
    }




}
