package com.bms.bank;

import com.bms.people.Employee;
import com.bms.people.Customer;

import java.util.List;

public class Branch {
    private String IFSCCode;
    private String branchName;
    private Address address;
    private long mobileNumber;
    private GeoLocation geoLocation;
    private List<Customer> customerList;
    private List<Employee> employeeList;

    public Branch(String IFSCCode, String branchName, Address address, long mobileNumber, GeoLocation geoLocation) {
        this.IFSCCode = IFSCCode;
        this.branchName = branchName;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.geoLocation = geoLocation;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
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
