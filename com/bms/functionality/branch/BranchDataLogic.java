package com.bms.functionality.branch;

import com.bms.bank.Branch;
import com.bms.functionality.CommonConstant;
import com.bms.functionality.MySQLConnection;
import com.bms.functionality.navigate.NavigateLogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    boolean checkBranchIdPresent(Connection connection,int branchId){
        boolean isRecordPresent=false;

        try(PreparedStatement ps = connection.prepareStatement(BranchSQLQuery.SELECT_CHECK_BRANCH_DTLS)){
            ps.setInt(1,branchId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getInt(1)>0){
                    isRecordPresent=true;
                    System.out.println("Selected Branch Details:-");
                    System.out.println("Branch Id: "+rs.getInt("branch_Id"));
                    System.out.println("IFSC Code: "+rs.getString("IFSC_Code"));
                    System.out.println("Branch Name: "+rs.getString("branch_Name")+"\n");
                }
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
                try(PreparedStatement ps = connection.prepareStatement(BranchSQLQuery.INSERT_BRANCH_QUERY)){
                    ps.setString(1,branch.getBranchName());
                    ps.setLong(2,branch.getMobileNumber());
                    ps.setInt(3, CommonConstant.BANK_IDENTIFICATION_NUMBER);
                    ps.setInt(4,addressId);
                    ps.setInt(4,geoLocationId);
                    is = ps.executeUpdate();
                    if(is>0 && ps.getGeneratedKeys().next()){
                        branchId = ps.getGeneratedKeys().getInt(1);
                        IFSCCode= CommonConstant.BANK_IDENTIFICATION_NUMBER +String.format("%1$" + 6 + "s", branchId).replace(" ", "0");
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
