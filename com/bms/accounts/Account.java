package com.bms.accounts;

import com.bms.accounts.loan.Loan;
import com.bms.people.Nominee;
import com.bms.transaction.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Account {
    private long accountNumber;
    private double currentBalance;
    private double availableBalance;
    private double creditScore;
    private LocalDateTime accountInceptionDateTime;
    private List<Transaction> transactionList;
    private List<Loan> loanList;
    private List<Nominee> nomineeList;

    public Account(long accountNumber, double currentBalance, double availableBalance, double creditScore, LocalDateTime accountInceptionDateTime) {
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
        this.availableBalance = availableBalance;
        this.creditScore = creditScore;
        this.accountInceptionDateTime = accountInceptionDateTime;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public double getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(double creditScore) {
        this.creditScore = creditScore;
    }

    public LocalDateTime getAccountInceptionDate() {
        return accountInceptionDateTime;
    }

    public void setAccountInceptionDate(LocalDateTime accountInceptionDateTime) {
        this.accountInceptionDateTime = accountInceptionDateTime;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void addTransactionList(Transaction transaction) {
        this.transactionList.add(transaction);
    }

    public List<Loan> getLoanList() {
        return loanList;
    }

    public void addLoanList(Loan loan) {
        this.loanList.add(loan);
    }

    public List<Nominee> getNomineeList() {
        return nomineeList;
    }

    public void addNomineeList(Nominee nominee) {
        this.nomineeList.add(nominee);
    }
}