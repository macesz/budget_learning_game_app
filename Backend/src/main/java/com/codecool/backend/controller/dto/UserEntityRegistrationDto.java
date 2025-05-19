package com.codecool.backend.controller.dto;

import com.codecool.backend.model.entity.Household;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserEntityRegistrationDto(@NotBlank(message = "Name is required")
                                        @JsonAlias("username")
                                        String name,

                                        @NotBlank(message = "Email is required")
                                        @Email(message = "Email should be valid")
                                        String email,

                                        @NotBlank(message = "Password is required")
                                        @Size(min = 6, message = "Password must be at least 6 characters")
                                        String password
) {}
