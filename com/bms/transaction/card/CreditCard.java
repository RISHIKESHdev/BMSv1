package com.bms.transaction.card;

import java.time.LocalDate;

public class CreditCard extends Card{
    private int interestFreeCreditDays;
    private double rateOfInterest;

    public CreditCard(long cardNumber, String cardHolderName, LocalDate inceptionDate, LocalDate expiryDate, String paymentGateway, int CVV, int pinNumber, int interestFreeCreditDays, double rateOfInterest) {
        super(cardNumber, cardHolderName, inceptionDate, expiryDate, paymentGateway, CVV, pinNumber);
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
