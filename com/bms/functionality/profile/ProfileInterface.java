package com.bms.functionality.profile;

import com.bms.people.*;

import java.sql.Connection;

public interface ProfileInterface {
    Nominee addNominee(Connection connection,double accountNumber);
    Employee registerEmployee();
    Customer registerCustomer(Connection connection);
    Admin registerAdmin();
    Customer getCustomerByCIFNumber(Connection connection,double CIFNumber);
    boolean checkCustomerByCIFNumber(Connection connection,double CIFNumber);
}
