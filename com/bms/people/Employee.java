package com.bms.people;

import com.bms.bank.Address;

public class Employee extends User{
    private String employeeDesignation;
    private double employeeCTC;
    private int yearOfExperience;
    private boolean isActive;
    private double employeeId;

    public Employee(String firstName, String middleName, String lastName, String emailId, String gender, int age, String mobileNumber, String password, String employeeDesignation, double employeeCTC, int yearOfExperience, boolean isActive) {
        super(firstName, middleName, lastName, emailId, gender, age, mobileNumber, password);
        this.employeeDesignation = employeeDesignation;
        this.employeeCTC = employeeCTC;
        this.yearOfExperience = yearOfExperience;
        this.isActive = isActive;
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

    public double getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(double employeeId) {
        this.employeeId = employeeId;
    }
}