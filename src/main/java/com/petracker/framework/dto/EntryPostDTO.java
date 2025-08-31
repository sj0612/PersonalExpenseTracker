package com.petracker.framework.dto;

import com.petracker.framework.models.Category;
import com.petracker.framework.models.PaymentMode;
import com.petracker.framework.models.TransactionType;

public class EntryPostDTO {
    private Float amount;
    private String remarks;
    private PaymentMode modeOfPayment;
    private String categoryName;

    public String getCategory() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setModeOfPayment(PaymentMode modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EntryPostDTO{" +
                "amount=" + amount +
                ", remarks='" + remarks + '\'' +
                ", modeOfPayment=" + modeOfPayment +
                ", type=" + type +
                '}';
    }

    public Float getAmount() {
        return amount;
    }

    public String getRemarks() {
        return remarks;
    }


    public PaymentMode getModeOfPayment() {
        return modeOfPayment;
    }

    public TransactionType getType() {
        return type;
    }

    private TransactionType type;
}
