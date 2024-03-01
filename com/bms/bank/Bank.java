package com.bms.bank;

import java.util.List;

public class Bank {
    private final int bankIdentificationNumber;
    private final String bankName;
    private final String bankCode;
    private final String bankType;
    List<Branch> branchList;

    public Bank(int bankIdentificationNumber,String bankName,String bankType, String bankCode) {
        this.bankIdentificationNumber=bankIdentificationNumber;
        this.bankName=bankName;
        this.bankType = bankType;
        this.bankCode=bankCode;
    }

    public int getBankIdentificationNumber() {
        return bankIdentificationNumber;
    }
    public String getBankType() {
        return bankType;
    }
    public List<Branch> getBranchList() {
        return branchList;
    }
    public String getBankCode() {
        return bankCode;
    }
    public String getBankName() {
        return bankName;
    }
    public void addBranchList(Branch branch) {
        this.branchList.add(branch);
    }
}