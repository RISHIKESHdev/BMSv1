package com.bms.functionality.master;

import com.bms.functionality.card.CardLogics;
import com.bms.transaction.card.Card;
import com.bms.transaction.card.CoBrandedCreditCard;
import com.bms.transaction.card.CreditCard;
import com.bms.transaction.card.DebitCard;

public class CardMaster {
    public Card saveNewCard(){
        Card card = null;
        int cardCode;

        CardLogics cardLogic = new CardLogics();

        cardCode=cardLogic.getCardCode();
        switch(cardCode){
            case 1:{
                card = saveDebitCard();
                break;
            }
            case 2:{
                card = saveCoBrandCreditCard();
                break;
            }
            case 3:{
                card = saveCreditCard();
                break;
            }
        }

        return card;
    }
    public boolean deActivateCard(){
        boolean isCardDeActivated=false;

        CardLogics cardLogic = new CardLogics();
        cardLogic.deactivateMasterSavedCardById();

        return isCardDeActivated;
    }
    private CreditCard saveCreditCard(){
        CreditCard creditCard;

        CardLogics cardLogic = new CardLogics();
        creditCard = cardLogic.saveNewCreditCard();

        return creditCard;
    }
    private CoBrandedCreditCard saveCoBrandCreditCard(){
        CoBrandedCreditCard coBrandCreditCard;

        CardLogics cardLogic = new CardLogics();
        coBrandCreditCard = cardLogic.saveNewCoBrandCreditCard();

        return coBrandCreditCard;
    }
    private DebitCard saveDebitCard(){
        DebitCard debitCard;

        CardLogics cardLogic = new CardLogics();
        debitCard = cardLogic.saveNewDebitCard();

        return debitCard;
    }
}
