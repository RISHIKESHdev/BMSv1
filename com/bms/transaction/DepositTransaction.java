package com.bms.transaction;

import java.time.LocalDateTime;

public class DepositTransaction extends Transaction{
    private String depositIFSCCode;
    private String depositLocation;

    public DepositTransaction(LocalDateTime transactionDateTime, double transactionAmount,String depositIFSCCode){
        super(transactionDateTime, transactionAmount);
        this.depositIFSCCode=depositIFSCCode;
    }

    public DepositTransaction(LocalDateTime transactionDateTime, double transactionAmount, PaymentMode payModeDetail, String depositIFSCCode, String depositLocation) {
        super(transactionDateTime, transactionAmount, payModeDetail);
        this.depositIFSCCode = depositIFSCCode;
        this.depositLocation = depositLocation;
    }
    public String getDepositIFSCCode() {
        return depositIFSCCode;
    }

    public void setDepositIFSCCode(String depositIFSCCode) {
        this.depositIFSCCode = depositIFSCCode;
    }

    public String getDepositLocation() {
        return depositLocation;
    }

    public void setDepositLocation(String depositLocation) {
        this.depositLocation = depositLocation;
    }
}
