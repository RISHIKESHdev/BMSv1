package com.bms.people;

import com.bms.bank.Address;

public class Admin extends User{
    private boolean isActive;

    public Admin(String firstName, String middleName, String lastName, String emailId, String gender, int age, long mobileNumber, String password, Address address, boolean isActive) {
        super(firstName, middleName, lastName, emailId, gender, age, mobileNumber, password, address);
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}