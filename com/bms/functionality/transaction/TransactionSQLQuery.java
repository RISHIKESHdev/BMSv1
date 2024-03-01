package com.bms.functionality.transaction;

public class TransactionSQLQuery {
    public static final String INSERT_TRANSACTION_QUERY="INSERT INTO Transaction(account_Number,transaction_DateTime,transaction_Amount) VALUE(?,CURRENT_TIMESTAMP,?)";
    public static final String INSERT_CARD_TRAN_MAP="INSERT INTO Card_Transaction_Map(card_Number,transaction_Id) VALUE(?,?);";
    public static final String INSERT_CASH_MAP="INSERT INTO Cash(transaction_Id,cash_Amount) VALUE(?,?)";
    public static final String INSERT_CHEQUE_MAP="INSERT INTO Cheque(transaction_Id,chequeNumber,beneficiaryName) VALUE(?,?,?)";
    public static final String INSERT_TRANSFER_TRAN_QUERY="INSERT INTO Transfer_Transaction(transaction_Id,transaction_Type,beneficiary_IFSC_Code,beneficiary_Account_Number) VALUE(?,?,?,?)";
    public static final String INSERT_WITHDRAW_TRAN_QUERY="INSERT INTO Withdraw_Transaction(transaction_Id,withdraw_IFSC_Code) VALUE(?,?)";
    public static final String INSERT_DEPOSIT_TRAN_QUERY="INSERT INTO Deposit_Transaction(transaction_Id,deposit_IFSC_Code) VALUE(?,?)";
}
