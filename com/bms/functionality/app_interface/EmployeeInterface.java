package com.bms.functionality.app_interface;

import com.bms.transaction.card.CoBrandedCreditCard;
import com.bms.transaction.card.CreditCard;
import com.bms.transaction.card.DebitCard;

public interface EmployeeInterface extends CustomerEmployeeInterface{
    public CreditCard saveNewCreditCardMaster();
    public CoBrandedCreditCard saveNewBrandCreditCardMaster();
    public DebitCard saveNewDebitCardMaster();
    public void createCustomerAccount();
    public void changeCustomerBranch();
}