package com.bms.transaction;

import java.time.LocalDateTime;

public class TransferTransaction extends Transaction{
    public enum TransactionType{NEFT,IMPS};
    private long beneficiaryAccountNumber;
    private String beneficiaryIFSCCode;
    private long payeeAccountNumber;
    private String payeeIFSCCode;
    private TransactionType transactionType;

    public TransferTransaction(String transactionId, LocalDateTime transactionDateTime, double transactionAmount, PaymentMode payModeDetail, long beneficiaryAccountNumber, String beneficiaryIFSCCode, long payeeAccountNumber, String payeeIFSCCode,TransactionType transactionType) {
        super(transactionId, transactionDateTime, transactionAmount, payModeDetail);
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
        this.beneficiaryIFSCCode = beneficiaryIFSCCode;
        this.payeeAccountNumber = payeeAccountNumber;
        this.payeeIFSCCode = payeeIFSCCode;
        this.transactionType = transactionType;
    }

    public long getBeneficiaryAccountNumber() {
        return beneficiaryAccountNumber;
    }

    public void setBeneficiaryAccountNumber(long beneficiaryAccountNumber) {
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }

    public String getBeneficiaryIFSCCode() {
        return beneficiaryIFSCCode;
    }

    public void setBeneficiaryIFSCCode(String beneficiaryIFSCCode) {
        this.beneficiaryIFSCCode = beneficiaryIFSCCode;
    }

    public long getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(long payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public String getPayeeIFSCCode() {
        return payeeIFSCCode;
    }

    public void setPayeeIFSCCode(String payeeIFSCCode) {
        this.payeeIFSCCode = payeeIFSCCode;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}