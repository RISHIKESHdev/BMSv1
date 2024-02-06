package com.bms.transaction;

import java.time.LocalDateTime;

public abstract class Transaction {
    private String transactionId;
    private LocalDateTime transactionDateTime;
    private double transactionAmount;
    PaymentMode payModeDetail;

    public Transaction(String transactionId, LocalDateTime transactionDateTime, double transactionAmount, PaymentMode payModeDetail) {
        this.transactionId = transactionId;
        this.transactionDateTime = transactionDateTime;
        this.transactionAmount = transactionAmount;
        this.payModeDetail = payModeDetail;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public PaymentMode getPayModeDetail() {
        return payModeDetail;
    }

    public void setPayModeDetail(PaymentMode payModeDetail) {
        this.payModeDetail = payModeDetail;
    }
}
