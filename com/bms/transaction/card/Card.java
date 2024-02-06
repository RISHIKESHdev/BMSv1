package com.bms.transaction.card;

import com.bms.transaction.PaymentMode;

import java.time.LocalDate;

public abstract class Card implements PaymentMode {
    private long cardNumber;
    private String cardHolderName;
    private LocalDate inceptionDate;
    private LocalDate expiryDate;
    private String paymentGateway;
    private int CVV;
    private int pinNumber;

    public Card(long cardNumber, String cardHolderName, LocalDate inceptionDate, LocalDate expiryDate, String paymentGateway, int CVV, int pinNumber) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.inceptionDate = inceptionDate;
        this.expiryDate = expiryDate;
        this.paymentGateway = paymentGateway;
        this.CVV = CVV;
        this.pinNumber = pinNumber;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public LocalDate getInceptionDate() {
        return inceptionDate;
    }

    public void setInceptionDate(LocalDate inceptionDate) {
        this.inceptionDate = inceptionDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public int getCVV() {
        return CVV;
    }

    public void setCVV(int CVV) {
        this.CVV = CVV;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }
}
