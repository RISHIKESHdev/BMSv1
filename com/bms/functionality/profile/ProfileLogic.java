package com.bms.functionality.profile;

import com.bms.Main;
import com.bms.functionality.CommonConstant;
import com.bms.people.*;

import java.sql.Connection;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProfileLogic implements ProfileInterface{
    private static final Scanner in = Main.globalIn;

    public Nominee addNominee(Connection connection,double accountNumber){
        Nominee nominee;

        ProfileDataLogic dataLogic = new ProfileDataLogic();
        nominee = getNomineeInput();

        if(nominee!=null && dataLogic.insertNominee(connection,nominee,accountNumber)) {
            System.out.println("Nominee Record Inserted.");
        }else{
            System.out.println("Nominee Record Insertion Failed.");
        }

        return nominee;
    }
    public Admin registerAdmin(){
        Admin admin;

        ProfileDataLogic dataLogic = new ProfileDataLogic();
        admin = (Admin) getProfileInput(2);

        if(admin!=null && dataLogic.insertAdmin(admin)) {
            System.out.println("Admin Record Inserted.");
        }else{
            System.out.println("Admin Record Insertion Failed.");
        }

        return admin;
    }
    public Employee registerEmployee(){
        Employee employee;

        ProfileDataLogic dataLogic = new ProfileDataLogic();
        employee = (Employee) getProfileInput(3);

        if(employee!=null && dataLogic.insertEmployee(employee)) {
            System.out.println("Employee Id: "+ employee.getEmployeeId());
            System.out.println("Employee Record Inserted.");
        }else{
            System.out.println("Employee Record Insertion Failed.");
        }

        return employee;
    }
    public Customer registerCustomer(Connection connection){
        Customer customer;

        ProfileDataLogic dataLogic = new ProfileDataLogic();
        customer = (Customer)getProfileInput(1);

        if(customer!=null && dataLogic.insertCustomer(connection,customer)) {
            System.out.println("CIF Number: "+ customer.getCIFNumber());
            System.out.println("Customer Record Inserted.");
        }else{
            customer=null;
            System.out.println("Customer Record Insertion Failed.");
        }

        return customer;
    }

    public Customer getCustomerByCIFNumber(Connection connection,double CIFNumber){
        Customer customer;

        ProfileDataLogic dataLogic = new ProfileDataLogic();
        customer = dataLogic.selectCustomerByCIFNumber(connection,CIFNumber);

        return customer;
    }
    public boolean checkCustomerByCIFNumber(Connection connection,double CIFNumber){
        ProfileDataLogic dataLogic = new ProfileDataLogic();
        return dataLogic.isCustomerPresent(connection,CIFNumber);
    }
    private User getProfileInput(int profileCode){
        User user=null;

        String firstName,middleName,lastName,emailId,gender,password,mobileNumber;
        int age;

        System.out.print("First Name: ");firstName=in.next();
        System.out.print("Middle Name: ");middleName=in.next();
        System.out.print("Last Name: ");lastName=in.next();
        System.out.print("Email Id: ");emailId=in.next();
        System.out.print("Gender: ");gender=in.next();
        System.out.print("Age: ");age=in.nextInt();
        System.out.print("Mobile Number: ");mobileNumber=in.next();
        System.out.print("Password: ");password=in.next();

        switch (profileCode){
            case 1:{
                String PANNumber,CKYCVerificationDocument,CKYCVerificationId;

                System.out.print("PAN Number: ");PANNumber=in.next();
                System.out.print("CKYC Verification Document: ");CKYCVerificationDocument=in.next();
                System.out.print("CKYC Verification Id: ");CKYCVerificationId=in.next();

                user = new Customer(firstName,middleName,lastName,emailId,gender,age,mobileNumber,password,PANNumber,CKYCVerificationDocument,CKYCVerificationId);

                break;
            }
            case 2:{
                boolean isActive;

                System.out.print("Is Active: ");isActive=in.nextBoolean();

                user = new Admin(firstName,middleName,lastName,emailId,gender,age,mobileNumber,password,isActive);
                break;
            }
            case 3:{
                int yearOfExperience;
                String employeeDesignation;
                double employeeCTC;
                boolean isActive;

                System.out.print("Employee Designation: ");employeeDesignation=in.next();
                System.out.print("Employee CTC: ");employeeCTC=in.nextDouble();
                System.out.print("Year Of Experience: ");yearOfExperience=in.nextInt();
                System.out.print("Is Active: ");isActive=in.nextBoolean();

                user = new Employee(firstName,middleName,lastName,emailId,gender,age,mobileNumber,password,employeeDesignation,employeeCTC,yearOfExperience,isActive);
                break;
            }
        }
         if(!isValidProfile(user)) user=null;

        return user;
    }
    private Nominee getNomineeInput(){
         Nominee nominee=null;

        String firstName,middleName,lastName,emailId,gender,CKYCVerificationDocument,CKYCVerificationId;
        int age;
        long mobileNumber;

        System.out.print("First Name: ");firstName=in.next();
        System.out.print("Middle Name: ");middleName=in.next();
        System.out.print("Last Name: ");lastName=in.next();
        System.out.print("Email Id: ");emailId=in.next();
        System.out.print("Gender: ");gender=in.next();
        System.out.print("Age: ");age=in.nextInt();
        System.out.print("Mobile Number: ");mobileNumber=in.nextInt();
        System.out.print("CKYC Verification Document: ");CKYCVerificationDocument=in.next();
        System.out.print("CKYC Verification Id: ");CKYCVerificationId=in.next();

        nominee = new Nominee(firstName,middleName,lastName,emailId,gender,age,mobileNumber,CKYCVerificationDocument,CKYCVerificationId);

        if(!isValidNominee(nominee)) nominee=null;

        return nominee;
    }

    private boolean isValidNominee(Nominee nominee) {
         boolean isValidNominee = true;

        if(!Pattern.matches(CommonConstant.AT_LEAST_ONE_STRING_REGEX, nominee.getFirstName())){
            System.out.println("Nominee First Name Must Contain At least One Words.");
            isValidNominee=false;
        }
        if(!Pattern.matches(CommonConstant.AT_LEAST_ONE_STRING_REGEX, nominee.getLastName())){
            System.out.println("Nominee Last Name Must Contain At least One Words.");
            isValidNominee=false;
        }
        if(!Pattern.matches(CommonConstant.EMAIL_ID_REGEX, nominee.getEmailId())){
            System.out.println("Nominee Entered Email Id Not Valid.");
            isValidNominee=false;
        }
        if(!Pattern.matches(CommonConstant.GENDER_REGEX, nominee.getGender())){
            System.out.println("Nominee Entered Gender is Not Valid.");
            isValidNominee=false;
        }
        if(nominee.getAge()<5 || nominee.getAge()>150){
            System.out.println("Nominee User Age Must Be In Between 5 to 150");
            isValidNominee=false;
        }
        if(!Pattern.matches(CommonConstant.MOBILE_NO_REGEX, nominee.getMobileNumber())){
            System.out.println("Nominee Entered Mobile Number is Not Valid.");
            isValidNominee=false;
        }
        if(!(nominee.getCKYCVerificationDocument()!=null && !Objects.equals(nominee.getCKYCVerificationDocument(), ""))){
            System.out.println("Nominee KYC Verification Document is Mandatory.");
            isValidNominee=false;
        }
        if(!(nominee.getCKYCVerificationId()!=null && !Objects.equals(nominee.getCKYCVerificationId(), ""))){
            System.out.println("Nominee KYC Verification Id is Mandatory.");
            isValidNominee=false;
        }

        return isValidNominee;
    }

    private boolean isValidUserInfo(User user){
        boolean isValidUser=true;

        if(!Pattern.matches(CommonConstant.AT_LEAST_ONE_STRING_REGEX, user.getFirstName())){
            System.out.println("First Name Must Contain At least One Words.");
            isValidUser=false;
        }
        if(!Pattern.matches(CommonConstant.AT_LEAST_ONE_STRING_REGEX, user.getLastName())){
            System.out.println("Last Name Must Contain At least One Words.");
            isValidUser=false;
        }
        if(!Pattern.matches(CommonConstant.EMAIL_ID_REGEX, user.getEmailId())){
            System.out.println("Entered Email Id Not Valid.");
            isValidUser=false;
        }
        if(!Pattern.matches(CommonConstant.GENDER_REGEX, user.getGender())){
            System.out.println("Entered Gender is Not Valid.");
            isValidUser=false;
        }
        if(!Pattern.matches(CommonConstant.PASSWORD_REGEX, user.getPassword())){
            System.out.println("Entered Password is Not Valid.");
            isValidUser=false;
        }
        if(user.getAge()<5 || user.getAge()>150){
            System.out.println("User Age Must Be In Between 5 to 150");
        }
        if(!Pattern.matches(CommonConstant.MOBILE_NO_REGEX, user.getMobileNumber())){
            System.out.println("Entered Mobile Number is Not Valid.");
            isValidUser=false;
        }

        return isValidUser;
    }
    private boolean isValidProfile(User user){
         if(user instanceof Customer){
             return isValidCustomerInfo((Customer) user);
         } else if (user instanceof Employee) {
            return isValidEmployeeInfo((Employee) user);
         }
         return true;
    }
    private boolean isValidCustomerInfo(Customer customer){
         boolean isValidCustomer=true;

        isValidCustomer=isValidUserInfo(customer);
        if(!Pattern.matches(CommonConstant.PAN_REGEX, customer.getPANNumber())){
            System.out.println("Entered PAN Number is Not Valid.");
            isValidCustomer=false;
        }
        if(!(customer.getCKYCVerificationDocument()!=null && !Objects.equals(customer.getCKYCVerificationDocument(), ""))){
            System.out.println("CKYC Verification Document is Mandatory.");
            isValidCustomer=false;
        }
        if(!(customer.getCKYCVerificationId()!=null && !Objects.equals(customer.getCKYCVerificationId(), ""))){
            System.out.println("CKYC Verification Id is Mandatory.");
            isValidCustomer=false;
        }
         return isValidCustomer;
    }
    private boolean isValidEmployeeInfo(Employee employee){
         boolean isValidEmployee=true;

        isValidEmployee=isValidUserInfo(employee);
        if(!(employee.getYearOfExperience()>=0 && employee.getYearOfExperience()<=40)){
            System.out.println("Employee Year Of Experience Must Be Between 0 to 40.");
            isValidEmployee=false;
        }
        if(!Pattern.matches(CommonConstant.AT_LEAST_ONE_STRING_REGEX, employee.getEmployeeDesignation())){
            System.out.println("Employee Designation Must Contain At least One Words.");
            isValidEmployee=false;
        }
        if(!(employee.getEmployeeCTC()>=20000)){
            System.out.println("Employee CTC Must Be At least Greater Than 20000.");
            isValidEmployee=false;
        }

        return isValidEmployee;
    }
}