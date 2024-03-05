package com.bms.transaction;

import java.time.LocalDateTime;

public abstract class Transaction {
    private int transactionId;
    private LocalDateTime transactionDateTime;
    private double transactionAmount;
    private String payMode;
    private PaymentMode payModeDetail;

    public Transaction(LocalDateTime transactionDateTime, double transactionAmount) {
        this.transactionDateTime = transactionDateTime;
        this.transactionAmount = transactionAmount;
    }
    public Transaction(LocalDateTime transactionDateTime, double transactionAmount, PaymentMode payModeDetail) {
        this.transactionDateTime = transactionDateTime;
        this.transactionAmount = transactionAmount;
        this.payModeDetail = payModeDetail;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
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
