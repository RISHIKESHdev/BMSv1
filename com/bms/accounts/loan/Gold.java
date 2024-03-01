package com.bms.accounts.loan;

public class Gold extends Loan implements Collateral{
    private String goldPurity;
    private double goldValuePerGram;
    private double weightInGram;
    public Gold(double loanAmount, double interestRate , LoanType loanType,String goldPurity,double goldValuePerGram,double weightInGram){
        super( loanAmount,  interestRate ,  loanType);
        this.goldPurity=goldPurity;
        this.goldValuePerGram=goldValuePerGram;
        this.weightInGram=weightInGram;
    }

    public double getWeightInGram() {
        return weightInGram;
    }

    public void setWeightInGram(double weightInGram) {
        this.weightInGram = weightInGram;
    }

    public String getGoldPurity() {
        return goldPurity;
    }

    public void setGoldPurity(String goldPurity) {
        this.goldPurity = goldPurity;
    }

    public double getGoldValuePerGram() {
        return goldValuePerGram;
    }

    public void setGoldValuePerGram(double goldValuePerGram) {
        this.goldValuePerGram = goldValuePerGram;
    }
}
