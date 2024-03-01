package com.bms.transaction;

import java.time.LocalDateTime;
import java.util.Scanner;

public class TransferTransaction extends Transaction{
    public enum TransactionType{NEFT,IMPS};
    private Double beneficiaryAccountNumber;
    private String beneficiaryIFSCCode;
    private TransactionType transactionType;

    public TransferTransaction(LocalDateTime transactionDateTime, double transactionAmount, PaymentMode payModeDetail,double beneficiaryAccountNumber, String beneficiaryIFSCCode,TransactionType transactionType) {
        super(transactionDateTime, transactionAmount, payModeDetail);
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
        this.beneficiaryIFSCCode = beneficiaryIFSCCode;
        this.transactionType = transactionType;
    }

    public static TransactionType inputTransactionType(Scanner in){
        int transactionCode;
        System.out.println("Trsaction Type:- ");
        System.out.println("1. NEFT");
        System.out.println("2. IMPS");
        System.out.println("Select A Transaction Type From Above: ");transactionCode=in.nextInt();

        return (transactionCode==1)?(TransactionType.NEFT):(TransactionType.IMPS);
    }
    public double getBeneficiaryAccountNumber() {
        return beneficiaryAccountNumber;
    }

    public void setBeneficiaryAccountNumber(double beneficiaryAccountNumber) {
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }

    public String getBeneficiaryIFSCCode() {
        return beneficiaryIFSCCode;
    }

    public void setBeneficiaryIFSCCode(String beneficiaryIFSCCode) {
        this.beneficiaryIFSCCode = beneficiaryIFSCCode;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}