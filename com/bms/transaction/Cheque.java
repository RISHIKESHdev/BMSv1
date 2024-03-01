package com.bms.transaction;

public class Cheque implements PaymentMode{
    private double transactionId;
    private String beneficiaryName;
    private long chequeNumber;

    public Cheque(String beneficiaryName, long chequeNumber) {
        this.beneficiaryName = beneficiaryName;
        this.chequeNumber = chequeNumber;
    }

    public double getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(double transactionId) {
        this.transactionId = transactionId;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public long getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(long chequeNumber) {
        this.chequeNumber = chequeNumber;
    }
}
