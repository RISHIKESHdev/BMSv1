package com.bms.functionality.app_implementation;

import com.bms.Application;
import com.bms.functionality.app_interface.UserInterface;
import com.bms.functionality.profile.ProfileLogic;
import com.bms.people.Admin;
import com.bms.people.Customer;
import com.bms.people.Employee;
import com.bms.people.User;

public abstract class UserFunctionality implements UserInterface {
    @Override
    public User signIn(int profileCode) {
        User user = null;

//        Application app = new Application();
        switch (profileCode){
            case 1: {
                ProfileLogic profile = new ProfileLogic();
                Customer customer  = profile.getCustomerOnLogin();
                user=customer;
//                app.setLoggedInUserInfo(customer);
//                if(customer!=null) app.setLoggedInUserEmailId(customer.getEmailId());
                break;
            }
            case 2: {
                ProfileLogic profile = new ProfileLogic();
                Employee employee  = profile.getEmployeeOnLogin();
                user=employee;
//                app.setLoggedInUserInfo(employee);
//                if(employee!=null) app.setLoggedInUserEmailId(employee.getEmailId());
                break;
            }
            case 3: {
                ProfileLogic profile = new ProfileLogic();
                Admin admin  = profile.getAdminOnLogin();
                user=admin;
//                app.setLoggedInUserInfo(admin);
//                if(admin!=null) app.setLoggedInUserEmailId(admin.getEmailId());
                break;
            }
        }

        return user;
    }

    @Override
    public void signOut() {
        Application app = new Application();
        app.setLoggedInUserInfo(null);
        app.setLoggedInUserEmailId(null);
        System.out.println("User Logged Out Successfully.");
    }
}
