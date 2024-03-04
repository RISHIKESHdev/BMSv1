package com.bms.functionality;

public class CommonConstant {
    //public static final String ADDRESS_LINE_REGEX = "^[a-zA-Z0-9]+(?:\\s[a-zA-Z0-9]+)+$";
    public static final String AT_LEAST_ONE_STRING_REGEX = "^[\\sa-zA-Z0-9]+$";
    public static final String INDIAN_PIN_CODE_REGEX = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$";
    public static final String EMAIL_ID_REGEX = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    public static final String GENDER_REGEX ="(?:male|Male|female|Female|FEMALE|MALE)$";
    public static final String PASSWORD_REGEX ="^(?=(.*[a-z]){3,})(?=(.*[A-Z]){2,})(?=(.*[0-9]){2,})(?=(.*[!@#$%^&*()\\-__+.]){1,}).{8,}$";
    public static final String MOBILE_NO_REGEX ="^(\\+\\d{1,3}[- ]?)?\\d{10}$";
    public static final String PAN_REGEX ="^[A-Z]{5}[0-9]{4}[A-Z]{1}$";
//    public static final String BOOLEAN_REGEX="^(true|false)$";
    public static final double INITIAL_CREDIT_SCORE=100.0;
    public static final double MIN_ACCOUNT_BALANCE=500.0;
    public static final double MIN_SAVING_ACCOUNT_BALANCE=2000.0;
    public static final double INITIAL_OVER_DRAFT_LIMIT=100000.0;
    public static final double MINIMUM_LOAN_AMOUNT=500000.0;
    public static final int BANK_IDENTIFICATION_NUMBER=245;
    public static final String BANK_NAME="State Bank Of International India.";
    public static final String BANK_CODE="SBII";
    public static final String BANK_TYPE="Public Sector";
}
