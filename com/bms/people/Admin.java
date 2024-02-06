package com.bms.people;

public class Admin extends User{
    private boolean isActive;

    public Admin(String firstName, String middleName, String lastName, String emailId, String gender, int age, long mobileNumber, String password, boolean isActive) {
        super(firstName, middleName, lastName, emailId, gender, age, mobileNumber, password);
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}