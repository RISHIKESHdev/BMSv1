package com.bms.functionality.branch;

public class BranchSQLQuery {
    static final String INSERT_BRANCH_QUERY="INSERT INTO Branch(branch_Name,mobile_Number,bank_Id,address_Id,geoLocation_Id) VALUE(?,?,?,?,?)";
    static final String UPDATE_BRANCH_IFSC_QUERY="UPDATE Branch SET IFSC_Code=? WHERE branch_Id=?";
    static final String SELECT_ALL_BRANCH_DTLS="SELECT branch_Id,IFSC_Code,branch_Name FROM Branch";
    static final String SELECT_CHECK_BRANCH_DTLS="SELECT COUNT(*) FROM Branch WHERE branch_Id = ?";
}
