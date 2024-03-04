package com.bms.functionality.profile;

public class ProfileSQLQuery {
    static final String INSERT_USER_QUERY="INSERT INTO User(first_Name,middle_Name,last_Name,address_Id,email_Id,gender,password,age,mobile_Number) Value(?,?,?,?,?,?,?,?,?)";
    static final String INSERT_CUSTOMER_QUERY ="INSERT INTO Customer(user_Id,CKYC_Verification_Document,CKYC_Verification_Id,PAN_Number) Value(?,?,?,?)";
    static final String INSERT_EMPLOYEE_QUERY ="INSERT INTO Employee(user_Id,employee_Designation,employee_CTC,year_Of_Experience,is_Active) Value(?,?,?,?,?)";
    static final String INSERT_ADMIN_QUERY ="INSERT INTO Employee(user_Id,is_Active) Value(?,?)";
    static final String INSERT_NOMINEE_QUERY ="INSERT INTO Nominee(account_Number,first_Name,middle_Name,last_Name,email_Id,gender,age,mobile_Number,CKYC_Verification_Document,CKYC_Verification_Id) value(?,?,?,?,?,?,?,?,?,?)";
    static final String SELECT_A_CUSTOMER_QUERY ="SELECT CIF_Number,USER.Id,first_Name,middle_Name,last_Name,email_Id,gender,age,mobile_Number,Address_Id,address_Line_One,address_Line_Two,address_Line_Three,landmark,city,state,country,pinCode,CKYC_Verification_Document,CKYC_Verification_Id,password,PAN_Number FROM CUSTOMER JOIN USER JOIN ADDRESS ON USER.Id = CUSTOMER.user_Id and ADDRESS.id=USER.address_id WHERE CUSTOMER.CIF_Number=?";
    static final String CHECK_A_CUSTOMER_QUERY ="SELECT COUNT(*) FROM CUSTOMER WHERE CUSTOMER.CIF_Number=?";
    static final String SELECT_COUNT_CUSTOMER_QUERY ="SELECT Count(*) FROM CUSTOMER WHERE CUSTOMER.CIF_Number=?";
    static final String INSERT_EMPLOYEE_BRANCH_MAP="INSERT INTO Employee_Branch_Map(employee_Id,branch_Id) VALUE(?,?)";
    static final String SELECT_ADMIN_ON_LOGIN="SELECT first_Name,middle_Name,last_Name,email_Id,gender,password,age,mobile_Number,address_Line_One,address_Line_Two,address_Line_Three,landmark,city,state,country,pinCode,is_Active FROM Address JOIN User JOIN Admin ON User.address_Id=Address.Address_Id AND User.Id=Admin.user_Id WHERE Admin.is_Active=True and User.email_Id=? AND User.password=?";
    static final String SELECT_CUSTOMER_ON_LOGIN="SELECT first_Name,middle_Name,last_Name,email_Id,gender,password,age,mobile_Number,address_Line_One,address_Line_Two,address_Line_Three,landmark,city,state,country,pinCode,CIF_Number,CKYC_Verification_Document,CKYC_Verification_Id,PAN_Number FROM Address JOIN User JOIN Customer ON User.address_Id=Address.Address_Id AND User.Id=Customer.user_Id WHERE User.email_Id=? AND User.password=?";
    static final String SELECT_EMPLOYEE_ON_LOGIN="SELECT first_Name,middle_Name,last_Name,email_Id,gender,password,age,mobile_Number,address_Line_One,address_Line_Two,address_Line_Three,landmark,city,state,country,pinCode,Employee_Id,employee_Designation,employee_CTC,year_Of_Experience,is_Active FROM Address JOIN User JOIN Employee ON User.address_Id=Address.Address_Id AND User.Id=Employee.user_Id WHERE Employee.is_Active=True and User.email_Id=? AND User.password=?";
    static final String SELECT_NOMINEE_BY_ID_QUERY="SELECT Id,first_Name,last_Name,mobile_Number FROM Nominee WHERE Id=?";
    static final String DELETE_NOMINEE_QUERY="DELETE FROM Nominee WHERE Id=? AND account_Number=?";
    static final String UPDATE_EMPLOYEE_DEACTIVATE_QUERY="UPDATE Employee SET is_Active=False WHERE Employee.Employee_Id=?";
}