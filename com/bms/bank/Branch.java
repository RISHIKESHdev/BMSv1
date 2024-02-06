package com.bms.bank;

import com.bms.people.Employee;
import com.bms.people.Customer;

import java.util.List;

public class Branch {
    private String IFSCCode;
    private String branchName;
    private String addressLineOne;
    private String addressLineTwo;
    private String addressLineThree;
    private String landMark;
    private String city;
    private long pinCode;
    private long mobileNumber;
    private double latitude;
    private double longitude;
    private List<Customer> customerList;
    private List<Employee> employeeList;

    public Branch(String IFSCCode, String branchName, String addressLineOne, String addressLineTwo, String addressLineThree, String landMark, String city, long pinCode, long mobileNumber, double latitude, double longitude) {
        this.IFSCCode = IFSCCode;
        this.branchName = branchName;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.addressLineThree = addressLineThree;
        this.landMark = landMark;
        this.city = city;
        this.pinCode = pinCode;
        this.mobileNumber = mobileNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIFSCCode() {
        return IFSCCode;
    }

    public void setIFSCCode(String IFSCCode) {
        this.IFSCCode = IFSCCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    public String getAddressLineThree() {
        return addressLineThree;
    }

    public void setAddressLineThree(String addressLineThree) {
        this.addressLineThree = addressLineThree;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getPinCode() {
        return pinCode;
    }

    public void setPinCode(long pinCode) {
        this.pinCode = pinCode;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void addCustomerList(Customer customer) {
        this.customerList.add(customer);
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(Employee employee) {
        this.employeeList.add(employee);
    }
}
