package com.bms.transaction.cash;

public class Currency {
    private double INRValue;
    private int count;

    public Currency(double INRValue, int count) {
        this.INRValue = INRValue;
        this.count = count;
    }

    public double getINRValue() {
        return INRValue;
    }

    public void setINRValue(double INRValue) {
        this.INRValue = INRValue;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
