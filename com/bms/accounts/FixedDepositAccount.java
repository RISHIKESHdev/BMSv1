package com.bms.accounts;

import java.time.LocalDateTime;

public class FixedDepositAccount extends Account{
    private int tenure;
    private LocalDateTime matureDateTime;
    private double rateOfInterest;

    public FixedDepositAccount(long accountNumber, double currentBalance, double availableBalance, double creditScore, LocalDateTime accountInceptionDate, int tenure, LocalDateTime matureDateTime, double rateOfInterest) {
        super(accountNumber, currentBalance, availableBalance, creditScore, accountInceptionDate);
        this.tenure = tenure;
        this.matureDateTime = matureDateTime;
        this.rateOfInterest = rateOfInterest;
    }

    public int getTenure() {
        return tenure;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }

    public LocalDateTime getMatureDateTime() {
        return matureDateTime;
    }

    public void setMatureDateTime(LocalDateTime matureDateTime) {
        this.matureDateTime = matureDateTime;
    }

    public double getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(double rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }
}
