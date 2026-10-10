package com.smartexpense.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardSummaryDto {
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal currentBalance;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpenses;
    private BigDecimal monthlyBalance;
    private List<CategoryExpenseDto> categoryExpenses;
    private List<TransactionDto> recentTransactions;

    public DashboardSummaryDto() {}

    public DashboardSummaryDto(BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal currentBalance, BigDecimal monthlyIncome, BigDecimal monthlyExpenses, BigDecimal monthlyBalance, List<CategoryExpenseDto> categoryExpenses, List<TransactionDto> recentTransactions) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.currentBalance = currentBalance;
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpenses = monthlyExpenses;
        this.monthlyBalance = monthlyBalance;
        this.categoryExpenses = categoryExpenses;
        this.recentTransactions = recentTransactions;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
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

    public List<CategoryExpenseDto> getCategoryExpenses() {
        return categoryExpenses;
    }

    public void setCategoryExpenses(List<CategoryExpenseDto> categoryExpenses) {
        this.categoryExpenses = categoryExpenses;
    }

    public List<TransactionDto> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<TransactionDto> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }
}
