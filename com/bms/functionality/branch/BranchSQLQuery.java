package com.bms.functionality.branch;

public class BranchSQLQuery {
    static final String INSERT_BRANCH_QUERY="INSERT INTO Branch(branch_Name,mobile_Number,bank_Id,address_Id,geoLocation_Id) VALUE(?,?,?,?,?)";
    static final String UPDATE_BRANCH_IFSC_QUERY="UPDATE Branch SET IFSC_Code=? WHERE branch_Id=?";
    static final String SELECT_ALL_BRANCH_DTLS="SELECT branch_Id,IFSC_Code,branch_Name FROM Branch";
    static final String SELECT_CHECK_BRANCH_DTLS="SELECT branch_Id,IFSC_Code,branch_Name FROM Branch WHERE branch_Id = ?";
    static final String UPDATE_EMPLOYEE_BRANCH_MAP_QUERY="UPDATE Employee_Branch_Map SET branch_Id=? WHERE employee_Id=?";
    static final String CHECK_ACCOUNT_BRANCH_CIF_QUERY="select COUNT(*) FROM Account JOIN Customer_Account_Map ON Account.account_Number=Customer_Account_Map.account_Number WHERE Customer_Account_Map.CIFNumber=? AND Account.branch_Id=? AND Account.account_Number=?";
    static final String UPDATE_CUSTOMER_BRANCH_QUERY="UPDATE Account SET branch_Id=? WHERE Account.account_Number=?";
}
