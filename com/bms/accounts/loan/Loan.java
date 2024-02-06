package com.bms.accounts.loan;

import java.util.ArrayList;
import java.util.List;

public class Loan {
    public enum LoanType{SECURED,UNSECURED};
    private double loanAmount;
    private double interestRate;
    private LoanType loanType;
    private List<Collateral> collateralList;

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

    public List<Collateral> getCollateralList() {
        return collateralList;
    }

    public void addCollateralList(Collateral collateral) {
        if(this.collateralList==null){
            this.collateralList = new ArrayList<>();
        }
        this.collateralList.add(collateral);
    }
}
