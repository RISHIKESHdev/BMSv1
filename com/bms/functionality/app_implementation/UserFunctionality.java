package com.bms.functionality.app_implementation;

import com.bms.Application;
import com.bms.functionality.app_interface.UserInterface;
import com.bms.functionality.profile.ProfileLogic;
import com.bms.people.User;

public abstract class UserFunctionality implements UserInterface {
    private User loggedInUserInfo;
    public UserFunctionality(){
    }
    public UserFunctionality(User user){
        this.loggedInUserInfo=user;
    }
    @Override
    public User signIn(int profileCode) {
        User user = null;

        switch (profileCode){
            case 1: {
                ProfileLogic profile = new ProfileLogic();
                user= profile.getCustomerOnLogin();
                break;
            }
            case 2: {
                ProfileLogic profile = new ProfileLogic();
                user= profile.getEmployeeOnLogin();
                break;
            }
            case 3: {
                ProfileLogic profile = new ProfileLogic();
                user= profile.getAdminOnLogin();
                break;
            }
        }

        return user;
    }

    @Override
    public boolean signOut() {
        if(loggedInUserInfo!=null){
            System.out.println("User Logged Out Successfully.");
            return true;
        }else{
            return false;
        }
    }
}
