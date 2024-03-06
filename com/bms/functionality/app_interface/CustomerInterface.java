package com.bms.functionality.app_interface;

public interface CustomerInterface extends CustomerEmployeeInterface{
    public void addAccount();
    public void updateCustomerProfile();
    public void transferAmount();
    public void withdrawAmount();
    public void depositAmount();
    public void viewAllTransaction();
    public void viewAllActiveCardDetail();
}