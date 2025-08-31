package com.petracker.framework.dto;

public class CategoryAmountDTO {
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public CategoryAmountDTO(String categoryName, double amount) {
        this.categoryName = categoryName;
        this.amount = amount;
    }

    private String categoryName;
    private double amount;
}
