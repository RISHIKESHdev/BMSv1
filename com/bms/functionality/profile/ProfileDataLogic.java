package com.bms.functionality.profile;

import com.bms.functionality.MySQLConnection;
import com.bms.functionality.branch.BranchLogic;
import com.bms.functionality.navigate.NavigateLogic;
import com.bms.people.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDataLogic {
    private boolean insertUserRecord(Connection connection, User user){
        int addressId;
        boolean isUserRecordInserted=false;

        NavigateLogic navigate = new NavigateLogic();

        addressId=navigate.getIdOnInsertAddressRecord(connection);
        try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.INSERT_USER_QUERY)){
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getMiddleName());
            ps.setString(3, user.getLastName());
            ps.setInt(4, addressId);
            ps.setString(5, user.getEmailId());
            ps.setString(6, user.getGender());
            ps.setString(7, user.getPassword());
            ps.setInt(8, user.getAge());
            ps.setString(9, user.getMobileNumber());
            int rs = ps.executeUpdate();
            isUserRecordInserted=(rs>0);
            if(isUserRecordInserted && ps.getGeneratedKeys().next()){
                user.setUserId(ps.getGeneratedKeys().getInt(1));
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isUserRecordInserted;
    }

    boolean insertCustomer(Connection connection,Customer customer){
        int userId;
        double CIFNumber;
        boolean isCustomerInserted=false;

        if(insertUserRecord(connection,customer)){
            userId=customer.getUserId();
            try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.INSERT_CUSTOMER_QUERY)){
                ps.setInt(1,userId);
                ps.setString(2,customer.getCKYCVerificationDocument());
                ps.setString(3,customer.getCKYCVerificationId());
                ps.setString(4, customer.getPANNumber());
                int rs = ps.executeUpdate();
                if(rs>0 && ps.getGeneratedKeys().next()){
                    CIFNumber = ps.getGeneratedKeys().getDouble(1);
                    customer.setCIFNumber(CIFNumber);
                }
            }catch(NullPointerException | SQLException e){
                System.out.println(e.getMessage());
            }
        }

        return isCustomerInserted;
    }
    boolean isCustomerPresent(Connection connection,double CIFNumber){
        boolean isCustomerPresent=false;

        try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.SELECT_COUNT_CUSTOMER_QUERY)){
            ps.setDouble(1,CIFNumber);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                isCustomerPresent = true;
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isCustomerPresent;
    }
    Customer selectCustomerByCIFNumber(Connection connection,double CIFNumber){
        Customer customer = null;

        try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.SELECT_A_CUSTOMER_QUERY)){
            ps.setDouble(1,CIFNumber);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                customer = new Customer(rs.getString("first_Name"),rs.getString("middle_Name"),rs.getString("last_Name"),rs.getString("email_Id"),rs.getString("gender"),rs.getInt("age"),rs.getString("mobile_Number"),rs.getString("password"),rs.getString("PAN_Number"),rs.getString("CKYC_Verification_Document"),rs.getString("CKYC_Verification_Id"));
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return customer;
    }
    boolean insertEmployee(Employee employee){
        int userId,branchId,rs1=0;
        double employeeId = 0;
        boolean isEmployeeInserted=false;

        try(Connection connection = MySQLConnection.connect()) {
            if(connection != null){
                connection.setAutoCommit(false);
                branchId=new BranchLogic().getBranchId(connection);
                if(branchId!=0 && insertUserRecord(connection,employee)){
                    userId=employee.getUserId();
                    try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.INSERT_EMPLOYEE_QUERY)){
                        ps.setInt(1,userId);
                        ps.setString(2,employee.getEmployeeDesignation());
                        ps.setDouble(3,employee.getEmployeeCTC());
                        ps.setInt(4, employee.getYearOfExperience());
                        ps.setBoolean(5, employee.isActive());
                        int rs = ps.executeUpdate();
                        if(rs>0 && ps.getGeneratedKeys().next()){
                            employeeId = ps.getGeneratedKeys().getDouble(1);
                            employee.setEmployeeId(employeeId);
                        }
                    }
                    try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.INSERT_EMPLOYEE_BRANCH_MAP)){
                        ps.setDouble(1,employeeId);
                        ps.setInt(2,branchId);
                        rs1=ps.executeUpdate();
                    }
                }
                if(rs1>0 && employeeId > 10000000000D){
                    connection.commit();
                    isEmployeeInserted=true;
                }else{
                    connection.rollback();
                }
            }
        } catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isEmployeeInserted;
    }
    boolean insertAdmin(Admin admin){
        int userId,rs = 0;
        boolean isAdminInserted=false;

        try(Connection connection = MySQLConnection.connect()) {
            if(connection != null){
                connection.setAutoCommit(false);
                if(insertUserRecord(connection,admin)){
                    userId=admin.getUserId();
                    try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.INSERT_ADMIN_QUERY)){
                        ps.setInt(1,userId);
                        ps.setBoolean(2,admin.isActive());
                        rs = ps.executeUpdate();
                    }
                }
                if(rs > 0){
                    connection.commit();
                    isAdminInserted=true;
                }else{
                    connection.rollback();
                }
            }
        } catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isAdminInserted;
    }
    boolean insertNominee(Connection connection,Nominee nominee,double accountNumber){
        int rs = 0;
        boolean isNomineeInserted=false;

        try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.INSERT_NOMINEE_QUERY)){
            ps.setDouble(1,accountNumber);
            ps.setString(2,nominee.getFirstName());
            ps.setString(3,nominee.getMiddleName());
            ps.setString(4,nominee.getLastName());
            ps.setString(5,nominee.getEmailId());
            ps.setString(6,nominee.getGender());
            ps.setInt(7,nominee.getAge());
            ps.setString(8, nominee.getMobileNumber());
            ps.setString(9,nominee.getCKYCVerificationDocument());
            ps.setString(10,nominee.getCKYCVerificationId());
            rs = ps.executeUpdate();
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }
        if(rs > 0){
            isNomineeInserted=true;
        }

        return isNomineeInserted;
    }
}
