package com.codecool.backend.controller;

import com.codecool.backend.controller.dto.CategoryDto;
import com.codecool.backend.controller.dto.NewTransactionDto;
import com.codecool.backend.controller.dto.TransactionDto;
import com.codecool.backend.model.entity.Category;
import com.codecool.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.OptionalDouble;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/categories/all")
    public List<CategoryDto> getAllCategories() {
        return transactionService.getAllCategories();
    }

//    @GetMapping("/all")
//    public List<TransactionDto> getAll() throws Exception {
//        return transactionService.getAllTransactions();
//    }

    @GetMapping("/all")
    public List<TransactionDto> getAllByUser(@RequestParam(required = false) LocalDate date) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return transactionService.getAllByUserEntity(user.getUsername(), date);
    }

    @GetMapping("/{id}")
    public TransactionDto getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/{category}")
    public List<TransactionDto> getTransactionByCategoryId(@PathVariable Category category) {
        return transactionService.getTransactionByCategory(category);
    }

    @GetMapping("/{categoryId}")
    public List<TransactionDto> getTransactionsByCategoryId(@PathVariable Long categoryId) {
        return transactionService.getTransactionsByCategoryId(categoryId);
    }

    @GetMapping("/{id}/avrg")
    public OptionalDouble getAverageTransactions(@PathVariable Long id) {
        return transactionService.getAvgSpendingByCategoryId(id);
    }

    @GetMapping("/{id}/sum")
    public int getSumOfTransactionByCategoryId(@PathVariable Long id) {
      return transactionService.getSumOfTransactionByCategoryId(id);
    }

    @PostMapping("/add")
    public Long addTransaction(@RequestBody NewTransactionDto transactionDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       return transactionService.createTransaction(user.getUsername(),transactionDto);
    }

    @PutMapping("")
    public boolean updateTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionService.updateTransaction(transactionDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTransaction( @PathVariable Long id) {
        return transactionService.deleteTransaction(id);
    }

}
