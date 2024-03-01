package com.bms.accounts;

import java.time.LocalDateTime;

public class SavingAccount extends Account {
    private double minimumAccountBalance;
    private double withdrawalLimit;
    private double rateOfInterest;

    public SavingAccount(double currentBalance, double availableBalance, double creditScore, LocalDateTime accountInceptionDate, double minimumAccountBalance, double withdrawalLimit, double rateOfInterest) {
        super(currentBalance, availableBalance, creditScore, accountInceptionDate);
        this.minimumAccountBalance = minimumAccountBalance;
        this.withdrawalLimit = withdrawalLimit;
        this.rateOfInterest = rateOfInterest;
    }

    public double getMinimumAccountBalance() {
        return minimumAccountBalance;
    }

    public void setMinimumAccountBalance(double minimumAccountBalance) {
        this.minimumAccountBalance = minimumAccountBalance;
    }

    public double getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(double withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }

    public double getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(double rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }
}
