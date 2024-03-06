package com.bms.functionality.transaction;

import com.bms.Main;
import com.bms.functionality.MySQLConnection;
import com.bms.functionality.account.AccountLogic;
import com.bms.transaction.*;
import com.bms.transaction.cash.Cash;
import com.bms.transaction.cash.Currency;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TransactionDataLogic {
    private ArrayList<Double> customerAccountNumbers;
    public TransactionDataLogic(){
    }
    public TransactionDataLogic(ArrayList<Double> customerAccountNumbers){
        this.customerAccountNumbers=customerAccountNumbers;
    }
    private void displayAllTransaction(ArrayList<Transaction> transactions){
        for(Transaction transaction:transactions){
            if(transaction instanceof DepositTransaction){
                System.out.println("Transaction Date: " + transaction.getTransactionDateTime().format(Main.dateTimeFormat) + " Transaction Amount: " + transaction.getTransactionAmount() + " Deposit IFSC Code: " + ((DepositTransaction) transaction).getDepositIFSCCode());
            } else if (transaction instanceof WithdrawTransaction) {
                System.out.println("Transaction Date: " + transaction.getTransactionDateTime().format(Main.dateTimeFormat) + " Transaction Amount: " + transaction.getTransactionAmount() + " Withdraw IFSC Code: " + ((WithdrawTransaction) transaction).getWithdrawalIFSCCode());
            }else{
                System.out.printf("Transaction Date: " + transaction.getTransactionDateTime().format(Main.dateTimeFormat) + " Transaction Amount: " + transaction.getTransactionAmount() + " Beneficiary Account Number: %.0f" +" Beneficiary IFSC Code: "+ ((TransferTransaction) transaction).getBeneficiaryIFSCCode()+ " Transaction Type: " + ((TransferTransaction) transaction).getTransactionType().toString()+"\n",((TransferTransaction) transaction).getBeneficiaryAccountNumber());
            }
        }
    }
    boolean selectAndViewTransaction(){
        boolean isRecordAvailable=false;
        double accountNumber;
        ArrayList<Transaction> transactions=new ArrayList<>();

        AccountLogic accountLogic = new AccountLogic(customerAccountNumbers);
        accountNumber=accountLogic.getCustomerAccountNumber();

        try(Connection connection = MySQLConnection.connect()){
            if(accountNumber!=0 && connection !=null){
                connection.setAutoCommit(false);
                try(PreparedStatement ps= connection.prepareStatement(TransactionSQLQuery.SELECT_TRANSACTION_QUERY)){
                    ps.setDouble(1,accountNumber);
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        isRecordAvailable=true;
                        if(rs.getString("deposit_IFSC_Code")!=null){
                            DepositTransaction depositRecord= new DepositTransaction(rs.getTimestamp("transaction_DateTime").toLocalDateTime(),rs.getDouble("transaction_Amount"),rs.getString("deposit_IFSC_Code"));
                            transactions.add(depositRecord);
                        }else if(rs.getString("withdraw_IFSC_Code")!=null){
                            WithdrawTransaction withdrawRecord= new WithdrawTransaction(rs.getTimestamp("transaction_DateTime").toLocalDateTime(),rs.getDouble("transaction_Amount"),rs.getString("withdraw_IFSC_Code"));
                            transactions.add(withdrawRecord);
                        }else{
                            TransferTransaction transferRecord = new TransferTransaction(rs.getTimestamp("transaction_DateTime").toLocalDateTime(),rs.getDouble("transaction_Amount"),rs.getDouble("beneficiary_Account_Number"),rs.getString("beneficiary_IFSC_Code"), TransferTransaction.TransactionType.valueOf(rs.getString("transaction_Type")));
                            transactions.add(transferRecord);
                        }
                    }
                }
                if(isRecordAvailable) {
                    connection.commit();
                    displayAllTransaction(transactions);
                }
                else connection.rollback();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return isRecordAvailable;
    }
    private boolean insertTransactionRecord(Connection connection,Transaction transaction){
        boolean isRecordInserted=false;
        double accountNumber;
        int transactionId;

        AccountLogic accountLogic = new AccountLogic(customerAccountNumbers);
        accountNumber=accountLogic.getAccountNumberOnUserRequest(connection);

        if(accountNumber>0 && updateAmount(connection,transaction,accountNumber)){
            try(PreparedStatement ps = connection.prepareStatement(TransactionSQLQuery.INSERT_TRANSACTION_QUERY, Statement.RETURN_GENERATED_KEYS)){
                ps.setDouble(1,accountNumber);
                ps.setDouble(2,transaction.getTransactionAmount());
                int rs = ps.executeUpdate();
                if(rs>0){
                    ResultSet rSet = ps.getGeneratedKeys();
                    rSet.next();
                    transactionId=rSet.getInt(1);
                    transaction.setTransactionId(transactionId);
                    isRecordInserted=true;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return isRecordInserted;
    }
    boolean updateAmount(Connection connection,Transaction transaction,double accountNumber){
        boolean isAmountUpdated=false,isValid=false;

        try(CallableStatement cs = connection.prepareCall(TransactionSQLQuery.CHECK_VALID_ACCOUNT)){
            cs.registerOutParameter(4, Types.BOOLEAN);

            cs.setDouble(1,accountNumber);
            if(transaction instanceof WithdrawTransaction){
                cs.setString(2,"WITHDRAW");
            }else if(transaction instanceof DepositTransaction){
                cs.setString(2,"DEPOSIT");
            }else{
                cs.setString(2,"TRANSFER");
            }
            cs.setDouble(3,transaction.getTransactionAmount());
            cs.execute();

            isValid=cs.getBoolean("valid_Account");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        if(isValid){
            try(CallableStatement cs = connection.prepareCall(TransactionSQLQuery.UPDATE_AMOUNT_TRANSACTION_QUERY)){
                cs.registerOutParameter(5, Types.BOOLEAN);

                cs.setDouble(1,accountNumber);
                if(transaction instanceof WithdrawTransaction){
                    cs.setDouble(2,0);
                    cs.setString(3,"WITHDRAW");
                }else if(transaction instanceof DepositTransaction){
                    cs.setDouble(2,0);
                    cs.setString(3,"DEPOSIT");
                }else{
                    cs.setDouble(2,((TransferTransaction)transaction).getBeneficiaryAccountNumber());
                    cs.setString(3,"TRANSFER");
                }
                cs.setDouble(4,transaction.getTransactionAmount());
                cs.execute();

                isAmountUpdated=cs.getBoolean("isTransactionCompleted");
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }

        return isAmountUpdated;
    }
    boolean insertWithdrawalRecord(WithdrawTransaction withdrawTransaction){
        boolean isRecordInserted=false;

        try(Connection connection = MySQLConnection.connect()){
            if(connection !=null){
                connection.setAutoCommit(false);
                if(insertTransactionRecord(connection,withdrawTransaction)){
                    if(insertPayModeRecord(connection,withdrawTransaction.getPayModeDetail(), withdrawTransaction.getTransactionId())){
                        try(PreparedStatement ps = connection.prepareStatement(TransactionSQLQuery.INSERT_WITHDRAW_TRAN_QUERY)){
                            ps.setInt(1,withdrawTransaction.getTransactionId());
                            ps.setString(2,withdrawTransaction.getWithdrawalIFSCCode());
                            int rs = ps.executeUpdate();
                            isRecordInserted = (rs>0);
                        }
                    }
                }
                if(isRecordInserted ){
                    connection.commit();
                }else{
                    connection.rollback();
                }
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isRecordInserted;
    }
    boolean insertDepositRecord(DepositTransaction depositTransaction){
        boolean isRecordInserted=false;

        try(Connection connection = MySQLConnection.connect()){
            if(connection !=null){
                connection.setAutoCommit(false);
                if(insertTransactionRecord(connection,depositTransaction)){
                    if(insertPayModeRecord(connection,depositTransaction.getPayModeDetail(), depositTransaction.getTransactionId())){
                        try(PreparedStatement ps = connection.prepareStatement(TransactionSQLQuery.INSERT_DEPOSIT_TRAN_QUERY)){
                            ps.setInt(1,depositTransaction.getTransactionId());
                            ps.setString(2,depositTransaction.getDepositIFSCCode());
                            int rs = ps.executeUpdate();
                            isRecordInserted = (rs>0);
                        }
                    }
                }
                if(isRecordInserted ){
                    connection.commit();
                }else{
                    connection.rollback();
                }
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isRecordInserted;
    }
    boolean insertTransferRecord(TransferTransaction transferTransaction){
        boolean isRecordInserted=false;

        try(Connection connection = MySQLConnection.connect()){
            if(connection !=null){
                connection.setAutoCommit(false);
                if(insertTransactionRecord(connection,transferTransaction)){
                    try(PreparedStatement ps = connection.prepareStatement(TransactionSQLQuery.INSERT_TRANSFER_TRAN_QUERY)){
                        ps.setInt(1,transferTransaction.getTransactionId());
                        ps.setString(2,transferTransaction.getTransactionType().toString());
                        ps.setString(3,transferTransaction.getBeneficiaryIFSCCode());
                        ps.setDouble(4,transferTransaction.getBeneficiaryAccountNumber());
                        int rs = ps.executeUpdate();
                        isRecordInserted = (rs>0);
                    }
                }
                if(isRecordInserted ){
                    connection.commit();
                }else{
                    connection.rollback();
                }
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isRecordInserted;
    }
    private boolean insertPayModeRecord(Connection connection,PaymentMode payMode,int transactionId){
        boolean isRecordInserted=false;

        if(payMode instanceof Cash) isRecordInserted=insertCashRecord(connection,(Cash) payMode,transactionId);
        else if(payMode instanceof Cheque) isRecordInserted=insertChequeRecord(connection,(Cheque) payMode,transactionId);
        else if(payMode instanceof PayModeDetails) isRecordInserted=insertCardTransRecord(connection,(PayModeDetails) payMode,transactionId);

        return isRecordInserted;
    }

    private boolean insertCardTransRecord(Connection connection,PayModeDetails cardTransMap,int transactionId){
        boolean isCardTransInserted=false;

        try(PreparedStatement ps = connection.prepareStatement(TransactionSQLQuery.INSERT_CARD_TRAN_MAP)){
            ps.setDouble(1,cardTransMap.getCardNumber());
            ps.setInt(2,transactionId);
            int rs = ps.executeUpdate();
            isCardTransInserted=rs>0;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return isCardTransInserted;
    }
    private boolean insertCashRecord(Connection connection,Cash cash,int transactionId){
        boolean isCashInserted=false;
        double cashAmount = 0;

        try(PreparedStatement ps = connection.prepareStatement(TransactionSQLQuery.INSERT_CASH_MAP)){
            for(Currency currency:cash.getCurrencyList()){
                cashAmount=cashAmount+currency.getCount()* currency.getINRValue();
            }
            ps.setInt(1,transactionId);
            ps.setDouble(2,cashAmount);
            int rs = ps.executeUpdate();
            isCashInserted=rs>0;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return isCashInserted;
    }
    private boolean insertChequeRecord(Connection connection,Cheque cheque,int transactionId){
        boolean isChequeInserted=false;

        try(PreparedStatement ps = connection.prepareStatement(TransactionSQLQuery.INSERT_CHEQUE_MAP)){
            ps.setInt(1,transactionId);
            ps.setLong(2,cheque.getChequeNumber());
            ps.setString(3,cheque.getBeneficiaryName());
            int rs = ps.executeUpdate();
            isChequeInserted=rs>0;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return isChequeInserted;
    }
}
