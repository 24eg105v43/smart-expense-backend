package com.smartexpense.dto;

import com.smartexpense.model.Category;

import java.math.BigDecimal;

public class CategoryExpenseDto {
    private Category category;
    private BigDecimal amount;
    private double percentage;

    public CategoryExpenseDto() {}

    public CategoryExpenseDto(Category category, BigDecimal amount, double percentage) {
        this.category = category;
        this.amount = amount;
        this.percentage = percentage;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
