package com.petracker.framework.dto;

import com.petracker.framework.models.PaymentMode;
import com.petracker.framework.models.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entry details returned from the API")
public class EntryGetDTO {
    @Schema(description = "Unique identifier of the entry", example = "42")
    private Long entryId;

    @Schema(description = "Transaction amount", example = "250.00")
    private Float amount;

    @Schema(description = "Optional remarks or notes", example = "Groceries from supermarket")
    private String remarks;

    @Schema(description = "Mode of payment", example = "CARD")
    private PaymentMode modeOfPayment;

    @Schema(description = "Type of transaction", example = "DEBIT")
    private TransactionType type;

    @Schema(description = "Unix timestamp when the entry was added", example = "1710489600000")
    private long added_Time;

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public Float getAmount() {
        return amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAdded_Time(long added_Time) {
        this.added_Time = added_Time;
    }

    public PaymentMode getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(PaymentMode modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public TransactionType getType() {
        return type;
    }

    public long getAdded_Time() {
        return added_Time;
    }

    @Override
    public String toString() {
        return "EntryGetDTO{" +
                "entryId=" + entryId +
                ", amount=" + amount +
                ", remarks='" + remarks + '\'' +
                ", modeOfPayment=" + modeOfPayment +
                ", type=" + type +
                ", added_Time=" + added_Time +
                '}';
    }
}
