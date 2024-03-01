package com.bms.functionality.card;

import com.bms.Main;
import com.bms.functionality.MySQLConnection;
import com.bms.functionality.account.AccountLogic;
import com.bms.transaction.card.Card;
import com.bms.transaction.card.CoBrandedCreditCard;
import com.bms.transaction.card.CreditCard;
import com.bms.transaction.card.DebitCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

public class CardDataLogic {
    boolean registerCardRecord(Card card){
        boolean isCardInserted;

        if(card instanceof CoBrandedCreditCard) isCardInserted=insertCoBrandCreditCard((CoBrandedCreditCard) card);
        else if(card instanceof CreditCard) isCardInserted=insertCreditCard((CreditCard) card);
        else isCardInserted=insertDebitCard((DebitCard) card);

        return isCardInserted;
    }
    private int generateRandomCVV(Connection connection){
        int cvv = 0;

        try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.SELECT_CVV)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                cvv=rs.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return cvv;
    }
    private int generateRandomPinNumber(Connection connection){
        int pinNumber = 0;

        try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.SELECT_PIN)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                pinNumber=rs.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return pinNumber;
    }
    private boolean insertCard(Connection connection,Card card){
        boolean isCardInserted=false;
        int cvv,pinNumber;
        double cardNumber,accountNumber;

        AccountLogic accountLogic  = new AccountLogic();

        cvv=generateRandomCVV(connection);
        pinNumber=generateRandomPinNumber(connection);
        accountNumber=accountLogic.getAccountNumberOnUserRequest(connection);

        if(accountNumber!=0 && pinNumber!=0 && cvv!=0){
            try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.INSERT_CARD)){
                ps.setDouble(1,accountNumber);
                ps.setString(2,card.getCardHolderName());
                ps.setString(3, Main.dbDateTimeFormat.format(card.getInceptionDate()));
                ps.setString(4,Main.dbDateTimeFormat.format(Main.currentDateTime.plusYears(8)));
                ps.setString(5,card.getPaymentGateway());
                ps.setInt(6,cvv);
                ps.setInt(7,pinNumber);
                int rs=ps.executeUpdate();
                if(rs>0 && ps.getGeneratedKeys().next()){
                    cardNumber=ps.getGeneratedKeys().getDouble(1);
                    card.setCardNumber(cardNumber);
                    isCardInserted=true;
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }

        return isCardInserted;
    }
    private boolean insertCreditCard(CreditCard creditCard){
        boolean isCreditCardInserted=false;

        try(Connection connection = MySQLConnection.connect()){
            if(connection !=null){
                connection.setAutoCommit(false);
                if(insertCard(connection,creditCard)){
                    try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.INSERT_CREDIT_CARD)){
                        ps.setDouble(1,creditCard.getCardNumber());
                        ps.setInt(2,creditCard.getInterestFreeCreditDays());
                        ps.setDouble(3,creditCard.getRateOfInterest());
                        int rs = ps.executeUpdate();
                        isCreditCardInserted = rs>0;
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return isCreditCardInserted;
    }
    private boolean insertDebitCard(DebitCard debitCard){
        boolean isDebitCardInserted=false;

        try(Connection connection = MySQLConnection.connect()){
            if(connection !=null){
                connection.setAutoCommit(false);
                if(insertCard(connection,debitCard)){
                    try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.INSERT_DEBIT_CARD)){
                        ps.setDouble(1,debitCard.getCardNumber());
                        ps.setDouble(2,debitCard.getWithdrawalLimit());
                        int rs = ps.executeUpdate();
                        isDebitCardInserted = rs>0;
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return isDebitCardInserted;
    }
    private boolean insertCoBrandCreditCard(CoBrandedCreditCard coBrandCreditCard){
        boolean isCoBrandCreditCardInserted=false;

        try(Connection connection = MySQLConnection.connect()){
            if(connection !=null){
                connection.setAutoCommit(false);
                if(insertCard(connection,coBrandCreditCard)){
                    try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.INSERT_CO_BRAND_CREDIT_CARD)){
                        ps.setDouble(1,coBrandCreditCard.getCardNumber());
                        ps.setString(2,coBrandCreditCard.getMerchantName());
                        ps.setDouble(3,coBrandCreditCard.getMerchantOfferPercentage());
                        int rs = ps.executeUpdate();
                        isCoBrandCreditCardInserted = rs>0;
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return isCoBrandCreditCardInserted;
    }
    CreditCard getCreditCardMasterById(){
        CreditCard creditCard = null;

        try(Connection connection = MySQLConnection.connect()){
            if(connection !=null){
                connection.setAutoCommit(false);
                try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.SELECT_CREDIT_CARD_MASTER_BY_ID)){
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        creditCard=new CreditCard(rs.getString(1), rs.getDate(2).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), true, rs.getString(3), rs.getInt(4),rs.getDouble(5));
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return creditCard;
    }
    DebitCard getDebitCardMasterById(){
        DebitCard debitCard = null;

        try(Connection connection = MySQLConnection.connect()){
            if(connection !=null){
                connection.setAutoCommit(false);
                try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.SELECT_DEBIT_CARD_MASTER_BY_ID)){
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        debitCard=new DebitCard(rs.getString(1), rs.getDate(2).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), true, rs.getString(3),rs.getDouble(4));
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return debitCard;
    }
    CoBrandedCreditCard getCoBrandCreditCardMasterById(){
        CoBrandedCreditCard coBrandCreditCard=null;

        try(Connection connection = MySQLConnection.connect()){
            if(connection !=null){
                connection.setAutoCommit(false);
                try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.SELECT_CO_BRAND_CREDIT_CARD_MASTER_BY_ID)){
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        coBrandCreditCard=new CoBrandedCreditCard(rs.getString(1), rs.getDate(2).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), true, rs.getString(3),rs.getInt(4),rs.getDouble(5),rs.getString(6),rs.getDouble(7));
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return coBrandCreditCard;
    }
    void displayAvailableDebitCard(){
        boolean isRecordPresent=false;

        try(Connection connection = MySQLConnection.connect()){
            if(connection !=null){
                connection.setAutoCommit(false);
                try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.SELECT_ACTIVE_DEBIT_CARD)){
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        isRecordPresent=true;
                        System.out.println("Id: "+rs.getInt(1)+" Name: "+rs.getString(2));
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        if(!isRecordPresent) System.out.println("No Record Found In Debit Card Master.");
    }
    void displayAvailableCreditCard(){
        boolean isRecordPresent=false;

        try(Connection connection = MySQLConnection.connect()){
            if(connection !=null){
                connection.setAutoCommit(false);
                try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.SELECT_ACTIVE_CREDIT_CARD)){
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        isRecordPresent=true;
                        System.out.println("Id: "+rs.getInt(1)+" Name: "+rs.getString(2));
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        if(!isRecordPresent) System.out.println("No Record Found In Credit Card Master.");
    }
    void displayAvailableCoBrandedCreditCard(){
        boolean isRecordPresent=false;

        try(Connection connection = MySQLConnection.connect()){
            if(connection !=null){
                connection.setAutoCommit(false);
                try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.SELECT_ACTIVE_CO_BRAND_CREDIT_CARD)){
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        isRecordPresent=true;
                        System.out.println("Id: "+rs.getInt(1)+" Name: "+rs.getString(2));
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        if(!isRecordPresent) System.out.println("No Record Found In Co Branded Credit Card Master.");
    }
    boolean deactivateMasterCard(int cardId){
        boolean isCardDeactivated=false;

        try(Connection connection = MySQLConnection.connect()){
            if(connection !=null){
                connection.setAutoCommit(false);
                try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.UPDATE_DEACTIVATE_CARD)){
                    ps.setInt(1,cardId);
                    int rs=ps.executeUpdate();
                    if(rs>0){
                        isCardDeactivated=true;
                        connection.commit();
                    }else{
                        connection.rollback();
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return isCardDeactivated;
    }
    boolean insertNewDebitCard(DebitCard debitCard){
        boolean isNewDebitCardInserted=false;
        int cardId = 0;

        try(Connection connection = MySQLConnection.connect()) {
            if(connection != null){
                connection.setAutoCommit(false);
                try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.INSERT_CARD_MASTER_QUERY)){
                    ps.setString(1, debitCard.getCardName());
                    ps.setString(2, debitCard.getPaymentGateway());
                    int rs = ps.executeUpdate();
                    if(rs>0 && ps.getGeneratedKeys().next()){
                        cardId = ps.getGeneratedKeys().getInt(1);
                    }
                }
                if(cardId>0){
                    try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.INSERT_DEBIT_CARD_MASTER_QUERY)){
                        ps.setInt(1,cardId);
                        ps.setDouble(2,debitCard.getWithdrawalLimit());
                        int rs = ps.executeUpdate();
                        if(rs>0){
                            isNewDebitCardInserted=true;
                            connection.commit();
                        }else{
                            connection.rollback();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return isNewDebitCardInserted;
    }
    boolean insertNewCreditCard(CreditCard creditCard){
        boolean isNewCreditCardInserted=false;
        int cardId = 0;

        try(Connection connection = MySQLConnection.connect()) {
            if(connection != null){
                connection.setAutoCommit(false);
                try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.INSERT_CARD_MASTER_QUERY)){
                    ps.setString(1, creditCard.getCardName());
                    ps.setString(2, creditCard.getPaymentGateway());
                    int rs = ps.executeUpdate();
                    if(rs>0 && ps.getGeneratedKeys().next()){
                        cardId = ps.getGeneratedKeys().getInt(1);
                    }
                }
                if(cardId>0){
                    try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.INSERT_CREDIT_CARD_MASTER_QUERY)){
                        ps.setInt(1,cardId);
                        ps.setInt(1,creditCard.getInterestFreeCreditDays());
                        ps.setDouble(2,creditCard.getRateOfInterest());
                        int rs = ps.executeUpdate();
                        if(rs>0){
                            isNewCreditCardInserted=true;
                            connection.commit();
                        }else{
                            connection.rollback();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isNewCreditCardInserted;
    }
    boolean insertNewCoBrandedCreditCard(CoBrandedCreditCard coBrandCreditCard){
        boolean isNewCoBrandCreditCardInserted=false;
        int cardId = 0;

        try(Connection connection = MySQLConnection.connect()) {
            if(connection != null){
                connection.setAutoCommit(false);
                try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.INSERT_CARD_MASTER_QUERY)){
                    ps.setString(1, coBrandCreditCard.getCardName());
                    ps.setString(2, coBrandCreditCard.getPaymentGateway());
                    int rs = ps.executeUpdate();
                    if(rs>0 && ps.getGeneratedKeys().next()){
                        cardId = ps.getGeneratedKeys().getInt(1);
                    }
                }
                if(cardId>0){
                    try(PreparedStatement ps = connection.prepareStatement(CardSQLQuery.INSERT_CO_BRAND_CARD_MASTER_QUERY)){
                        ps.setInt(1,cardId);
                        ps.setString(2,coBrandCreditCard.getMerchantName());
                        ps.setDouble(3,coBrandCreditCard.getMerchantOfferPercentage());
                        ps.setInt(4,coBrandCreditCard.getInterestFreeCreditDays());
                        ps.setDouble(5,coBrandCreditCard.getRateOfInterest());
                        int rs = ps.executeUpdate();
                        if(rs>0){
                            isNewCoBrandCreditCardInserted=true;
                            connection.commit();
                        }else{
                            connection.rollback();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isNewCoBrandCreditCardInserted;
    }
}
