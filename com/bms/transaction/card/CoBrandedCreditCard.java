package com.bms.transaction.card;

import java.time.LocalDate;

public class CoBrandedCreditCard extends CreditCard{
    private String merchantName;
    private double merchantOfferPercentage;

    public CoBrandedCreditCard(long cardNumber, String cardHolderName, LocalDate inceptionDate, LocalDate expiryDate, String paymentGateway, int CVV, int pinNumber, int interestFreeCreditDays, double rateOfInterest, String merchantName, double merchantOfferPercentage) {
        super(cardNumber, cardHolderName, inceptionDate, expiryDate, paymentGateway, CVV, pinNumber, interestFreeCreditDays, rateOfInterest);
        this.merchantName = merchantName;
        this.merchantOfferPercentage = merchantOfferPercentage;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public double getMerchantOfferPercentage() {
        return merchantOfferPercentage;
    }

    public void setMerchantOfferPercentage(double merchantOfferPercentage) {
        this.merchantOfferPercentage = merchantOfferPercentage;
    }
}
