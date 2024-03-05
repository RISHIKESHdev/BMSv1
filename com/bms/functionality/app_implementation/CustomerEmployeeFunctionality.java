package com.bms.functionality.app_implementation;

import com.bms.functionality.account.AccountLogic;
import com.bms.functionality.app_interface.CustomerEmployeeInterface;
import com.bms.functionality.card.CardLogics;
import com.bms.functionality.profile.ProfileLogic;
import com.bms.people.User;

import java.util.ArrayList;

public class CustomerEmployeeFunctionality extends UserFunctionality implements CustomerEmployeeInterface {
    private ArrayList<Double> customerAccountNumbers;
    public CustomerEmployeeFunctionality(){

    }
    public CustomerEmployeeFunctionality(User user){
        super(user);
    }
    public CustomerEmployeeFunctionality(ArrayList<Double> customerAccountNumbers){
        this.customerAccountNumbers=customerAccountNumbers;
    }
    @Override
    public void addCard() {
        CardLogics cardLogic = new CardLogics(customerAccountNumbers);
        cardLogic.registerCard();
    }

    @Override
    public void deleteCard() {
        CardLogics cardLogic = new CardLogics(customerAccountNumbers);
        cardLogic.deactivateCard();
    }
    @Override
    public void addGoldLoan(){
        AccountLogic accountLogic = new AccountLogic(customerAccountNumbers);
        accountLogic.registerGoldLoan();
    }
    @Override
    public void addHomeLoan(){
        AccountLogic accountLogic = new AccountLogic(customerAccountNumbers);
        accountLogic.registerHomeLoan();
    }

    @Override
    public void addNominee() {
        ProfileLogic profileLogic = new ProfileLogic(customerAccountNumbers);
        profileLogic.addNominee();
    }

    @Override
    public void deleteNominee() {
        ProfileLogic profileLogic = new ProfileLogic(customerAccountNumbers);
        profileLogic.removeNominee();
    }
}
