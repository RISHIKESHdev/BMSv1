package com.bms.transaction.card;

import com.bms.transaction.PaymentMode;

import java.time.LocalDateTime;

public abstract class Card implements PaymentMode {
    private double cardNumber;
    private String cardHolderName;
    private String cardName;
    private LocalDateTime inceptionDate;
    private LocalDateTime validFromDate;
    private LocalDateTime expiryDate;
    private boolean isActive;
    private String paymentGateway;
    private int CVV;
    private int pinNumber;
    public Card(double cardNumber){
        this.cardNumber=cardNumber;
    }

    public Card(String cardName, LocalDateTime inceptionDate, boolean isActive, String paymentGateway) {
        this.cardName=cardName;
        this.inceptionDate = inceptionDate;
        this.isActive = isActive;
        this.paymentGateway = paymentGateway;
    }
    public Card(double cardNumber,LocalDateTime validFromDate,LocalDateTime expiryDate,String paymentGateway){
        this.cardNumber=cardNumber;
        this.validFromDate=validFromDate;
        this.expiryDate=expiryDate;
        this.paymentGateway=paymentGateway;
    }

    public double getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(double cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public LocalDateTime getInceptionDate() {
        return inceptionDate;
    }

    public void setInceptionDate(LocalDateTime inceptionDate) {
        this.inceptionDate = inceptionDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
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

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public LocalDateTime getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(LocalDateTime validFromDate) {
        this.validFromDate = validFromDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
