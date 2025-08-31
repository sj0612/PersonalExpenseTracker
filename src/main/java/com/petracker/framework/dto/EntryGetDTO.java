package com.petracker.framework.dto;

import com.petracker.framework.models.PaymentMode;
import com.petracker.framework.models.TransactionType;

public class EntryGetDTO {
    private Long entryId;
    private Float amount;
    private String remarks;

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

    private PaymentMode modeOfPayment;
    private TransactionType type;

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

    private long added_Time;
}
