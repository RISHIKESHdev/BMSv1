package com.bms.functionality.profile;

public class ProfileSQLQuery {
    static final String INSERT_USER_QUERY="INSERT INTO User(first_Name,middle_Name,last_Name,address_Id,email_Id,gender,password,age,mobile_Number) Value(?,?,?,?,?,?,?,?,?)";
    static final String INSERT_CUSTOMER_QUERY ="INSERT INTO Customer(user_Id,CKYC_Verification_Document,CKYC_Verification_Id,PAN_Number) Value(?,?,?,?)";
    static final String INSERT_EMPLOYEE_QUERY ="INSERT INTO Employee(user_Id,employee_Designation,employee_CTC,year_Of_Experience,is_Active) Value(?,?,?,?,?)";
    static final String INSERT_ADMIN_QUERY ="INSERT INTO Employee(user_Id,is_Active) Value(?,?)";
    static final String INSERT_NOMINEE_QUERY ="INSERT INTO Nominee(account_Number,first_Name,middle_Name,last_Name,email_Id,gender,age,mobile_Number,CKYC_Verification_Document,CKYC_Verification_Id) value(?,?,?,?,?,?,?,?,?,?)";
    static final String SELECT_A_CUSTOMER_QUERY ="SELECT CIF_Number,USER.Id,first_Name,middle_Name,last_Name,email_Id,gender,age,mobile_Number,Address_Id,address_Line_One,address_Line_Two,address_Line_Three,landmark,city,state,country,pinCode,CKYC_Verification_Document,CKYC_Verification_Id,password,PAN_Number FROM CUSTOMER JOIN USER JOIN ADDRESS ON USER.Id = CUSTOMER.user_Id and ADDRESS.id=USER.address_id WHERE CUSTOMER.CIF_Number=?";
    static final String SELECT_COUNT_CUSTOMER_QUERY ="SELECT Count(*) FROM CUSTOMER WHERE CUSTOMER.CIF_Number=?";
    static final String INSERT_EMPLOYEE_BRANCH_MAP="INSERT INTO Employee_Branch_Map(employee_Id,branch_Id) VALUE(?,?)";
}