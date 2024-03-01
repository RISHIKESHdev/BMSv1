package com.bms.accounts.loan;

import java.util.ArrayList;
import java.util.List;

public abstract class Loan {
    public enum LoanType{SECURED,UNSECURED};
    private double loanAmount;
    private double loanId;
    private double accountNumber;
    private double interestRate;
    private LoanType loanType;

    public Loan(double loanAmount, double interestRate , LoanType loanType) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanType = loanType;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public double getLoanId() {
        return loanId;
    }

    public void setLoanId(double loanId) {
        this.loanId = loanId;
    }

    public double getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(double accountNumber) {
        this.accountNumber = accountNumber;
    }
}
