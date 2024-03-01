package com.bms.transaction.card;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreditCard extends Card{
    private int interestFreeCreditDays;
    private double rateOfInterest;

    public CreditCard(String cardName, LocalDateTime inceptionDate, boolean isActive, String paymentGateway, int interestFreeCreditDays, double rateOfInterest) {
        super(cardName, inceptionDate, isActive, paymentGateway);
        this.interestFreeCreditDays = interestFreeCreditDays;
        this.rateOfInterest = rateOfInterest;
    }

    public int getInterestFreeCreditDays() {
        return interestFreeCreditDays;
    }

    public void setInterestFreeCreditDays(int interestFreeCreditDays) {
        this.interestFreeCreditDays = interestFreeCreditDays;
    }

    public double getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(double rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }
}
