package com.bms.functionality.bank;

import com.bms.bank.Bank;
import com.bms.functionality.CommonConstant;

public class BankLogic implements BankInterface{

    public boolean registerBank(){
        boolean isBankCreated=false;

        Bank bank=getBankInfo();
        BankDataLogic dataLogic = new BankDataLogic();

        if(dataLogic.insertBankRecord(bank)){
            System.out.println("Bank Identification Number: "+ bank.getBankIdentificationNumber());
            isBankCreated=true;
        }

        return isBankCreated;
    }

    private Bank getBankInfo(){
        Bank bank;

        int bankIdentificationNumber;
        String bankName,bankCode,bankType;

        bankIdentificationNumber=CommonConstant.BANK_IDENTIFICATION_NUMBER;
        bankName=CommonConstant.BANK_NAME;
        bankCode=CommonConstant.BANK_CODE;
        bankType=CommonConstant.BANK_TYPE;

        bank=new Bank(bankIdentificationNumber,bankName,bankType,bankCode);

        return bank;
    }
}
