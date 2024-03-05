package com.bms.functionality.profile;

import com.bms.Main;
import com.bms.bank.Address;
import com.bms.functionality.CommonConstant;
import com.bms.functionality.MySQLConnection;
import com.bms.functionality.account.AccountLogic;
import com.bms.functionality.branch.BranchLogic;
import com.bms.functionality.navigate.NavigateLogic;
import com.bms.people.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProfileDataLogic {
    private final Scanner in = Main.globalIn;
    private ArrayList<Double> customerAccountNumbers;
    private User loggeInUser;
    public ProfileDataLogic(){

    }
    public ProfileDataLogic(ArrayList<Double> customerAccountNumbers){
        this.customerAccountNumbers=customerAccountNumbers;
    }
    public ProfileDataLogic(ArrayList<Double> customerAccountNumbers,User loggeInUser){
        this.customerAccountNumbers=customerAccountNumbers;
        this.loggeInUser=loggeInUser;
    }
    private boolean insertUserRecord(Connection connection, User user){
        int addressId=0;
        boolean isUserRecordInserted=false;

        NavigateLogic navigate = new NavigateLogic();

        addressId=navigate.getIdOnInsertAddressRecord(connection);
        if(addressId!=0){
            try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS)){
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
                if(rs>0){
                    ResultSet rSet=ps.getGeneratedKeys();
                    rSet.next();
                    user.setUserId(rSet.getInt(1));
                    isUserRecordInserted=true;
                }
            }catch(NullPointerException | SQLException e){
                System.out.println(e.getMessage());
            }
        }

        return isUserRecordInserted;
    }

    boolean insertCustomer(Customer customer){
        int userId;
        double CIFNumber;
        boolean isCustomerInserted=false;

        try(Connection connection = MySQLConnection.connect()) {
            if(connection!=null){
                connection.setAutoCommit(false);
                if(insertUserRecord(connection,customer)){
                    userId=customer.getUserId();
                    try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.INSERT_CUSTOMER_QUERY, Statement.RETURN_GENERATED_KEYS)){
                        ps.setInt(1,userId);
                        ps.setString(2,customer.getCKYCVerificationDocument());
                        ps.setString(3,customer.getCKYCVerificationId());
                        ps.setString(4, customer.getPANNumber());
                        int rs = ps.executeUpdate();
                        if(rs>0){
                            ResultSet rSet=ps.getGeneratedKeys();
                            rSet.next();
                            CIFNumber = rSet.getDouble(1);
                            customer.setCIFNumber(CIFNumber);
                            isCustomerInserted=true;
                        }
                    }
                }
                if(!isCustomerInserted) connection.rollback();
                else connection.commit();
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isCustomerInserted;
    }

    boolean insertCustomerOnAccountCreation(Connection connection, Customer customer){
        int userId;
        double CIFNumber;
        boolean isCustomerInserted=false;

        if(insertUserRecord(connection,customer)){
            userId=customer.getUserId();
            try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.INSERT_CUSTOMER_QUERY, Statement.RETURN_GENERATED_KEYS)){
                ps.setInt(1,userId);
                ps.setString(2,customer.getCKYCVerificationDocument());
                ps.setString(3,customer.getCKYCVerificationId());
                ps.setString(4, customer.getPANNumber());
                int rs = ps.executeUpdate();
                if(rs>0){
                    ResultSet rSet = ps.getGeneratedKeys();
                    rSet.next();
                    CIFNumber = rSet.getDouble(1);
                    customer.setCIFNumber(CIFNumber);
                    isCustomerInserted=true;
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
    boolean checkCIFNumber(Connection connection,double CIFNumber){
        boolean isCIFPresent = false;

        try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.CHECK_A_CUSTOMER_QUERY)){
            ps.setDouble(1,CIFNumber);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getInt(1)>0){
                    isCIFPresent=true;
                }
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return isCIFPresent;
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
    boolean updateEmployeeDeactivate(double employeeId) throws SQLException {
        boolean isEmployeeDeactivated=false;

        try(Connection connection = MySQLConnection.connect()){
            if(connection!=null){
                connection.setAutoCommit(false);
                try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.UPDATE_EMPLOYEE_DEACTIVATE_QUERY)){
                    ps.setDouble(1,employeeId);
                    int rs =ps.executeUpdate();
                    if(rs>0){
                        connection.commit();
                        isEmployeeDeactivated=true;
                    }
                }
                if(!isEmployeeDeactivated) connection.rollback();
            }
        }

        return isEmployeeDeactivated;
    }
    private boolean updateUserRecord(Connection connection,String updateUserQuery,double employeeId){
        boolean isUserUpdated=false;

        try(PreparedStatement ps = connection.prepareStatement(updateUserQuery)){
            ps.setDouble(1,employeeId);
            int rs=ps.executeUpdate();
            isUserUpdated=rs>0;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return isUserUpdated;
    }
    private double getCIFNumber(Connection connection){
        double CIFNumber;

        System.out.print("CIF Number: ");CIFNumber=in.nextDouble();
        if(isCustomerPresent(connection,CIFNumber)){
            System.out.println("Entered CIF Number Not Found.");
            CIFNumber=0;
        }

        return CIFNumber;
    }
    boolean updateCustomerProfile(Customer customer){
        boolean isCustomerProfileUpdated=false;
        double CIFNumber;
        String updateCustomerQuery,updateUserQuery;

        try(Connection connection = MySQLConnection.connect()){
            if(connection!=null) {
                connection.setAutoCommit(false);
                updateUserQuery=getUpdateUserQuery(customer);
                CIFNumber=((Customer)loggeInUser).getCIFNumber();
                if(CIFNumber!=0 && updateUserQuery!=null && updateUserRecord(connection,updateUserQuery,CIFNumber)){
                    updateCustomerQuery=getUpdateCustomerQuery(customer);
                    try(PreparedStatement ps = connection.prepareStatement(updateCustomerQuery)){
                        ps.setDouble(1,CIFNumber);
                        int rs=ps.executeUpdate();
                        isCustomerProfileUpdated=rs>0;
                    }
                }
                if(isCustomerProfileUpdated){
                    connection.commit();
                }else{
                    connection.rollback();
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return isCustomerProfileUpdated;
    }
    boolean updateEmployeeProfile(Employee employee,double employeeId){
        boolean isEmployeeProfileUpdated=false;
        String updateEmployeeQuery,updateUserQuery;

        try(Connection connection = MySQLConnection.connect()){
            if(connection!=null) {
                connection.setAutoCommit(false);
                updateUserQuery=getUpdateUserQuery(employee);
                if(updateUserQuery!=null && updateUserRecord(connection,updateUserQuery,employeeId)){
                    updateEmployeeQuery=getUpdateEmployeeQuery(employee);
                    try(PreparedStatement ps = connection.prepareStatement(updateEmployeeQuery)){
                        ps.setDouble(1,employeeId);
                        int rs=ps.executeUpdate();
                        isEmployeeProfileUpdated=rs>0;
                    }
                }
                if(isEmployeeProfileUpdated){
                    connection.commit();
                }else{
                    connection.rollback();
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return isEmployeeProfileUpdated;
    }
    private String ifSpaceReplaceNull(String string){
        if(string==null || Pattern.matches(CommonConstant.EMPTY_REGEX,string)) return null;
        return string;
    }
    private String getUpdateUserQuery(User user){
        String updateUserQuery="";

        if(ifSpaceReplaceNull(user.getFirstName())!=null){
            updateUserQuery +=" first_Name='"+user.getFirstName() +"', ";
        }
        if(ifSpaceReplaceNull(user.getMiddleName())!=null){
            updateUserQuery +=" middle_Name='"+user.getMiddleName() +"', ";
        }
        if(ifSpaceReplaceNull(user.getLastName())!=null){
            updateUserQuery +=" last_Name='"+user.getLastName() +"', ";
        }
        if(ifSpaceReplaceNull(user.getGender())!=null){
            updateUserQuery +=" gender='"+user.getGender() +"', ";
        }
        if(user.getAge()!=0){
            updateUserQuery +=" age="+user.getAge() +", ";
        }
        if(ifSpaceReplaceNull(user.getMobileNumber())!=null){
            updateUserQuery +=" mobile_Number='"+user.getMobileNumber() +"', ";
        }

        if(updateUserQuery!="" && user instanceof Customer){
            updateUserQuery = updateUserQuery.substring(0, updateUserQuery.length() - 2);
            updateUserQuery = "UPDATE User JOIN Customer ON Customer.user_Id=User.Id SET" + updateUserQuery+ " WHERE Customer.CIF_Number=?";
        }
        else if(updateUserQuery!="" && user instanceof Employee){
            updateUserQuery = updateUserQuery.substring(0, updateUserQuery.length() - 2);
            updateUserQuery = "UPDATE User JOIN Employee ON Employee.user_Id=User.Id SET" + updateUserQuery+ " WHERE Employee.Employee_Id=?";
        }else{
            updateUserQuery=null;
        }

        return updateUserQuery;
    }
    private String getUpdateCustomerQuery(Customer customer){
        String updateCustomerQuery = "";

        if(ifSpaceReplaceNull(customer.getCKYCVerificationDocument())!=null){
            updateCustomerQuery +=" CKYC_Verification_Document='"+customer.getCKYCVerificationDocument() +"', ";
        }
        if(ifSpaceReplaceNull(customer.getCKYCVerificationId())!=null){
            updateCustomerQuery +=" CKYC_Verification_Id='"+customer.getCKYCVerificationId() +"', ";
        }
        if(ifSpaceReplaceNull(customer.getPANNumber())!=null){
            updateCustomerQuery +=" PAN_Number='"+customer.getPANNumber() +"', ";
        }

        if(updateCustomerQuery!=""){
            updateCustomerQuery = updateCustomerQuery.substring(0, updateCustomerQuery.length() - 2);
            updateCustomerQuery = "UPDATE Customer SET" + updateCustomerQuery+ " WHERE Customer.CIF_Number=?";
        }

        return updateCustomerQuery;
    }
    private String getUpdateEmployeeQuery(Employee employee){
        String updateEmployeeQuery = "";

        if(ifSpaceReplaceNull(employee.getEmployeeDesignation())!=null){
            updateEmployeeQuery +=" employee_Designation='"+employee.getEmployeeDesignation() +"', ";
        }
        if(employee.getEmployeeCTC()!=0){
            updateEmployeeQuery +=" employee_CTC="+employee.getEmployeeCTC() +", ";
        }
        if(employee.getYearOfExperience()!=0){
            updateEmployeeQuery +=" year_Of_Experience="+employee.getYearOfExperience() +", ";
        }

        if(updateEmployeeQuery!=""){
            updateEmployeeQuery = updateEmployeeQuery.substring(0, updateEmployeeQuery.length() - 2);
            updateEmployeeQuery = "UPDATE Employee SET" + updateEmployeeQuery+ " WHERE Employee.Employee_Id=?";
        }

        return updateEmployeeQuery;
    }
    boolean insertEmployee(Employee employee){
        int userId,branchId,rs1=0;
        double employeeId = 0;
        boolean isEmployeeInserted=false;

        try(Connection connection = MySQLConnection.connect()) {
            if(connection != null){
                connection.setAutoCommit(false);
                if(insertUserRecord(connection,employee)){
                    userId=employee.getUserId();
                    branchId=new BranchLogic().getBranchId(connection);
                    if(branchId!=0){
                        try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.INSERT_EMPLOYEE_QUERY, Statement.RETURN_GENERATED_KEYS)){
                            ps.setInt(1,userId);
                            ps.setString(2,employee.getEmployeeDesignation());
                            ps.setDouble(3,employee.getEmployeeCTC());
                            ps.setInt(4, employee.getYearOfExperience());
                            ps.setBoolean(5, employee.isActive());
                            int rs = ps.executeUpdate();
                            if(rs>0){
                                ResultSet rSet = ps.getGeneratedKeys();
                                rSet.next();
                                employeeId = rSet.getDouble(1);
                                employee.setEmployeeId(employeeId);
                            }
                        }
                        try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.INSERT_EMPLOYEE_BRANCH_MAP)){
                            ps.setDouble(1,employeeId);
                            ps.setInt(2,branchId);
                            rs1=ps.executeUpdate();
                        }
                    }
                }
                if(rs1>0 && employeeId >= 10000000000D){
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
    boolean deleteNominee(){
        boolean isRecordDeleted=false;
        int nomineeId;
        double accountNumber;

        AccountLogic accountLogic =new AccountLogic(customerAccountNumbers);
        ProfileLogic profileLogic = new ProfileLogic();

        try(Connection connection = MySQLConnection.connect()){
            if(connection!=null){
                connection.setAutoCommit(false);
                accountNumber=accountLogic.getAccountNumberOnUserRequest(connection);
                nomineeId=profileLogic.displayAndGetNomineeId(connection,accountNumber);
                if(nomineeId!=0 && accountNumber!=0){
                    try(PreparedStatement ps= connection.prepareStatement(ProfileSQLQuery.DELETE_NOMINEE_QUERY)){
                        ps.setInt(1,nomineeId);
                        ps.setDouble(2,accountNumber);
                        int rs=ps.executeUpdate();
                        isRecordDeleted=rs>0;
                    }
                }
                if(isRecordDeleted)connection.commit();
                else connection.rollback();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return isRecordDeleted;
    }
    boolean selectNomineeDetail(Connection connection,double accountNumer){
        boolean isRecordPresent=false;

        try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.SELECT_NOMINEE_BY_ACCOUNT_NUMBER)){
            ps.setDouble(1,accountNumer);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                isRecordPresent=true;
                System.out.println("Nominee Details :- ");
                System.out.println("Id: " + rs.getInt(1));
                System.out.println("First Name: " + rs.getString("first_Name"));
                System.out.println("Last Name: " + rs.getString("last_Name"));
                System.out.println("Mobile Number: " + rs.getString("mobile_Number"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return isRecordPresent;
    }
    boolean insertNewNominee(Nominee nominee){
        int rs = 0;
        double accountNumber;
        boolean isNomineeInserted=false;

        AccountLogic accountLogic =new AccountLogic(customerAccountNumbers);

        try(Connection connection = MySQLConnection.connect()){
            if(connection != null){
                connection.setAutoCommit(false);
                accountNumber=accountLogic.getAccountNumberOnUserRequest(connection);
                if(accountNumber!=0){
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
                        isNomineeInserted=rs>0;
                    }
                }
                if(isNomineeInserted){
                    connection.commit();
                }else connection.rollback();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return isNomineeInserted;
    }
    Admin checkCredentialAndGetAdmin(UserCredential userCredential){
        Admin admin=null;
        Address address;

        try(Connection connection = MySQLConnection.connect()){
            if(connection!=null){
                try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.SELECT_ADMIN_ON_LOGIN)){
                    ps.setString(1, userCredential.getEmailId());
                    ps.setString(2,userCredential.getPassword());
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        address=new Address(rs.getString("address_Line_One"),rs.getString("address_Line_Two"),rs.getString("address_Line_Three"),rs.getString("landmark"),rs.getString("city"),rs.getString("state"),rs.getString("country"),rs.getString("pinCode"));
                        admin=new Admin(rs.getString("first_Name"),rs.getString("middle_Name"),rs.getString("last_Name"),rs.getString("email_Id"),rs.getString("gender"),rs.getInt("age"),rs.getString("mobile_Number"),rs.getString("password"),rs.getBoolean("is_Active"));
                        admin.setAddress(address);
                    }
                }
            }
        } catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return admin;
    }
    Customer checkCredentialAndGetCustomer(UserCredential userCredential){
        Customer customer=null;
        Address address;

        try(Connection connection = MySQLConnection.connect()){
            if(connection!=null){
                try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.SELECT_CUSTOMER_ON_LOGIN)){
                    ps.setString(1, userCredential.getEmailId());
                    ps.setString(2,userCredential.getPassword());
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        address=new Address(rs.getString("address_Line_One"),rs.getString("address_Line_Two"),rs.getString("address_Line_Three"),rs.getString("landmark"),rs.getString("city"),rs.getString("state"),rs.getString("country"),rs.getString("pinCode"));
                        customer=new Customer(rs.getString("first_Name"),rs.getString("middle_Name"),rs.getString("last_Name"),rs.getString("email_Id"),rs.getString("gender"),rs.getInt("age"),rs.getString("mobile_Number"),rs.getString("password"),rs.getString("PAN_Number"),rs.getString("CKYC_Verification_Document"),rs.getString("CKYC_Verification_Id"));
                        customer.setAddress(address);
                        customer.setCIFNumber(rs.getDouble("CIF_Number"));
                    }
                }
            }
        } catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return customer;
    }
    Employee checkCredentialAndGetEmployee(UserCredential userCredential){
        Employee employee=null;
        Address address;

        try(Connection connection = MySQLConnection.connect()){
            if(connection!=null){
                try(PreparedStatement ps = connection.prepareStatement(ProfileSQLQuery.SELECT_EMPLOYEE_ON_LOGIN)){
                    ps.setString(1, userCredential.getEmailId());
                    ps.setString(2,userCredential.getPassword());
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        address=new Address(rs.getString("address_Line_One"),rs.getString("address_Line_Two"),rs.getString("address_Line_Three"),rs.getString("landmark"),rs.getString("city"),rs.getString("state"),rs.getString("country"),rs.getString("pinCode"));
                        employee=new Employee(rs.getString("first_Name"),rs.getString("middle_Name"),rs.getString("last_Name"),rs.getString("email_Id"),rs.getString("gender"),rs.getInt("age"),rs.getString("mobile_Number"),rs.getString("password"),rs.getString("employee_Designation"),rs.getDouble("employee_CTC"), rs.getInt("year_Of_Experience"),rs.getBoolean("is_Active"));
                        employee.setAddress(address);
                    }
                }
            }
        } catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }

        return employee;
    }
}
