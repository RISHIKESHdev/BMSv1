package com.bms.functionality.bank;

import com.bms.bank.Bank;
import com.bms.functionality.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class BankDataLogic {
    boolean insertBankRecord(Bank bank){
        boolean isBankRecordInserted=false;

        try(Connection connection = MySQLConnection.connect()) {
            if(connection != null){
                try(PreparedStatement ps = connection.prepareStatement(BankSQLQuery.INSERT_BANK_QUERY)){
                    ps.setInt(1,bank.getBankIdentificationNumber());
                    ps.setString(2,bank.getBankName());
                    ps.setString(3,bank.getBankCode());
                    ps.setString(4,bank.getBankType());
                    int rs = ps.executeUpdate();
                    isBankRecordInserted=(rs>0);
                }
            }
        } catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }


        return isBankRecordInserted;
    }
}
