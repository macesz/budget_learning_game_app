package com.codecool.backend.service;

import com.codecool.backend.model.entity.Closer;
import com.codecool.backend.model.entity.Household;
import com.codecool.backend.model.entity.Transaction;
import com.codecool.backend.repository.CloserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AccountingService {

    private final TransactionService transactionService;
    private final CloserRepository closerRepository;


    @Autowired
    public AccountingService(TransactionService transactionService, CloserRepository closerRepository) {
        this.transactionService = transactionService;
        this.closerRepository = closerRepository;
    }


    public BigDecimal getBalance(Long householdId, LocalDate balanceDate) {
        Closer lastCloser = closerRepository.getCloserByDateAndId(householdId, balanceDate);
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
