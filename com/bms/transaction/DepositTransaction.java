package com.bms.transaction;

import java.time.LocalDateTime;

public class DepositTransaction extends Transaction{
    private long beneficiaryAccountNumber;
    private long beneficiaryIFSCCode;
    private String depositLocation;

    public DepositTransaction(String transactionId, LocalDateTime transactionDateTime, double transactionAmount, PaymentMode payModeDetail, long beneficiaryAccountNumber, long beneficiaryIFSCCode, String depositLocation) {
        super(transactionId, transactionDateTime, transactionAmount, payModeDetail);
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
        this.beneficiaryIFSCCode = beneficiaryIFSCCode;
        this.depositLocation = depositLocation;
    }

    public long getBeneficiaryAccountNumber() {
        return beneficiaryAccountNumber;
    }

    public void setBeneficiaryAccountNumber(long beneficiaryAccountNumber) {
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }

    public long getBeneficiaryIFSCCode() {
        return beneficiaryIFSCCode;
    }

    public void setBeneficiaryIFSCCode(long beneficiaryIFSCCode) {
        this.beneficiaryIFSCCode = beneficiaryIFSCCode;
    }

    public String getDepositLocation() {
        return depositLocation;
    }

    public void setDepositLocation(String depositLocation) {
        this.depositLocation = depositLocation;
    }
}
