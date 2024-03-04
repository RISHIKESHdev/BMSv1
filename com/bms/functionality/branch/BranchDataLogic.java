package com.bms.functionality.branch;

import com.bms.bank.Branch;
import com.bms.functionality.CommonConstant;
import com.bms.functionality.MySQLConnection;
import com.bms.functionality.account.AccountLogic;
import com.bms.functionality.navigate.NavigateLogic;
import com.bms.functionality.profile.ProfileLogic;

import java.sql.*;

class BranchDataLogic {
    boolean viewAllBranchId(Connection connection){
        boolean isRecordPresent=false;

        try(PreparedStatement ps = connection.prepareStatement(BranchSQLQuery.SELECT_ALL_BRANCH_DTLS)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                isRecordPresent=true;
                System.out.println("Branch Id: "+rs.getInt("branch_Id"));
                System.out.println("IFSC Code: "+rs.getString("IFSC_Code"));
                System.out.println("Branch Name: "+rs.getString("branch_Name")+"\n");
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isRecordPresent;
    }
    boolean isAccountBranchCustomerValid(Connection connection,int branchId,double CIFNumber,double accountNumber){
        boolean isValid=false;

        try(PreparedStatement ps=connection.prepareStatement(BranchSQLQuery.CHECK_ACCOUNT_BRANCH_CIF_QUERY)){
            ps.setDouble(1,CIFNumber);
            ps.setInt(2,branchId);
            ps.setDouble(3,accountNumber);
            ResultSet rs= ps.executeQuery();
            if(rs.next()){
                if(rs.getInt(1)>0){
                    isValid=true;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return isValid;
    }
    boolean updateCustomerBranchMap(){
        boolean isCustomerBranchUpdated=false,isValidData=false;
        double CIFNumber,accountNumber;
        int branchId;

        BranchLogic branch = new BranchLogic();
        ProfileLogic profile =new ProfileLogic();
        AccountLogic account = new AccountLogic();

        try(Connection connection = MySQLConnection.connect()){
            if(connection!=null){
                connection.setAutoCommit(false);
                branchId=branch.getBranchId(connection);
                CIFNumber=profile.checkAndGetCIFNumber(connection);
                accountNumber=account.getAccountNumberOnUserRequest(connection);
                if(branchId!=0 && CIFNumber!=0 && accountNumber!=0 && isAccountBranchCustomerValid(connection,branchId,CIFNumber,accountNumber)){
                    isValidData=true;
                }
                if(isValidData){
                    try(PreparedStatement ps=connection.prepareStatement(BranchSQLQuery.UPDATE_CUSTOMER_BRANCH_QUERY)){
                        System.out.println("Select New Branch Id: ");
                        branchId=branch.getBranchId(connection);
                        ps.setInt(1,branchId);
                        ps.setDouble(2,accountNumber);

                        int rs=ps.executeUpdate();
                        isCustomerBranchUpdated=rs>0;
                    }
                }
                if(isCustomerBranchUpdated) connection.commit();
                else connection.rollback();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return isCustomerBranchUpdated;
    }
    boolean updateEmployeeBranchMap(double employeeId){
        boolean isRecordUpdated=false;
        int branchId;

        BranchLogic branch = new BranchLogic();

        try(Connection connection = MySQLConnection.connect()){
            if(connection!=null){
                connection.setAutoCommit(false);
                branchId=branch.getBranchId(connection);
                if(branchId!=0){
                    try(PreparedStatement ps = connection.prepareStatement(BranchSQLQuery.UPDATE_EMPLOYEE_BRANCH_MAP_QUERY)){
                        ps.setInt(1,branchId);
                        ps.setDouble(2,employeeId);
                        int rs=ps.executeUpdate();
                        if (rs > 0) {
                            connection.commit();
                            isRecordUpdated=true;
                        }else{
                            connection.rollback();
                        }
                    }
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return  isRecordUpdated;
    }
    boolean checkBranchIdPresent(Connection connection,int branchId){
        boolean isRecordPresent=false;

        try(PreparedStatement ps = connection.prepareStatement(BranchSQLQuery.SELECT_CHECK_BRANCH_DTLS)){
            ps.setInt(1,branchId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                    isRecordPresent=true;
                    System.out.println("Selected Branch Details:-");
                    System.out.println("Branch Id: "+rs.getInt("branch_Id"));
                    System.out.println("IFSC Code: "+rs.getString("IFSC_Code"));
                    System.out.println("Branch Name: "+rs.getString("branch_Name")+"\n");
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isRecordPresent;
    }

    boolean insertBranchRecord(Branch branch){
        int addressId,geoLocationId,branchId = 0,is,us=0;
        boolean isBranchInserted=false;
        String IFSCCode="";

        NavigateLogic navigate = new NavigateLogic();

        try(Connection connection = MySQLConnection.connect()) {
            if(connection != null){
                connection.setAutoCommit(false);
                addressId=navigate.getIdOnInsertAddressRecord(connection);
                geoLocationId=navigate.getIdOnInsertGeoLocationRecord(connection);
                try(PreparedStatement ps = connection.prepareStatement(BranchSQLQuery.INSERT_BRANCH_QUERY, Statement.RETURN_GENERATED_KEYS)){
                    ps.setString(1,branch.getBranchName());
                    ps.setLong(2,branch.getMobileNumber());
                    ps.setInt(3, CommonConstant.BANK_IDENTIFICATION_NUMBER);
                    ps.setInt(4,addressId);
                    ps.setInt(5,geoLocationId);
                    is = ps.executeUpdate();
                    if(is>0){
                        ResultSet rSet = ps.getGeneratedKeys();
                        rSet.next();
                        branchId = rSet.getInt(1);
                        IFSCCode= CommonConstant.BANK_CODE +String.format("%1$" + 6 + "s", branchId).replace(" ", "0");
                        branch.setIFSCCode(IFSCCode);
                    }
                }
                if(is>0){
                    try(PreparedStatement ps = connection.prepareStatement(BranchSQLQuery.UPDATE_BRANCH_IFSC_QUERY)){
                        ps.setString(1,IFSCCode);
                        ps.setInt(2,branchId);
                        us = ps.executeUpdate();
                    }
                }
                if(is==1 && us==1){
                    connection.commit();
                    isBranchInserted=true;
                }else{
                    connection.rollback();
                }
            }
        } catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isBranchInserted;
    }
}
