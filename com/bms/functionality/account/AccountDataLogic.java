package com.bms.functionality.account;

import com.bms.Main;
import com.bms.accounts.Account;
import com.bms.accounts.CurrentAccount;
import com.bms.accounts.FixedDepositAccount;
import com.bms.accounts.SavingAccount;
import com.bms.accounts.loan.Gold;
import com.bms.accounts.loan.Home;
import com.bms.functionality.MySQLConnection;
import com.bms.functionality.branch.BranchLogic;
import com.bms.functionality.profile.ProfileLogic;
import com.bms.people.Customer;
import com.bms.people.Nominee;
import com.bms.people.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountDataLogic {
    private final Scanner in = Main.globalIn;
    private ArrayList<Double> customerAccountNumbers;
    private User loggedInUserInfo;
    public AccountDataLogic(ArrayList<Double> loggedInCustomerAccountNumbers, User loggedInUserInfo){
        this.loggedInUserInfo=loggedInUserInfo;
        this.customerAccountNumbers=loggedInCustomerAccountNumbers;
    }
    public AccountDataLogic(){

    }
    public AccountDataLogic(ArrayList<Double> customerAccountNumbers){
        this.customerAccountNumbers=customerAccountNumbers;
    }

    private boolean insertAccount(Connection connection, Account account, int branch_Id){
        boolean isAccountInserted=false;

        try(PreparedStatement ps = connection.prepareStatement(AccountSQLQuery.INSERT_ACCOUNT_QUERY, Statement.RETURN_GENERATED_KEYS)){
            ps.setDouble(1, account.getCurrentBalance());
            ps.setDouble(2, account.getAvailableBalance());
            ps.setDouble(3, account.getCreditScore());
            ps.setInt(4, branch_Id);
            int rs = ps.executeUpdate();
            if(rs>0){
                ResultSet rSet = ps.getGeneratedKeys();
                rSet.next();
                account.setAccountNumber(rSet.getDouble(1));
                isAccountInserted=true;
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isAccountInserted;
    }

    boolean getCustomerAccountNumbers(ArrayList<Double> customerAccountNumbers,double CIFNumber){
        boolean isAccountNumberAvailable=false;

        try(Connection connection = MySQLConnection.connect()){
            if(connection!=null){
                try(PreparedStatement ps = connection.prepareStatement(AccountSQLQuery.SELECT_CUSTOMER_ACCOUNTS_QUERY)){
                    ps.setDouble(1,CIFNumber);
                    ResultSet rs=ps.executeQuery();
                    while(rs.next()){
                        customerAccountNumbers.add(rs.getDouble("account_Number"));
                        isAccountNumberAvailable=true;
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return isAccountNumberAvailable;
    }
    boolean insertCurrentAccount(CurrentAccount currentAccount){
        Nominee nominee=null;
        boolean isCurrentAccountInserted=false;
        double accountNumber=0,CIFNumber;
        int branch_Id,rs = 0,rs1=0;

        BranchLogic branchLogic = new BranchLogic();

        try(Connection connection = MySQLConnection.connect()) {
            if(connection != null){
                connection.setAutoCommit(false);
                CIFNumber=getCustomerCIF(connection);
                branch_Id=branchLogic.getBranchId(connection);
                if(CIFNumber!=0 && branch_Id!=0 && insertAccount(connection,currentAccount,branch_Id)){
                    accountNumber=currentAccount.getAccountNumber();
                    try(PreparedStatement ps = connection.prepareStatement(AccountSQLQuery.INSERT_CURRENT_ACCOUNT_QUERY)){
                        ps.setDouble(1,accountNumber);
                        ps.setDouble(2,currentAccount.getOverDraftLimit());
                        rs = ps.executeUpdate();
                    }
                }
                if(rs > 0){
                    try(PreparedStatement ps = connection.prepareStatement(AccountSQLQuery.INSERT_ACCOUNT_CUSTOMER_MAP_QUERY)){
                        ps.setDouble(1,CIFNumber);
                        ps.setDouble(2,accountNumber);
                        rs1=ps.executeUpdate();
                    }
                }
                if (rs1 > 0) {
                    ProfileLogic profile = new ProfileLogic();
                    nominee=profile.addNomineeOnAccountCreation(connection,accountNumber);
                }
                if(rs>0 && rs1>0 && nominee !=null){
                    connection.commit();
                    isCurrentAccountInserted=true;
                }else{
                    connection.rollback();
                }
            }
        } catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isCurrentAccountInserted;
    }
    boolean insertFixedDepositAccount(FixedDepositAccount FDAccount){
        Nominee nominee=null;
        boolean isFixedDepositAccountInserted=false;
        double accountNumber=0,CIFNumber;
        int branch_Id,rs = 0,rs1=1;

        BranchLogic branchLogic = new BranchLogic();

        try(Connection connection = MySQLConnection.connect()) {
            if(connection != null){
                connection.setAutoCommit(false);
                CIFNumber=getCustomerCIF(connection);
                branch_Id=branchLogic.getBranchId(connection);
                if(CIFNumber!=0 && branch_Id!=0 && insertAccount(connection,FDAccount,branch_Id)){
                    accountNumber=FDAccount.getAccountNumber();
                    try(PreparedStatement ps = connection.prepareStatement(AccountSQLQuery.INSERT_FD_ACCOUNT_QUERY)){
                        ps.setDouble(1,accountNumber);
                        ps.setInt(2,FDAccount.getTenure());
                        ps.setDouble(3,FDAccount.getRateOfInterest());
                        ps.setString(4,FDAccount.getMatureDateTime().format(Main.dbDateTimeFormat));
                        rs = ps.executeUpdate();
                    }
                }
                if(rs > 0){
                    try(PreparedStatement ps = connection.prepareStatement(AccountSQLQuery.INSERT_ACCOUNT_CUSTOMER_MAP_QUERY)){
                        ps.setDouble(1,CIFNumber);
                        ps.setDouble(2,accountNumber);
                        rs1=ps.executeUpdate();
                    }
                }
                if (rs1 > 0) {
                    ProfileLogic profile = new ProfileLogic();
                    nominee=profile.addNomineeOnAccountCreation(connection,accountNumber);
                }
                if(rs>0 && rs1>0 && nominee !=null){
                    connection.commit();
                    isFixedDepositAccountInserted=true;
                }else{
                    connection.rollback();
                }
            }
        } catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isFixedDepositAccountInserted;
    }
    boolean insertSavingAccount(SavingAccount savingAccount){
        Nominee nominee = null;
        boolean isSavingAccountInserted=false;
        double accountNumber=0,CIFNumber;
        int branch_Id,rs = 0,rs1=0;

        BranchLogic branchLogic = new BranchLogic();

        try(Connection connection = MySQLConnection.connect()) {
            if(connection != null){
                connection.setAutoCommit(false);
                CIFNumber=getCustomerCIF(connection);
                branch_Id=branchLogic.getBranchId(connection);
                if(CIFNumber!=0 && branch_Id!=0 && insertAccount(connection,savingAccount,branch_Id)){
                    accountNumber=savingAccount.getAccountNumber();
                    try(PreparedStatement ps = connection.prepareStatement(AccountSQLQuery.INSERT_SAVING_ACCOUNT_QUERY)){
                        ps.setDouble(1,accountNumber);
                        ps.setDouble(2,savingAccount.getMinimumAccountBalance());
                        ps.setDouble(3,savingAccount.getWithdrawalLimit());
                        ps.setDouble(4,savingAccount.getRateOfInterest());
                        rs = ps.executeUpdate();
                    }
                }
                if(rs > 0){
                    try(PreparedStatement ps = connection.prepareStatement(AccountSQLQuery.INSERT_ACCOUNT_CUSTOMER_MAP_QUERY)){
                        ps.setDouble(1,CIFNumber);
                        ps.setDouble(2,accountNumber);
                        rs1=ps.executeUpdate();
                    }
                }
                if (rs1 > 0) {
                    ProfileLogic profile = new ProfileLogic();
                    System.out.println("Nominee Details:-");
                    nominee=profile.addNomineeOnAccountCreation(connection,accountNumber);
                }
                if(rs>0 && rs1>0 && nominee !=null){
                    connection.commit();
                    isSavingAccountInserted=true;
                }else{
                    connection.rollback();
                }
            }
        } catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isSavingAccountInserted;
    }

    private Double getCustomerCIF(Connection connection){
        Customer customer;
        boolean isCustomerCreated;
        double CIFNumber = 0;

        ProfileLogic profile = new ProfileLogic();

        if(loggedInUserInfo==null){
            System.out.print("Press 1 to Create Add New Customer or 0 to continue: ");isCustomerCreated=(in.nextInt() != 1);
            if(!isCustomerCreated){
                customer=profile.registerCustomerOnAccountCreation(connection);
                if(customer!=null){
                    CIFNumber=customer.getCIFNumber();
                }
            }else{
                System.out.print("Enter CIF Number of the Existing Customer to Map: ");CIFNumber=in.nextDouble();
                if(!profile.checkCustomerByCIFNumber(connection,CIFNumber)){
                    System.out.println("Customer Record Not Found For Entered CIF Number.");
                }
            }
        }else{
            CIFNumber=((Customer)loggedInUserInfo).getCIFNumber();
        }

        return CIFNumber;
    }
    private boolean checkAccountNumberPresent(Connection connection,double accountNumber){
        boolean isAccountNumberPresent=false;

        try(PreparedStatement ps = connection.prepareStatement(AccountSQLQuery.SELECT_CHECK_ACCOUNT_NUMBER)){
            ps.setDouble(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if(rs.getInt(1)>0){
                    isAccountNumberPresent=true;
                }
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isAccountNumberPresent;
    }
    double getAccountNumber(Connection connection){
        double accountNumber;

        System.out.print("Account Number: ");accountNumber=in.nextDouble();
        if(!checkAccountNumberPresent(connection,accountNumber)){
            System.out.println("Entered Account Number Not Found.");
            accountNumber=0;
        }

        return accountNumber;
    }
    boolean insertGoldLoan(Gold goldLoan){
        int loanId = 0,rs,rs1;
        double accountNumber;
        boolean isGoldLoanInserted=false;

        AccountLogic accountLogic = new AccountLogic(customerAccountNumbers);

        try(Connection connection = MySQLConnection.connect()){
            if(connection!=null){
                connection.setAutoCommit(false);
                accountNumber=accountLogic.getAccountNumberOnUserRequest(connection);
                if(accountNumber>2450000000000000D){
                    try(PreparedStatement ps = connection.prepareStatement(AccountSQLQuery.INSERT_LOAN_QUERY, Statement.RETURN_GENERATED_KEYS)){
                        ps.setDouble(1,accountNumber);
                        ps.setDouble(2,goldLoan.getLoanAmount());
                        ps.setDouble(3,goldLoan.getInterestRate());
                        ps.setString(4,goldLoan.getLoanType().toString());
                        rs = ps.executeUpdate();
                        if(rs>0){
                            ResultSet rSet = ps.getGeneratedKeys();
                            rSet.next();
                            loanId=rSet.getInt(1);
                            goldLoan.setLoanId(loanId);
                        }
                    }
                    if(rs>0 && loanId!=0){
                        try(PreparedStatement ps = connection.prepareStatement(AccountSQLQuery.INSERT_GOLD_LOAN_QUERY)){
                            ps.setInt(1,loanId);
                            ps.setString(2,goldLoan.getGoldPurity());
                            ps.setDouble(3,goldLoan.getGoldValuePerGram());
                            ps.setDouble(4,goldLoan.getWeightInGram());
                            rs1=ps.executeUpdate();
                            if(rs1>0){
                                isGoldLoanInserted=true;
                            }
                        }
                    }
                }
                if(isGoldLoanInserted){
                    connection.commit();
                }else{
                    connection.rollback();
                }
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isGoldLoanInserted;
    }
    boolean insertHomeLoan(Home homeLoan){
        int loanId = 0,rs,rs1;
        double accountNumber;
        boolean isHomeLoanInserted=false;

        AccountLogic accountLogic = new AccountLogic(customerAccountNumbers);

        try(Connection connection = MySQLConnection.connect()){
            if(connection!=null){
                connection.setAutoCommit(false);
                accountNumber=accountLogic.getAccountNumberOnUserRequest(connection);
                if(accountNumber>2450000000000000D){
                    try(PreparedStatement ps = connection.prepareStatement(AccountSQLQuery.INSERT_LOAN_QUERY, Statement.RETURN_GENERATED_KEYS)){
                        ps.setDouble(1,accountNumber);
                        ps.setDouble(2,homeLoan.getLoanAmount());
                        ps.setDouble(3,homeLoan.getInterestRate());
                        ps.setString(4,homeLoan.getLoanType().toString());
                        rs = ps.executeUpdate();
                        if(rs>0){
                            ResultSet rSet=ps.getGeneratedKeys();
                            rSet.next();
                            loanId=rSet.getInt(1);
                            homeLoan.setLoanId(loanId);
                        }
                    }
                    if(rs>0 && loanId!=0){
                        try(PreparedStatement ps = connection.prepareStatement(AccountSQLQuery.INSERT_HOME_LOAN_QUERY)){
                            ps.setInt(1,loanId);
                            ps.setDouble(2,homeLoan.getBuildUpArea());
                            ps.setDouble(3,homeLoan.getTotalArea());
                            ps.setDouble(4,homeLoan.getTotalValue());
                            ps.setInt(5,homeLoan.getTotalNoOfFloors());
                            rs1=ps.executeUpdate();
                            if(rs1>0){
                                isHomeLoanInserted=true;
                            }
                        }
                    }
                }
                if(isHomeLoanInserted){
                    connection.commit();
                }else{
                    connection.rollback();
                }
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isHomeLoanInserted;
    }
}
