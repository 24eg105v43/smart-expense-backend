package com.smartexpense.dto;

import java.math.BigDecimal;
import java.util.List;

public class MonthlyAnalysisDto {
    private String month;
    private int year;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpenses;
    private BigDecimal monthlyBalance;
    private double savingsRate;
    private List<CategoryExpenseDto> categoryExpenses;

    public MonthlyAnalysisDto() {}

    public MonthlyAnalysisDto(String month, int year, BigDecimal monthlyIncome, BigDecimal monthlyExpenses, BigDecimal monthlyBalance, double savingsRate, List<CategoryExpenseDto> categoryExpenses) {
        this.month = month;
        this.year = year;
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpenses = monthlyExpenses;
        this.monthlyBalance = monthlyBalance;
        this.savingsRate = savingsRate;
        this.categoryExpenses = categoryExpenses;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public BigDecimal getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(BigDecimal monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }

    public BigDecimal getMonthlyBalance() {
        return monthlyBalance;
    }

    public void setMonthlyBalance(BigDecimal monthlyBalance) {
        this.monthlyBalance = monthlyBalance;
    }

    public double getSavingsRate() {
        return savingsRate;
    }

    public void setSavingsRate(double savingsRate) {
        this.savingsRate = savingsRate;
    }

    public List<CategoryExpenseDto> getCategoryExpenses() {
        return categoryExpenses;
    }

    public void setCategoryExpenses(List<CategoryExpenseDto> categoryExpenses) {
        this.categoryExpenses = categoryExpenses;
    }
}
