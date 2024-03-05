package com.bms.functionality.card;

import com.bms.Main;
import com.bms.transaction.card.Card;
import com.bms.transaction.card.CoBrandedCreditCard;
import com.bms.transaction.card.CreditCard;
import com.bms.transaction.card.DebitCard;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class CardLogics implements CardInterface{
    private final Scanner in = Main.globalIn;
    private ArrayList<Double> customerAccountNumbers;
    public CardLogics(){

    }
    public CardLogics(ArrayList<Double> customerAccountNumbers){
        this.customerAccountNumbers=customerAccountNumbers;
    }

    public boolean registerCard(){
        Card card;
        int cardCode;
        boolean isCardSaved=false;

        CardDataLogic dataLogic = new CardDataLogic(customerAccountNumbers);
        cardCode=getCardCode();
        displayCardMasterInfo(cardCode);
        card=getCardInfo(cardCode);

        if(card!=null && dataLogic.registerCardRecord(card)){
            System.out.println("New Card Added Successfully.");
        }else{
            System.out.println("New Card Insertion Failed.");
        }

        return isCardSaved;
    }

    public double getCardNumber(Connection connection,double accountNumber){
        double cardNumber;

        CardDataLogic dataLogic = new CardDataLogic();

        if(accountNumber!=0 && dataLogic.displayActiveCard(connection,accountNumber)){
            System.out.println("Card Number: ");cardNumber=in.nextDouble();
        }else{
            cardNumber=0;
            System.out.println("Card Not Found For The Entered Account number.");
        }

        return cardNumber;
    }
    public boolean deactivateCard(){
        boolean isDeactivated=false;

        CardDataLogic dataLogic = new CardDataLogic(customerAccountNumbers);

        if(dataLogic.deactivateCardRecord()){
            System.out.println("New Card Added Successfully.");
        }else{
            System.out.println("New Card Insertion Failed.");
        }

        return isDeactivated;
    }
    private Card getCardInfo(int cardCode){
        Card card = null;
        int cardId=0;
        String cardHolderName;

        CardDataLogic dataLogic = new CardDataLogic();

        System.out.print("Select A Card Id: ");cardId=in.nextInt();

        switch(cardCode){
            case 1:{
                card=dataLogic.getDebitCardMasterById(cardId);
                break;
            }
            case 2:{
                card=dataLogic.getCreditCardMasterById(cardId);
                break;
            }
            case 3:{
                card=dataLogic.getCoBrandCreditCardMasterById(cardId);
                break;
            }
        }
        if(card !=null){
            System.out.print("Enter Card Holder Name: ");cardHolderName=in.next();
            card.setCardHolderName(cardHolderName);
        }
        return card;
    }
    public void displayCardMasterInfo(int cardCode){

        CardDataLogic dataLogic = new CardDataLogic();

        switch(cardCode){
            case 1:{
                dataLogic.displayAvailableDebitCard();
                break;
            }
            case 2:{
                dataLogic.displayAvailableCreditCard();
                break;
            }
            case 3:{
                dataLogic.displayAvailableCoBrandedCreditCard();
                break;
            }
        }
    }

    public void deactivateMasterSavedCardById(){
        int cardId;

        CardDataLogic dataLogic = new CardDataLogic();
        System.out.print("Enter Card Id To Deactivate A Card: ");cardId=in.nextInt();
        if(dataLogic.deactivateMasterCard(cardId)){
            System.out.println("Card Deactivated Successfully.");
        }else{
            System.out.println("Card Deactivation Failed.");
        }
    }
    public int getCardCode(){
        int cardCode;

        System.out.println("1. Debit Card.");
        System.out.println("2. Credit Card.");
        System.out.println("3. Co Branded Credit Card.");

        System.out.print("Select A Card Code From Above: ");cardCode=in.nextInt();

        return cardCode;
    }

    public DebitCard saveNewDebitCard(){
        DebitCard debitCard;

        CardDataLogic dataLogic = new CardDataLogic();
        debitCard=(DebitCard) getNewCardInfo(1);

        if(debitCard!=null && dataLogic.insertNewDebitCard(debitCard)){
            System.out.println("Debit Card Master Saved Successfully.");
        }else{
            System.out.println("Debit Card Master Save Failed.");
        }

        return debitCard;
    }
    public CreditCard saveNewCreditCard(){
        CreditCard creditCard;

        CardDataLogic dataLogic = new CardDataLogic();
        creditCard=(CreditCard) getNewCardInfo(2);

        if(creditCard!=null && dataLogic.insertNewCreditCard(creditCard)){
            System.out.println("Credit Card Master Saved Successfully.");
        }else{
            System.out.println("Credit Card Master Save Failed.");
        }

        return creditCard;
    }
    public CoBrandedCreditCard saveNewCoBrandCreditCard(){
        CoBrandedCreditCard coBrandedCreditCard;

        CardDataLogic dataLogic = new CardDataLogic();
        coBrandedCreditCard=(CoBrandedCreditCard) getNewCardInfo(1);

        if(coBrandedCreditCard!=null && dataLogic.insertNewCoBrandedCreditCard(coBrandedCreditCard)){
            System.out.println("Co Branded Credit Card Master Saved Successfully.");
        }else{
            System.out.println("Co Branded Credit Card Master Save Failed.");
        }

        return coBrandedCreditCard;
    }


    private Card getNewCardInfo(int cardCode){
        Card card=null;

        String cardName,paymentGateway;
        LocalDateTime inceptionDate;
        boolean isActive;

        System.out.print("Card Name: ");cardName=in.next();
        inceptionDate= Main.currentDateTime;
        System.out.print("Is card Active: ");isActive=in.nextBoolean();
        System.out.print("Card Payment Gateway: ");paymentGateway=in.next();

        switch (cardCode){
            case 1:{
                double withdrawalLimit;

                System.out.print("Debit Card Withdrawal Limit: ");withdrawalLimit=in.nextDouble();

                card = new DebitCard(cardName,inceptionDate,isActive,paymentGateway,withdrawalLimit);

                break;
            }
            case 2:{
                int interestFreeCreditDays;
                double rateOfInterest;

                System.out.print("Credit Card Interest Free Credit Days: ");interestFreeCreditDays=in.nextInt();
                System.out.print("Credit Card Rate Of Interest: ");rateOfInterest=in.nextDouble();

                card = new CreditCard(cardName,inceptionDate,isActive,paymentGateway,interestFreeCreditDays,rateOfInterest);

                break;
            }
            case 3:{
                int interestFreeCreditDays;
                String merchantName;
                double merchantOfferPercentage,rateOfInterest;

                System.out.print("Credit Card Interest Free Credit Days: ");interestFreeCreditDays=in.nextInt();
                System.out.print("Credit Card Rate Of Interest: ");rateOfInterest=in.nextDouble();
                System.out.print("Co-Branded Credit Card Merchant Name: ");merchantName=in.next();
                System.out.print("Co-Branded Credit Card Merchant Offer Percentage: ");merchantOfferPercentage=in.nextInt();

                card = new CoBrandedCreditCard(cardName,inceptionDate,isActive,paymentGateway,interestFreeCreditDays,rateOfInterest,merchantName,merchantOfferPercentage);

                break;
            }
        }

        if(!isValidCard(card)) card = null;

        return card;
    }

    private boolean isValidCard(Card card){
        boolean isValidCard=true;

        if(card instanceof CoBrandedCreditCard) isValidCard = isValidCoBrandedCreditCard((CoBrandedCreditCard) card);
        else if(card instanceof CreditCard) isValidCard = isValidCreditCard((CreditCard) card);
        else if(card instanceof DebitCard) isValidCard  = isValidDebitCard((DebitCard) card);

        return isValidCard;
    }
    private boolean isValidCreditCard(CreditCard creditCard){
        boolean isValidCreditCard=true;

        return isValidCreditCard;
    }
    private boolean isValidDebitCard(DebitCard debitCard){
        boolean isValidDebitCard=true;

        return isValidDebitCard;
    }
    private boolean isValidCoBrandedCreditCard(CoBrandedCreditCard coBrandedCreditCard){
        boolean isValidCoBrandedCreditCard=true;

        return isValidCoBrandedCreditCard;
    }
}
