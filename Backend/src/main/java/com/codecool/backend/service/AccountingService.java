package com.codecool.backend.service;

import com.codecool.backend.model.entity.Closer;
import com.codecool.backend.model.entity.Household;
import com.codecool.backend.model.entity.Transaction;
import com.codecool.backend.repository.CloserRepository;
import com.codecool.backend.repository.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AccountingService {

    private final TransactionService transactionService;
    private final CloserRepository closerRepository;
    private final HouseholdRepository householdRepository;



    @Autowired
    public AccountingService(TransactionService transactionService, CloserRepository closerRepository, HouseholdRepository householdRepository) {
        this.transactionService = transactionService;
        this.closerRepository = closerRepository;
        this.householdRepository = householdRepository;
    }


    public BigDecimal getBalance(Long householdId, LocalDate balanceDate) {
        Closer lastCloser = closerRepository.findFirstByHouseholdIdAndDateLessThanEqualOrderByDateDesc(
                householdId, balanceDate);

        if (lastCloser == null) {
            // No closer found, start from zero
            return BigDecimal.ZERO;
        }

        LocalDate lastCloserDate = lastCloser.getDate();

        List<Transaction> transactions = transactionService.getTransactionsBetweenDates(householdId, lastCloserDate, balanceDate );

        BigDecimal sum = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return lastCloser.getAmount().add(sum);
    }

    public Closer createCloser(Long householdId, LocalDate closerDate) {
        BigDecimal balance = getBalance(householdId, closerDate);

        Household household = householdRepository.findById(householdId)
                .orElseThrow(() -> new IllegalArgumentException("Household not found"));

        Closer closer = new Closer(household, closerDate, balance);
        return closerRepository.save(closer) ;
    }


}
