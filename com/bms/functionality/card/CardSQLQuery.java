package com.bms.functionality.card;

public class CardSQLQuery {
    static final String INSERT_CARD_MASTER_QUERY="INSERT INTO Card_Master(card_Name,inception_Date,payment_Gateway,is_Active) VALUE (?,CURRENT_TIMESTAMP,?,TRUE)";
    static final String INSERT_CO_BRAND_CARD_MASTER_QUERY="INSERT INTO Co_Branded_Credit_Card_Master(card_Id,merchant_Name,merchant_Offer_Percentage,interest_Free_Credit_Days,rate_Of_Interest) VALUE (?,?,?,?,?)";
    static final String INSERT_CREDIT_CARD_MASTER_QUERY="INSERT INTO Credit_Card_Master(card_Id,interest_Free_Credit_Days,rate_Of_Interest) VALUE (?,?,?)";
    static final String INSERT_DEBIT_CARD_MASTER_QUERY="INSERT INTO Debit_Card_Master(card_Id,withdrawal_Limit) VALUE(?,?)";
    static final String UPDATE_DEACTIVATE_MASTER_CARD = "UPDATE Card_Master SET is_Active='False' WHERE Id=?";
    static final String SELECT_ACTIVE_DEBIT_CARD = "SELECT Id,card_Name FROM Card_Master JOIN DEBIT_CARD_MASTER ON card_Id=Id WHERE is_Active=TRUE";
    static final String SELECT_ACTIVE_CREDIT_CARD = "SELECT Id,card_Name FROM Card_Master JOIN CREDIT_CARD_MASTER ON card_Id=Id WHERE is_Active=TRUE";
    static final String SELECT_ACTIVE_CO_BRAND_CREDIT_CARD = "SELECT Id,card_Name FROM Card_Master JOIN CO_BRANDED_CREDIT_CARD_MASTER ON card_Id=Id WHERE is_Active=TRUE";
    static final String SELECT_CREDIT_CARD_MASTER_BY_ID="SELECT card_Name,inception_Date,payment_Gateway,interest_Free_Credit_Days,rate_Of_Interest FROM Card_MASTER JOIN CREDIT_CARD_MASTER ON Id=card_Id WHERE Id=?";
    static final String SELECT_DEBIT_CARD_MASTER_BY_ID="SELECT card_Name,inception_Date,payment_Gateway,withdrawal_Limit FROM Card_MASTER JOIN DEBIT_CARD_MASTER ON Id=card_Id WHERE Id=?";
    static final String SELECT_CO_BRAND_CREDIT_CARD_MASTER_BY_ID="SELECT card_Name,inception_Date,payment_Gateway,interest_Free_Credit_Days,rate_Of_Interest,merchant_Name,merchant_Offer_Percentage FROM Card_MASTER JOIN CO_BRANDED_CREDIT_CARD_MASTER ON Id=card_Id WHERE Id=?";
    static final String INSERT_CARD="INSERT INTO Card(account_Number,card_Holder_Name,inception_Date,valid_from,expiry_Date,payment_Gateway,CVV,pin_Number,is_Active) VALUE(?,?,?,CURRENT_TIMESTAMP,?,?,?,?,TRUE)";
    static final String INSERT_CREDIT_CARD="INSERT INTO Credit_Card(card_Id,interest_Free_Credit_Days,rate_Of_Interest) value(?,?,?)";
    static final String INSERT_DEBIT_CARD="INSERT INTO Debit_Card(card_Id,withdrawal_Limit) VALUE(?,?)";
    static final String INSERT_CO_BRAND_CREDIT_CARD="INSERT INTO CO_BRANDED_CREDIT_CARD(card_Id,merchant_Name,merchant_Offer_Percentage,interest_Free_Credit_Days,rate_Of_Interest) VALUE(?,?,?,?,?)";
    static final String UPDATE_DEACTIVATE_CARD ="UPDATE CARD SET is_Active=FALSE WHERE card_Number=?";
    static final String SELECT_ACTIVE_CARD="SELECT card_Number FROM CARD WHERE account_Number=? AND is_Active=TRUE";
    static final String SELECT_ACTIVE_LIVE_CARD_QUERY="SELECT card_Number,account_Number,valid_from,expiry_date,payment_Gateway,Credit_Card.interest_free_credit_days As freeCreditDays ,Credit_Card.rate_of_interest roi,merchant_name,merchant_offer_percentage,Co_Branded_Credit_Card.interest_free_credit_days AS coFreeCreditDay,Co_Branded_Credit_Card.rate_of_interest AS coRoi,withdrawal_limit FROM Card LEFT JOIN Credit_Card ON Credit_Card.card_Id=card_Number LEFT JOIN Co_Branded_Credit_Card ON Co_Branded_Credit_Card.card_Id=card_Number LEFT JOIN Debit_Card ON Debit_Card.card_Id=card_Number WHERE is_active=TRUE AND account_Number=? ORDER BY expiry_Date";
    static final String SELECT_CVV="SELECT FLOOR(rand() * 900 + 100)";
    static final String SELECT_PIN="SELECT FLOOR(rand() * 9000 + 1000)";
}
