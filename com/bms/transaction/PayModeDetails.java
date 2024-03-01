package com.bms.transaction;

public class PayModeDetails implements PaymentMode {
    private double cardNumber;

    public PayModeDetails(double cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getCardNumber() {
        return cardNumber;
    }
}