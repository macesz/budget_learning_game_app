package com.codecool.backend.service;

import com.codecool.backend.controller.dto.CategoryDto;
import com.codecool.backend.controller.dto.NewTransactionDto;
import com.codecool.backend.controller.dto.TransactionDto;
import com.codecool.backend.controller.exception.CategoryNotFoundException;
import com.codecool.backend.controller.exception.UserEntityNotFoundException;
import com.codecool.backend.controller.exception.TransactionNotFoundException;
import com.codecool.backend.model.entity.Category;
import com.codecool.backend.model.entity.UserEntity;
import com.codecool.backend.model.entity.Transaction;
import com.codecool.backend.repository.CategoryRepository;
import com.codecool.backend.repository.UserEntityRepository;
import com.codecool.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserEntityRepository userRepository;
    private final UserEntityService userEntityService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserEntityRepository userRepository, UserEntityService userEntityService, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.userEntityService = userEntityService;
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::new)
                .toList();
    }

    public List<TransactionDto> getAllTransactions() {
        List<Transaction> allTransactions = transactionRepository.findAll();
        List<TransactionDto> transactionDtos = new ArrayList<>();
        allTransactions.forEach(transaction -> transactionDtos.add(new TransactionDto(transaction)));
        return transactionDtos;
    }

    public List<TransactionDto> getAllByUserEntity(String email, LocalDate startDate) {

        UserEntity userEntity = userRepository.findUserEntityByEmail(email)
                .orElseThrow(UserEntityNotFoundException::new);
        List<Transaction> transactions = new ArrayList<>();
        if(startDate == null){
            transactions = transactionRepository.getAllByUserEntity(userEntity)
                    .orElseThrow(TransactionNotFoundException::new);
        } else {
            transactions = transactionRepository.getAllByUserEntityAndDateAfter(userEntity, startDate)
                .orElseThrow(TransactionNotFoundException::new);
        }
        return transactions.stream()
                .map(TransactionDto::new)
                .toList();
    }

    public Long createTransaction(String email,NewTransactionDto transactionDto) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email)
                .orElseThrow(UserEntityNotFoundException::new);

        Category category = categoryRepository.getCategoryById(transactionDto.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        LocalDate date = LocalDate.now();
        Transaction transaction = new Transaction(transactionDto);
        transaction.setUserEntity(userEntity);
        transaction.setCategory(category);
        transaction.setDate(date);
        return transactionRepository.save(transaction).getId();
    }

    public List<TransactionDto> getTransactionByGategory(Category category) {

        List<Transaction> transactions = new ArrayList<>();

        transactions = transactionRepository.getTransactionsByCategory(category)
                    .orElseThrow(TransactionNotFoundException::new);

        return transactions.stream()
                .map(TransactionDto::new)
                .toList();
    }

    public TransactionDto getTransactionById(int id) {
        return transactionRepository.getTransactionById(id)
                .map(transaction -> new TransactionDto(transaction.getId(), transaction.getName(), transaction.getCategory(), transaction.getAmount(), transaction.getUserEntity().getId(), transaction.getDate()
                )).orElseThrow(NoSuchElementException::new);
    }

    public boolean updateTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction(transactionDto);
        transactionRepository.save(transaction);
        return true;
    }

    public boolean deleteTransaction(int id) {
        return transactionRepository.deleteTransactionById(id);
    }

    public int getSumOfTransactionByCategoryId(int categoryId) {
        List<Transaction> transactions = transactionRepository.getAllByCategoryId(categoryId)
                .orElseThrow(TransactionNotFoundException::new);
        return transactions.stream()
                .mapToInt(Transaction::getAmount)
                .sum();
    }

    public OptionalDouble getAvgSpendingByCategoryId(int categoryId) {
        List<Transaction> transactions = transactionRepository.getAllByCategoryId(categoryId)
                .orElseThrow(TransactionNotFoundException::new);
        return transactions.stream()
                .mapToInt(Transaction::getAmount)
                .average();
    }

}