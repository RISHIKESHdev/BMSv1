package com.bms.functionality.app_interface;

import com.bms.people.User;

public interface UserInterface {
    public User signIn(int profileCode);
    public void signOut();
}