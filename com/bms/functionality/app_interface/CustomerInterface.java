package com.bms.functionality.app_interface;

public interface CustomerInterface extends CustomerEmployeeInterface{
    public void addAccount();
    public void updateProfile();
    public void transferAmount();
    public void withdrawAmount();
    public void depositAmount();
}
