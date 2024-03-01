package com.bms.functionality.transaction;

import com.bms.Main;
import com.bms.transaction.*;
import com.bms.transaction.cash.Cash;
import com.bms.transaction.cash.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionLogic implements TransactionInterface {
    private static final Scanner in = Main.globalIn;

    public boolean registerTransferTransaction(TransferTransaction transferTransaction){
        boolean isTransferRegistered=false;

        TransactionDataLogic dataLogic = new TransactionDataLogic();

        if(dataLogic.insertTransferRecord(transferTransaction)){
            System.out.println("Transfer Transaction Id: "+transferTransaction.getTransactionId());
            System.out.println("Transfer Transaction Record Inserted.");
        }else{
            System.out.println("Transfer Transaction Record Insertion Failed.");
        }

        return isTransferRegistered;
    }
    public boolean registerWithdrawalTransaction(WithdrawTransaction withdrawalTransaction){
        boolean isWithdrawalRegistered=false;

        TransactionDataLogic dataLogic = new TransactionDataLogic();

        if(dataLogic.insertWithdrawalRecord(withdrawalTransaction)){
            System.out.println("Transfer Transaction Id: "+withdrawalTransaction.getTransactionId());
            System.out.println("Transfer Transaction Record Inserted.");
        }else{
            System.out.println("Transfer Transaction Record Insertion Failed.");
        }

        return isWithdrawalRegistered;
    }
    public boolean registerDepositTransaction(DepositTransaction depositTransaction){
        boolean isDepositRegistered=false;

        TransactionDataLogic dataLogic = new TransactionDataLogic();

        if(dataLogic.insertDepositRecord(depositTransaction)){
            System.out.println("Transfer Transaction Id: "+depositTransaction.getTransactionId());
            System.out.println("Transfer Transaction Record Inserted.");
        }else{
            System.out.println("Transfer Transaction Record Insertion Failed.");
        }

        return isDepositRegistered;
    }
    public TransferTransaction getTransferTransaction(){
        TransferTransaction transferTransaction;
        PaymentMode payMode=null;
        double transactionAmount,beneficiaryAccountNumber;
        String beneficiaryIFSCCode;
        TransferTransaction.TransactionType transactionType;

        System.out.print("Transfer Amount: ");transactionAmount=in.nextDouble();
        System.out.print("Beneficiary Account Number: ");beneficiaryAccountNumber=in.nextDouble();
        System.out.print("Beneficiary IFSC Code: ");beneficiaryIFSCCode=in.next();
        transactionType=TransferTransaction.inputTransactionType(in);

        transferTransaction = new TransferTransaction(Main.currentDateTime,transactionAmount,payMode,beneficiaryAccountNumber,beneficiaryIFSCCode,transactionType);

        if(!isValidTransferTransaction(transferTransaction)) transferTransaction=null;

        return transferTransaction;
    }
    public DepositTransaction getDepositTransaction(){
        DepositTransaction depositTransaction;
        PaymentMode payMode;
        int payModeCode;
        double transactionAmount;
        String depositIFSCCode,depositLocation;

        payModeCode=getpayModeCode();
        payMode=getPaymentModeInfo(payModeCode);
        if(payMode!=null){
            System.out.print("Deposit Amount: ");transactionAmount=in.nextDouble();
            System.out.print("Deposit IFSC Code: ");depositIFSCCode=in.next();
            System.out.print("Deposit Location: ");depositLocation=in.next();
            depositTransaction = new DepositTransaction(Main.currentDateTime,transactionAmount,payMode,depositIFSCCode,depositLocation);
            if(!(isValidDepositTransaction(depositTransaction))) depositTransaction=null;
        }else{
            depositTransaction=null;
        }

        return depositTransaction;
    }
    public WithdrawTransaction getWithdrawalTransaction(){
        WithdrawTransaction withdrawalTransaction;
        PaymentMode payMode;
        int payModeCode;
        double transactionAmount;
        String withdrawalIFSCCode,withdrawalLocation;

        payModeCode=getpayModeCode();
        payMode=getPaymentModeInfo(payModeCode);
        if(payMode!=null){
            System.out.print("Withdrawal Amount: ");transactionAmount=in.nextDouble();
            System.out.print("Withdrawal IFSC Code: ");withdrawalIFSCCode=in.next();
            System.out.print("Withdrawal Location: ");withdrawalLocation=in.next();
            withdrawalTransaction = new WithdrawTransaction(Main.currentDateTime,transactionAmount,payMode,withdrawalIFSCCode,withdrawalLocation);
            if(!(isValidWithdrawalTransaction(withdrawalTransaction))) withdrawalTransaction=null;
        }else{
            withdrawalTransaction=null;
        }

        return withdrawalTransaction;
    }
    private int getpayModeCode(){
        int payModeCode;

        System.out.println("1. Cash.");
        System.out.println("2. Cheque.");
        System.out.println("3. Card.");

        System.out.print("Select Pay Code From Above: ");payModeCode=in.nextInt();

        return payModeCode;
    }
    private PaymentMode getPaymentModeInfo(int payModeCode){
        PaymentMode payMode = null;
        
        switch(payModeCode){
            case 1:{
                payMode=getCashInfo();
                break;
            }
            case 2:{
                payMode=getChequeInfo();
                break;
            }
            case 3:{
                double cardNumber;
                System.out.print("Enter Card Number: ");cardNumber=in.nextDouble();
                payMode=new PayModeDetails(cardNumber);
                break;
            }
        }
        
        return payMode;
    }
    private Cheque getChequeInfo(){
        Cheque cheque;
        long chequeNumber;
        String beneficiaryName;

        System.out.print("Enter Beneficiary Name: ");beneficiaryName = in.next();
        System.out.print("Enter Cheque Number: ");chequeNumber=in.nextLong();

        cheque = new Cheque(beneficiaryName,chequeNumber);

        return cheque;
    }
    private Cash getCashInfo(){
        Cash cash = null;
        Currency.Denomination denomination;
        String currencyDenomination;
        List<Currency> currencyList = new ArrayList<>();
        int count;
        double inrValue;
        boolean toContinue = true;

        while(toContinue){
            System.out.print("Enter Denomination");currencyDenomination=in.next();
            denomination=Currency.Denomination.valueOf(currencyDenomination);
            if(!isValidDenomination(denomination)){
                cash=null;
                break;
            }
            System.out.print("INR Value");inrValue=in.nextDouble();
            System.out.print("Denomination Count");count=in.nextInt();
            currencyList.add(new Currency(denomination,inrValue,count));
            System.out.print("Press 1 To Continue Adding Currency: ");toContinue=(in.nextInt()==1);
        }
        
        return cash;
    }
    private boolean isValidTransferTransaction(TransferTransaction transferTransaction){
        boolean isValid =true;

        return isValid;
    }
    private boolean isValidDepositTransaction(DepositTransaction depositTransaction){
        boolean isValid =true;

        return isValid;
    }
    private boolean isValidWithdrawalTransaction(WithdrawTransaction withdrawalTransaction){
        boolean isValid=true;

        return isValid;
    }
    private boolean isValidDenomination(Currency.Denomination denomination){
        boolean isValid=false;
        try{
            switch(denomination){
                case TEN: case TWENTY: case FIFTY: case HUNDRED: case TWO_HUNDRED: case FIVE_HUNDRED: case TWO_THOUSAND:{isValid=true;break;}
            }
        }
        catch(Exception e){
            isValid=false;
        }
        return isValid;
    }
}
