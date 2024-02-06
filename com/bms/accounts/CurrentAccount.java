package com.bms.accounts;

import java.time.LocalDateTime;

public class CurrentAccount extends Account{
private double overDraftLimit;

    public CurrentAccount(long accountNumber, double currentBalance, double availableBalance, double creditScore, LocalDateTime accountInceptionDate, double overDraftLimit) {
        super(accountNumber, currentBalance, availableBalance, creditScore, accountInceptionDate);
        this.overDraftLimit = overDraftLimit;
    }

    public double getOverDraftLimit() {
        return overDraftLimit;
    }

    public void setOverDraftLimit(double overDraftLimit) {
        this.overDraftLimit = overDraftLimit;
    }
}
