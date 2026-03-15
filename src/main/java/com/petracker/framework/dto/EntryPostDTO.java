package com.petracker.framework.dto;

import com.petracker.framework.models.PaymentMode;
import com.petracker.framework.models.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload for creating a new expense/income entry")
public class EntryPostDTO {

    @Schema(description = "Transaction amount", example = "250.00")
    private Float amount;

    @Schema(description = "Optional remarks or notes", example = "Groceries from supermarket")
    private String remarks;

    @Schema(description = "Mode of payment", example = "CARD", allowableValues = {"CASH", "CARD", "UPI", "NET_BANKING"})
    private PaymentMode modeOfPayment;

    @Schema(description = "Category name for this entry", example = "Food")
    private String categoryName;

    @Schema(description = "Type of transaction", example = "DEBIT", allowableValues = {"CREDIT", "DEBIT"})
    private TransactionType type;

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
}
