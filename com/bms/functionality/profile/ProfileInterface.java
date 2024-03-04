package com.bms.functionality.profile;

import com.bms.people.*;

import java.sql.Connection;

public interface ProfileInterface {
    Nominee addNomineeOnAccountCreation(Connection connection, double accountNumber);
    Employee registerEmployee();
    Customer registerCustomer();
    Customer registerCustomerOnAccountCreation(Connection connection);
    Admin registerAdmin();
    Customer getCustomerByCIFNumber(Connection connection,double CIFNumber);
    boolean checkCustomerByCIFNumber(Connection connection,double CIFNumber);
    Admin getAdminOnLogin();
    Employee getEmployeeOnLogin();
    Customer getCustomerOnLogin();
    void deActivateEmployee();
    Employee employeeProfileUpdate();
    Customer customerProfileUpdate();
}
