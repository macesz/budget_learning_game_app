package com.codecool.backend.controller.exception;

public class HouseholdNotFoundException extends RuntimeException {
    public HouseholdNotFoundException(String message) {
        super(message);
    }
}
