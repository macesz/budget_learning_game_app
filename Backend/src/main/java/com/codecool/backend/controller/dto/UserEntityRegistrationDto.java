package com.codecool.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record UserEntityRegistrationDto(@JsonAlias("username") String name, String email, String password) {
}
