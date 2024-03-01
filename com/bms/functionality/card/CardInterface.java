package com.bms.functionality.card;

import com.bms.transaction.card.CoBrandedCreditCard;
import com.bms.transaction.card.CreditCard;
import com.bms.transaction.card.DebitCard;

public interface CardInterface {
    boolean registerCard();
    void displayCardMasterInfo(int cardCode);
    void deactivateMasterSavedCardById();
    int getCardCode();
    DebitCard saveNewDebitCard();
    CreditCard saveNewCreditCard();
    CoBrandedCreditCard saveNewCoBrandCreditCard();
}
