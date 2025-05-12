package com.codecool.backend.controller;

import com.codecool.backend.controller.exception.UserEntityNotFoundException;

import com.codecool.backend.controller.exception.TransactionNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestControllerAdvice
public class PokeControllerAdvice {
    @ResponseBody
    @ExceptionHandler(UserEntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String memberNotFoundExceptionHandler(UserEntityNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String transactionNotFoundExceptionHandler(TransactionNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String DataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex) {
        return ex.getMessage();
    }
}
