package com.bms.accounts.loan;

public class Home extends  Loan implements Collateral{
    private int totalArea;
    private int builtUpArea;
    private int totalNoOfFloors;
    private int totalValue;
    public Home(double loanAmount, double interestRate , LoanType loanType,int totalArea, int buildUpArea, int totalNoOfFloors,int totalValue){
        super( loanAmount,  interestRate ,  loanType);
        this.totalArea=totalArea;
        this.builtUpArea=buildUpArea;
        this.totalNoOfFloors=totalNoOfFloors;
        this.totalValue=totalValue;
    }

    public int getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(int totalArea) {
        this.totalArea = totalArea;
    }

    public int getBuildUpArea() {
        return builtUpArea;
    }

    public void setBuildUpArea(int buildUpArea) {
        this.builtUpArea = buildUpArea;
    }

    public int getTotalNoOfFloors() {
        return totalNoOfFloors;
    }

    public void setTotalNoOfFloors(int totalNoOfFloors) {
        this.totalNoOfFloors = totalNoOfFloors;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }
}
