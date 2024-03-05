package com.bms;

import com.bms.functionality.account.AccountLogic;
import com.bms.functionality.app_implementation.*;
import com.bms.people.Admin;
import com.bms.people.Customer;
import com.bms.people.Employee;
import com.bms.people.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Application{
    private User loggedInUserInfo=null;
    private ArrayList<Double> loggedInCustomerAccountNumbers =new ArrayList<>();

    public void appFeatures(){
        int optionIndex=0;
        int userOption=-1;
        Scanner in = new Scanner(System.in);
        System.out.println("--------------------------------");
        System.out.println("Banking Application");
        System.out.println("--------------------------------");

        applicationWhile: while(userOption<optionIndex){
            optionIndex=0;
            System.out.println("\n");
            if(loggedInUserInfo != null){
                if(loggedInUserInfo instanceof Customer || loggedInUserInfo instanceof Employee){
                    System.out.println(++optionIndex + ". Add Card.");
                    System.out.println(++optionIndex + ". Delete Card.");
                    System.out.println(++optionIndex + ". Add Gold Loan.");
                    System.out.println(++optionIndex + ". Add Home Loan.");
                    System.out.println(++optionIndex + ". Add Nominee.");
                    System.out.println(++optionIndex + ". Delete Nominee.");
                }
                if(loggedInUserInfo instanceof Customer){
                    System.out.println(++optionIndex + ". Add Account.");
                    System.out.println(++optionIndex + ". Update Customer Profile.");
                    System.out.println(++optionIndex + ". Transfer Amount.");
                    System.out.println(++optionIndex + ". Deposit Amount.");
                    System.out.println(++optionIndex + ". Withdraw Amount.");
                    System.out.println(++optionIndex + ". View All Transaction.");
                }
                if(loggedInUserInfo instanceof Employee){
                    System.out.println(++optionIndex + ". Create Customer Account.");
                    System.out.println(++optionIndex + ". Change Customer Account Branch.");
                    System.out.println(++optionIndex + ". Add New Debit Card Master Record.");
                    System.out.println(++optionIndex + ". Add New Credit Card Master Record.");
                    System.out.println(++optionIndex + ". Add New Co Branded Credit Card Master Record.");
                }
                if(loggedInUserInfo instanceof Admin){
                    System.out.println(++optionIndex + ". Add Admin.");
                    System.out.println(++optionIndex + ". Add Employee.");
                    System.out.println(++optionIndex + ". Delete Employee.");
                    System.out.println(++optionIndex + ". Update Employee Profile.");
                    System.out.println(++optionIndex + ". Add Bank Branch.");
                    System.out.println(++optionIndex + ". Change Employee Branch.");
                }
                System.out.println(++optionIndex + ". Logout User.");

            }else{
                System.out.println(++optionIndex + ". Register New Customer.");
                System.out.println(++optionIndex + ". Login Customer.");
                System.out.println(++optionIndex + ". Login Employee.");
                System.out.println(++optionIndex + ". Login Admin.");
            }
            System.out.println(++optionIndex + ". Exit Application.");
            System.out.print("Please select a option from above list: ");

            userOption = in.nextInt();

            if(loggedInUserInfo != null){
                if(loggedInUserInfo instanceof Admin){
                    switch(userOption){
                        case 1:{
                            AdminFunctionality admin = new AdminFunctionality();
                            admin.addAdmin();
                            break;
                        }
                        case 2:{
                            AdminFunctionality admin = new AdminFunctionality();
                            admin.addEmployee();
                            break;
                        }
                        case 3:{
                            AdminFunctionality admin = new AdminFunctionality();
                            admin.deleteEmployee();
                            break;
                        }
                        case 4:{
                            AdminFunctionality admin = new AdminFunctionality();
                            admin.updateEmployee();
                            break;
                        }
                        case 5:{
                            AdminFunctionality admin = new AdminFunctionality();
                            admin.addBranch();
                            break;
                        }
                        case 6:{
                            AdminFunctionality admin = new AdminFunctionality();
                            admin.changeEmployeeBranch();
                            break;
                        }
                        case 7:{
                            AdminFunctionality admin = new AdminFunctionality(loggedInUserInfo);
                            if(admin.signOut()){
                                loggedInUserInfo=null;
                                loggedInCustomerAccountNumbers =new ArrayList<>();
                            }else{
                                System.out.println("Admin Logout Failed.");
                            }
                            break;
                        }
                        case 8: {
                            loggedInUserInfo =null;
                            loggedInCustomerAccountNumbers =new ArrayList<>();
                            break applicationWhile;
                        }
                    }
                }else{
                    CustomerEmployeeFunctionality customerEmployee = new CustomerEmployeeFunctionality(loggedInCustomerAccountNumbers);
                    switch(userOption){
                        case 1: {
                            customerEmployee.addCard();
                            break;
                        }
                        case 2: {
                            customerEmployee.deleteCard();
                            break;
                        }
                        case 3: {
                            customerEmployee.addGoldLoan();
                            break;
                        }
                        case 4:{
                            customerEmployee.addHomeLoan();
                            break;
                        }
                        case 5: {
                            customerEmployee.addNominee();
                            break;
                        }
                        case 6: {
                            customerEmployee.deleteNominee();
                            break;
                        }
                    }
                    if(loggedInUserInfo instanceof Employee){
                        switch(userOption){
                            case 7: {
                                EmployeeFunctionality employee = new EmployeeFunctionality();
                                employee.createCustomerAccount();
                                break;
                            }
                            case 8: {
                                EmployeeFunctionality employee = new EmployeeFunctionality();
                                employee.changeCustomerBranch();
                                break;
                            }case 9: {
                                EmployeeFunctionality employee = new EmployeeFunctionality();
                                employee.saveNewDebitCardMaster();
                                break;
                            }case 10: {
                                EmployeeFunctionality employee = new EmployeeFunctionality();
                                employee.saveNewCreditCardMaster();
                                break;
                            }case 11: {
                                EmployeeFunctionality employee = new EmployeeFunctionality();
                                employee.saveNewBrandCreditCardMaster();
                                break;
                            }
                            case 12:{
                                EmployeeFunctionality employee = new EmployeeFunctionality(loggedInUserInfo);
                                if(employee.signOut()){
                                    loggedInUserInfo=null;
                                    loggedInCustomerAccountNumbers =new ArrayList<>();
                                }else{
                                    System.out.println("Employee Logout Failed.");
                                }
                                break;
                            }
                            case 13: {
                                loggedInUserInfo =null;
                                loggedInCustomerAccountNumbers =new ArrayList<>();
                                break applicationWhile;
                            }
                        }
                    }else if(loggedInUserInfo instanceof Customer){
                        switch(userOption){
                            case 7: {
                                CustomerFunctionality customer = new CustomerFunctionality(loggedInCustomerAccountNumbers,loggedInUserInfo);
                                customer.addAccount();
                                break;
                            }
                            case 8: {
                                CustomerFunctionality customer = new CustomerFunctionality(loggedInCustomerAccountNumbers,loggedInUserInfo);
                                customer.updateCustomerProfile();
                                break;
                            }
                            case 9: {
                                CustomerFunctionality customer = new CustomerFunctionality(loggedInCustomerAccountNumbers,loggedInUserInfo);
                                customer.transferAmount();
                                break;
                            }
                            case 10: {
                                CustomerFunctionality customer = new CustomerFunctionality(loggedInCustomerAccountNumbers,loggedInUserInfo);
                                customer.depositAmount();
                                break;
                            }
                            case 11: {
                                CustomerFunctionality customer = new CustomerFunctionality(loggedInCustomerAccountNumbers,loggedInUserInfo);
                                customer.withdrawAmount();
                                break;
                            }
                            case 12:{
                                CustomerFunctionality customer = new CustomerFunctionality(loggedInCustomerAccountNumbers,loggedInUserInfo);
                                customer.viewAllTransaction();
                                break;
                            }
                            case 13:{
                                CustomerFunctionality customer = new CustomerFunctionality(loggedInUserInfo);
                                if(customer.signOut()){
                                    loggedInUserInfo=null;
                                    loggedInCustomerAccountNumbers =new ArrayList<>();
                                }else{
                                    System.out.println("Customer Logout Failed.");
                                }
                                break;
                            }
                            case 14: {
                                loggedInUserInfo =null;
                                loggedInCustomerAccountNumbers =new ArrayList<>();
                                break applicationWhile;
                            }
                        }
                    }
                }
            }else{
                switch(userOption){
                    case 1: {
                        GuestFunctionality guest = new GuestFunctionality();
                        guest.signUp();
                        break;
                    }
                    case 2: {
                        CustomerFunctionality customerLogic = new CustomerFunctionality();
                        AccountLogic accountLogic = new AccountLogic();
                        Customer customer = (Customer) customerLogic.signIn(1);
                        if(customer!=null){
                            loggedInUserInfo=customer;
                            loggedInCustomerAccountNumbers =accountLogic.getCustomerAccountNumberOnLogin(customer.getCIFNumber());
                        }
                        break;
                    }
                    case 3: {
                        EmployeeFunctionality employeeLogic = new EmployeeFunctionality();
                        Employee employee = (Employee) employeeLogic.signIn(2);
                        if(employee!=null){
                            loggedInUserInfo=employee;
                        }
                        break;
                    }
                    case 4: {
                        AdminFunctionality adminLogic = new AdminFunctionality();
                        Admin admin = (Admin) adminLogic.signIn(3);
                        if(admin!=null){
                            loggedInUserInfo=admin;
                        }
                        break;
                    }
                    case 5: {
                        loggedInUserInfo =null;
                        loggedInCustomerAccountNumbers =new ArrayList<>();
                        break applicationWhile;
                    }
                }
            }
            userOption=-1;
        }
    }
}
