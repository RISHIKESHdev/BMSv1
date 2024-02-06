package com.bms.people;

import com.bms.bank.Address;

public class Employee extends User{
    private int employeeId;
    private String employeeDesignation;
    private double employeeCTC;
    private int yearOfExperience;
    private boolean isActive;

    public Employee(String firstName, String middleName, String lastName, String emailId, String gender, int age, long mobileNumber, String password, Address address, int employeeId, String employeeDesignation, double employeeCTC, int yearOfExperience, boolean isActive) {
        super(firstName, middleName, lastName, emailId, gender, age, mobileNumber, password, address);
        this.employeeId = employeeId;
        this.employeeDesignation = employeeDesignation;
        this.employeeCTC = employeeCTC;
        this.yearOfExperience = yearOfExperience;
        this.isActive = isActive;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(String employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    public double getEmployeeCTC() {
        return employeeCTC;
    }

    public void setEmployeeCTC(double employeeCTC) {
        this.employeeCTC = employeeCTC;
    }

    public int getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(int yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}