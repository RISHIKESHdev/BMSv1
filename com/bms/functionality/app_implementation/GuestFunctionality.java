package com.bms.functionality.app_implementation;

import com.bms.functionality.app_interface.GuestInterface;
import com.bms.functionality.profile.ProfileLogic;

public class GuestFunctionality implements GuestInterface {
    @Override
    public void signUp() {
        ProfileLogic profile = new ProfileLogic();
        profile.registerCustomer();
    }
}
