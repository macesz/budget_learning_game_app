package com.codecool.backend.controller.dto.reports;

public record MonthlyIncomeExpenseReportDTO(
        String month,
        double totalIncome,
        double totalExpense) {
}
