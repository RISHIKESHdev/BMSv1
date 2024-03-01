package com.bms.transaction.cash;

import com.bms.transaction.PaymentMode;

import java.util.List;

public class Cash implements PaymentMode {
    private double transactionId;
    private List<Currency> currencyList;

    public double getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(double transactionId) {
        this.transactionId = transactionId;
    }

    public Cash(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void addCurrencyList(Currency currency) {
        this.currencyList.add(currency);
    }
}
