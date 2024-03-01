package com.bms.functionality.transaction;

import com.bms.transaction.DepositTransaction;
import com.bms.transaction.TransferTransaction;
import com.bms.transaction.WithdrawTransaction;

public interface TransactionInterface {
    boolean registerTransferTransaction(TransferTransaction transferTransaction);
    boolean registerWithdrawalTransaction(WithdrawTransaction withdrawalTransaction);
    boolean registerDepositTransaction(DepositTransaction depositTransaction);
    TransferTransaction getTransferTransaction();
    DepositTransaction getDepositTransaction();
    WithdrawTransaction getWithdrawalTransaction();
}
