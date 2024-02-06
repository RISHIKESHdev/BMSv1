package com.bms.accounts.loan;

public class Gold implements Collateral{
    private String goldPurity;
    private String goldValuePerGram;
    private double weightInGram;
    public Gold(String goldPurity,String goldValuePerGram,double weightInGram){
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

    public String getGoldValuePerGram() {
        return goldValuePerGram;
    }

    public void setGoldValuePerGram(String goldValuePerGram) {
        this.goldValuePerGram = goldValuePerGram;
    }
}
