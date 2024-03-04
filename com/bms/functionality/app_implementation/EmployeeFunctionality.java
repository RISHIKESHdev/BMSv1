package com.bms.functionality.app_implementation;

import com.bms.functionality.account.AccountLogic;
import com.bms.functionality.app_interface.EmployeeInterface;
import com.bms.functionality.branch.BranchLogic;
import com.bms.functionality.card.CardLogics;
import com.bms.transaction.card.CoBrandedCreditCard;
import com.bms.transaction.card.CreditCard;
import com.bms.transaction.card.DebitCard;

public class EmployeeFunctionality extends CustomerEmployeeFunctionality implements EmployeeInterface {
    public EmployeeFunctionality(){

    }

    @Override
    public void changeCustomerBranch() {
        BranchLogic branchLogic = new BranchLogic();
        branchLogic.changeCustomerBranch();
    }

    @Override
    public CreditCard saveNewCreditCardMaster() {
        CardLogics cardLogic = new CardLogics();
        return cardLogic.saveNewCreditCard();
    }

    @Override
    public CoBrandedCreditCard saveNewBrandCreditCardMaster() {
        CardLogics cardLogic = new CardLogics();
        return cardLogic.saveNewCoBrandCreditCard();
    }

    @Override
    public DebitCard saveNewDebitCardMaster() {
        CardLogics cardLogic = new CardLogics();
        return cardLogic.saveNewDebitCard();
    }

    @Override
    public void createCustomerAccount() {
        int accountCode;

        AccountLogic accountLogic = new AccountLogic();

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
