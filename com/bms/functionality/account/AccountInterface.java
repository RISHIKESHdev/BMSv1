package com.bms.functionality.account;

import com.bms.accounts.CurrentAccount;
import com.bms.accounts.FixedDepositAccount;
import com.bms.accounts.SavingAccount;
import com.bms.accounts.loan.Loan;

public interface AccountInterface {
    FixedDepositAccount registerFDAccount();
    CurrentAccount registerCurrentAccount();
    SavingAccount registerSavingAccount();
    Loan registerGoldLoan();
    Loan registerHomeLoan();
}
