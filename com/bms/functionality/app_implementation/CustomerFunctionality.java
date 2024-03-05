package com.bms.functionality.app_implementation;

import com.bms.functionality.account.AccountLogic;
import com.bms.functionality.app_interface.CustomerInterface;
import com.bms.functionality.profile.ProfileLogic;
import com.bms.functionality.transaction.TransactionLogic;
import com.bms.people.User;
import com.bms.transaction.DepositTransaction;
import com.bms.transaction.TransferTransaction;
import com.bms.transaction.WithdrawTransaction;

import java.util.ArrayList;

public class CustomerFunctionality extends CustomerEmployeeFunctionality implements CustomerInterface{
    private ArrayList<Double> loggedInCustomerAccountNumbers;
    private User loggedInUserInfo;
    public CustomerFunctionality(User customer){
        super(customer);
    }
    public CustomerFunctionality(){

    }
    public CustomerFunctionality(ArrayList<Double> customerAccountNumbers,User customer){
        this.loggedInCustomerAccountNumbers=customerAccountNumbers;
        this.loggedInUserInfo=customer;
    }

    @Override
    public void updateCustomerProfile() {
        ProfileLogic profileLogic = new ProfileLogic(loggedInCustomerAccountNumbers,loggedInUserInfo);
        profileLogic.customerProfileUpdate();
    }

    @Override
    public void transferAmount() {
        TransactionLogic transactionLogic = new TransactionLogic(loggedInCustomerAccountNumbers,loggedInUserInfo);
        TransferTransaction transferTransaction = transactionLogic.getTransferTransaction();
        transactionLogic.registerTransferTransaction(transferTransaction);
    }

    @Override
    public void withdrawAmount() {
        TransactionLogic transactionLogic = new TransactionLogic(loggedInCustomerAccountNumbers,loggedInUserInfo);
        WithdrawTransaction withdrawTransaction = transactionLogic.getWithdrawalTransaction();
        transactionLogic.registerWithdrawalTransaction(withdrawTransaction);
    }

    @Override
    public void depositAmount() {
        TransactionLogic transactionLogic = new TransactionLogic(loggedInCustomerAccountNumbers,loggedInUserInfo);
        DepositTransaction depositTransaction = transactionLogic.getDepositTransaction();
        transactionLogic.registerDepositTransaction(depositTransaction);
    }

    @Override
    public void viewAllTransaction() {
        TransactionLogic transactionLogic = new TransactionLogic(loggedInCustomerAccountNumbers,loggedInUserInfo);
        transactionLogic.viewAllTransaction();
    }

    @Override
    public void addAccount() {
        int accountCode;

        AccountLogic accountLogic = new AccountLogic(loggedInCustomerAccountNumbers,loggedInUserInfo);

        accountCode=accountLogic.getAccountCode();

        switch (accountCode){
            case 1:{
                accountLogic.registerFDAccount();
                break;
            }
            case 2:{
                accountLogic.registerCurrentAccount();
                break;
            }
            case 3:{
                accountLogic.registerSavingAccount();
                break;
            }
        }
    }
}
