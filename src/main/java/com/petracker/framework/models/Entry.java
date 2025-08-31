package com.petracker.framework.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "entries")
@Data
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Long entryId;

    @Column(nullable = false)
    private Float amount;

    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMode modeOfPayment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private long added_time;

    @Column(nullable = false)
    private long updated_time;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public void setType(TransactionType type) {
        this.type = type;
    }

    public long getAdded_time() {
        return added_time;
    }

    public void setAdded_time(long added_time) {
        this.added_time = added_time;
    }

    public long getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(long updated_time) {
        this.updated_time = updated_time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Getters and setters

    @Override
    public String toString() {
        return "Entry{" +
                "entryId=" + entryId +
                ", amount=" + amount +
                ", remarks='" + remarks + '\'' +
                ", modeOfPayment=" + modeOfPayment +
                ", type=" + type +
                ", addedTime=" + added_time +
                ", updatedTime=" + updated_time +
                ", category=" + category +
                '}';
    }
}