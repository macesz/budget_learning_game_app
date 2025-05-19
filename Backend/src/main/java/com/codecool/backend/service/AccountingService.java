package com.codecool.backend.service;

import com.codecool.backend.model.entity.Closer;
import com.codecool.backend.model.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AccountingService {

    private final TransactionService transactionService;
    private final ICloserService closerSrevice;


    @Autowired
    public AccountingService(TransactionService transactionService, ICloserService closerSrevice) {
        this.transactionService = transactionService;
        this.closerSrevice = closerSrevice;
    }


    public BigDecimal getBalance(Long householdId, LocalDate balanceDate) {
        Closer lastCloser = closerSrevice.getLastCloser(householdId, balanceDate);
        LocalDate lastCloserDate = lastCloser.getDate();

        List<Transaction> transactions = transactionService.getTransactions(householdId, lastCloserDate, balanceDate );

        BigDecimal sum = transactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        return lastCloser.getAmount().add(sum);
    }

    public Closer createCloser(Long householdId, LocalDate closerDate) {
        BigDecimal balance = getBalance(householdId, closerDate);

        // save to repository
        return new Closer(balance, closerDate);
    }




}
