package com.bms.transaction.card;

import java.time.LocalDate;

public class DebitCard extends Card{
    private double withdrawalLimit;

    public DebitCard(long cardNumber, String cardHolderName, LocalDate inceptionDate, LocalDate expiryDate, String paymentGateway, int CVV, int pinNumber, double withdrawalLimit) {
        super(cardNumber, cardHolderName, inceptionDate, expiryDate, paymentGateway, CVV, pinNumber);
        this.withdrawalLimit = withdrawalLimit;
    }

    public double getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(double withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }
}
