package com.codecool.backend.service;

import com.codecool.backend.controller.dto.CategoryDto;
import com.codecool.backend.controller.dto.NewTransactionDto;
import com.codecool.backend.controller.dto.TransactionDto;
import com.codecool.backend.model.entity.Category;
import com.codecool.backend.model.entity.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public interface ITransactionService {

    List<CategoryDto> getAllCategories();

    List<TransactionDto> getAllTransactions();

    TransactionDto getTransactionById(Long id);

    TransactionDto createTransaction(TransactionDto transactionDto);

    TransactionDto updateTransaction(TransactionDto transactionDto);

    void deleteTransaction(Long id);

    List<TransactionDto> getAllByUserEntity(String email, LocalDate startDate);

    Long createTransaction(String email, NewTransactionDto transactionDto);

    List<TransactionDto> getTransactionsByCategories(Collection<Category> categories);

    List<TransactionDto> getTransactionByCategory(Category category);

    List<TransactionDto> getTransactionsByCategoryId(Long categoryId);

    BigDecimal getSumOfTransactionByCategoryId(Long categoryId);

    List<Transaction> getTransactions(Long householdId, LocalDate lastCloserDate, LocalDate balanceDate);


}