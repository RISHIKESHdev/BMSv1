package com.bms.functionality.transaction;

import com.bms.functionality.MySQLConnection;
import com.bms.functionality.account.AccountLogic;
import com.bms.transaction.*;
import com.bms.transaction.cash.Cash;
import com.bms.transaction.cash.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDataLogic {
    private boolean insertTransactionRecord(Connection connection,Transaction transaction){
        boolean isRecordInserted=false;
        double accountNumber;
        int transactionId;

        AccountLogic accountLogic = new AccountLogic();
        accountNumber=accountLogic.getAccountNumberOnUserRequest(connection);

        if(accountNumber>0){
            try(PreparedStatement ps = connection.prepareStatement(TransactionSQLQuery.INSERT_TRANSACTION_QUERY)){
                ps.setDouble(1,accountNumber);
                ps.setDouble(2,transaction.getTransactionAmount());
                int rs = ps.executeUpdate();
                if(rs>0 && ps.getGeneratedKeys().next()){
                    transactionId=ps.getGeneratedKeys().getInt(1);
                    transaction.setTransactionId(transactionId);
                    isRecordInserted=true;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return isRecordInserted;
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
                    if(insertPayModeRecord(connection,transferTransaction.getPayModeDetail(), transferTransaction.getTransactionId())){
                        try(PreparedStatement ps = connection.prepareStatement(TransactionSQLQuery.INSERT_TRANSFER_TRAN_QUERY)){
                            ps.setInt(1,transferTransaction.getTransactionId());
                            ps.setString(2,transferTransaction.getTransactionType().toString());
                            ps.setString(3,transferTransaction.getBeneficiaryIFSCCode());
                            ps.setDouble(4,transferTransaction.getBeneficiaryAccountNumber());
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
