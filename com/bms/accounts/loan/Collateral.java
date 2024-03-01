package com.bms.accounts.loan;

public interface Collateral {
    double getLoanAmount();

    void setLoanAmount(double loanAmount);

    double getInterestRate();

    void setInterestRate(double interestRate);

    Loan.LoanType getLoanType();

    void setLoanType(Loan.LoanType loanType);

    double getLoanId();

    void setLoanId(double loanId);

    double getAccountNumber();

    void setAccountNumber(double accountNumber);
}