package com.bms.functionality.transaction;

public class TransactionSQLQuery {
    public static final String INSERT_TRANSACTION_QUERY="INSERT INTO Transaction(account_Number,transaction_DateTime,transaction_Amount) VALUE(?,CURRENT_TIMESTAMP,?)";
    public static final String INSERT_CARD_TRAN_MAP="INSERT INTO Card_Transaction_Map(card_Number,transaction_Id) VALUE(?,?);";
    public static final String INSERT_CASH_MAP="INSERT INTO Cash(transaction_Id,cash_Amount) VALUE(?,?)";
    public static final String INSERT_CHEQUE_MAP="INSERT INTO Cheque(transaction_Id,chequeNumber,beneficiaryName) VALUE(?,?,?)";
    public static final String INSERT_TRANSFER_TRAN_QUERY="INSERT INTO Transfer_Transaction(transaction_Id,transaction_Type,beneficiary_IFSC_Code,beneficiary_Account_Number) VALUE(?,?,?,?)";
    public static final String INSERT_WITHDRAW_TRAN_QUERY="INSERT INTO Withdraw_Transaction(transaction_Id,withdraw_IFSC_Code) VALUE(?,?)";
    public static final String INSERT_DEPOSIT_TRAN_QUERY="INSERT INTO Deposit_Transaction(transaction_Id,deposit_IFSC_Code) VALUE(?,?)";
    public static final String UPDATE_AMOUNT_TRANSACTION_QUERY = "CALL update_Amount_On_Transaction(?,?,?,?,?)";
    public static final String CHECK_VALID_CARD_TRANSACTION_QUERY="CALL Check_Valid_Card_Transaction(?,?,?,?,@validTransaction)";
    public static final String CHECK_VALID_ACCOUNT="CALL is_Valid_Account(?,?,?,?)";
    public static final String SELECT_TRANSACTION_QUERY="SELECT TRANSACTION.ID AS transactionID,transaction_DateTime,transaction_Amount,deposit_IFSC_Code,withdraw_IFSC_Code,transaction_Type,beneficiary_IFSC_Code,beneficiary_Account_Number FROM TRANSACTION LEFT JOIN Deposit_Transaction ON TRANSACTION.ID=Deposit_Transaction.transaction_Id LEFT JOIN Withdraw_Transaction ON TRANSACTION.ID=Withdraw_Transaction.transaction_Id LEFT JOIN Transfer_Transaction ON TRANSACTION.ID=Transfer_Transaction.transaction_Id WHERE TRANSACTION.account_Number=? ORDER BY TRANSACTION.transaction_DateTime DESC";
}
