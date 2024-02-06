package com.bms.bank;

import java.util.List;

public class Bank {
    private long bankIdentificationNumber;
    private String bankType;
    List<Branch> branchList;

    public Bank(long bankIdentificationNumber, String bankType) {
        this.bankIdentificationNumber = bankIdentificationNumber;
        this.bankType = bankType;
    }

    public long getBankIdentificationNumber() {
        return bankIdentificationNumber;
    }

    public void setBankIdentificationNumber(long bankIdentificationNumber) {
        this.bankIdentificationNumber = bankIdentificationNumber;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public List<Branch> getBranchList() {
        return branchList;
    }

    public void addBranchList(Branch branch) {
        this.branchList.add(branch);
    }
}