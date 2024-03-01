package com.bms.transaction.card;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DebitCard extends Card{
    private double withdrawalLimit;

    public DebitCard(String cardName, LocalDateTime inceptionDate, boolean isActive, String paymentGateway,double withdrawalLimit) {
        super(cardName, inceptionDate, isActive, paymentGateway);
        this.withdrawalLimit = withdrawalLimit;
    }

    public double getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(double withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }
}
