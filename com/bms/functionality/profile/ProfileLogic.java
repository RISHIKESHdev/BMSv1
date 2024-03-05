package com.bms.functionality.profile;

import com.bms.Main;
import com.bms.bank.Address;
import com.bms.functionality.CommonConstant;
import com.bms.functionality.navigate.NavigateLogic;
import com.bms.people.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProfileLogic implements ProfileInterface{
    private final Scanner in = Main.globalIn;
    private final BufferedReader reader=Main.globalBuffer;
    private ArrayList<Double> customerAccountNumbers;
    private User loggedInCustomer;
    public ProfileLogic(){

    }
    public ProfileLogic(ArrayList<Double> customerAccountNumbers){
        this.customerAccountNumbers=customerAccountNumbers;
    }
    public ProfileLogic(ArrayList<Double> customerAccountNumbers,User loggedInCustomer){
        this.customerAccountNumbers=customerAccountNumbers;
        this.loggedInCustomer=loggedInCustomer;
    }

    public Admin getAdminOnLogin(){
        Admin admin;
        UserCredential userCredential;

        ProfileDataLogic dataLogic = new ProfileDataLogic();

        userCredential=getUserCredential();
        admin=dataLogic.checkCredentialAndGetAdmin(userCredential);
        if(admin!=null){
            System.out.println("Admin Logged In Successful.");
        }else{
            System.out.println("Admin Login Failed.");
        }

        return admin;
    }
    public Employee getEmployeeOnLogin(){
        Employee employee;
        UserCredential userCredential;

        ProfileDataLogic dataLogic = new ProfileDataLogic();

        userCredential=getUserCredential();
        employee=dataLogic.checkCredentialAndGetEmployee(userCredential);
        if(employee!=null){
            System.out.println("Employee Logged In Successful.");
        }else{
            System.out.println("Employee Login Failed.");
        }

        return employee;
    }
    public Customer getCustomerOnLogin(){
        Customer customer;
        UserCredential userCredential;

        ProfileDataLogic dataLogic = new ProfileDataLogic();

        userCredential=getUserCredential();
        customer=dataLogic.checkCredentialAndGetCustomer(userCredential);
        if(customer!=null){
            System.out.println("Customer Logged In Successful.");
        }else{
            System.out.println("Customer Login Failed.");
        }

        return customer;
    }
    public int displayAndGetNomineeId(Connection connection,double accountNumber){
        int NomineeId;

        ProfileDataLogic dataLogic = new ProfileDataLogic();

        if(dataLogic.selectNomineeDetail(connection,accountNumber)){
            System.out.print("Enter Nominee Id: ");NomineeId=in.nextInt();
        }else{
            NomineeId=0;
        }

        return NomineeId;
    }
    public Nominee addNomineeOnAccountCreation(Connection connection, double accountNumber){
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
    public Nominee addNominee(){
        Nominee nominee;

        ProfileDataLogic dataLogic = new ProfileDataLogic(customerAccountNumbers);
        nominee = getNomineeInput();

        if(nominee!=null && dataLogic.insertNewNominee(nominee)) {
            System.out.println("Nominee Record Inserted.");
        }else{
            System.out.println("Nominee Record Insertion Failed.");
        }

        return nominee;
    }
    public boolean removeNominee(){
        boolean isNomineeRemoved=false;

        ProfileDataLogic dataLogic = new ProfileDataLogic(customerAccountNumbers);

        if(dataLogic.deleteNominee()){
            System.out.println("Nominee Record Deleted.");
        }else{
            System.out.println("Nominee Record Deleteion Failed.");
        }

        return isNomineeRemoved;
    }
    public Admin registerAdmin(){
        Admin admin;

        ProfileDataLogic dataLogic = new ProfileDataLogic();
        admin = (Admin) getProfileInput(2,true);

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
        employee = (Employee) getProfileInput(3,true);

        if(employee!=null && dataLogic.insertEmployee(employee)) {
            System.out.printf("Employee Id: %.0f\n", employee.getEmployeeId());
            System.out.println("Employee Record Inserted.");
        }else{
            System.out.println("Employee Record Insertion Failed.");
        }

        return employee;
    }
    public Customer registerCustomer(){
        Customer customer;

        ProfileDataLogic dataLogic = new ProfileDataLogic();
        customer = (Customer)getProfileInput(1,true);

        if(customer!=null && dataLogic.insertCustomer(customer)) {
            System.out.printf("CIF Number: %.0f\n", customer.getCIFNumber());
            System.out.println("Customer Record Inserted.");
        }else{
            customer=null;
            System.out.println("Customer Record Insertion Failed.");
        }

        return customer;
    }
    public Customer registerCustomerOnAccountCreation(Connection connection){
        Customer customer;

        ProfileDataLogic dataLogic = new ProfileDataLogic();
        customer = (Customer)getProfileInput(1,true);

        if(customer!=null && dataLogic.insertCustomerOnAccountCreation(connection,customer)) {
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
    public double checkAndGetCIFNumber(Connection connection){
        double CIFNumber;

        ProfileDataLogic dataLogic = new ProfileDataLogic();

        System.out.print("CIF Number: ");CIFNumber=in.nextDouble();
        if(!dataLogic.checkCIFNumber(connection,CIFNumber)){
            CIFNumber=0;
        }
        return CIFNumber;
    }
    public boolean checkCustomerByCIFNumber(Connection connection,double CIFNumber){
        ProfileDataLogic dataLogic = new ProfileDataLogic();
        return dataLogic.isCustomerPresent(connection,CIFNumber);
    }
    public void deActivateEmployee() {
        double employeeId;

        ProfileDataLogic dataLogic = new ProfileDataLogic();
        employeeId=getEmployeeId();

        try{
            if(dataLogic.updateEmployeeDeactivate(employeeId)){
                System.out.println("Employee Deactivated Successfully.");
            }else{
                System.out.println("Employee Deactivation Failed.");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public Customer customerProfileUpdate(){
        Customer customer;
        Address address;
        boolean updateAddress;

        ProfileDataLogic dataLogic = new ProfileDataLogic(customerAccountNumbers,loggedInCustomer);
        NavigateLogic navigate = new NavigateLogic();

        customer=(Customer) getProfileInput(1,false);
        System.out.print("Press 1 to Update Customer Address Details or 0: ");updateAddress=in.nextInt()==1;
        if(updateAddress){
            address=navigate.getAddressInfo(false);
            customer.setAddress(address);
        }
        if(dataLogic.updateCustomerProfile(customer)){
            System.out.println("Customer Profile Updated Successfully.");
        }else{
            System.out.println("Customer Profile Update Failed.");
        }

        return customer;
    }
    public Employee employeeProfileUpdate(){
        Employee employee;
        Address address;
        double employeeId;
        boolean updateAddress;

        ProfileDataLogic dataLogic = new ProfileDataLogic();
        NavigateLogic navigate = new NavigateLogic();
        employeeId=getEmployeeId();

        employee=(Employee) getProfileInput(3,false);
        System.out.print("Press 1 to Update Employee Address Details or 0: ");updateAddress=in.nextInt()==1;
        if(updateAddress){
            address=navigate.getAddressInfo(false);
            employee.setAddress(address);
        }
        if(dataLogic.updateEmployeeProfile(employee,employeeId)){
            System.out.println("Employee Profile Updated Successfully.");
        }else{
            System.out.println("Employee Profile Update Failed.");
        }

        return employee;
    }
    private double getEmployeeId(){
        double employeeId = 0;

        System.out.print("Enter Employee Id: ");employeeId=in.nextDouble();

        return employeeId;
    }
    private UserCredential getUserCredential(){
        UserCredential userCredential;
        String emailId,password;

        System.out.print("Email Id: ");emailId=in.next();
        System.out.print("Password: ");password=in.next();

        userCredential = new UserCredential(emailId,password);

        if(!isValidUserCredential(userCredential)) userCredential=null;

        return userCredential;
    }
    private User getProfileInput(int profileCode,boolean validateInput){
        User user=null;

        String firstName,middleName,lastName,emailId = null,gender,password = null,mobileNumber = null;
        int age;

        try{
            System.out.print("First Name: ");firstName=reader.readLine();
            System.out.print("Middle Name: ");middleName=reader.readLine();
            System.out.print("Last Name: ");lastName=reader.readLine();
            if(validateInput){
                System.out.print("Email Id: ");emailId=reader.readLine();
            }
            System.out.print("Gender: ");gender=reader.readLine();
            System.out.print("Age: ");age= in.nextInt();
            if(validateInput){
                System.out.print("Mobile Number: ");mobileNumber=reader.readLine();
                System.out.print("Password: ");password=reader.readLine();
            }

            switch (profileCode){
                case 1:{
                    String PANNumber,CKYCVerificationDocument,CKYCVerificationId;

                    System.out.print("PAN Number: ");PANNumber=reader.readLine();
                    System.out.print("CKYC Verification Document: ");CKYCVerificationDocument=reader.readLine();
                    System.out.print("CKYC Verification Id: ");CKYCVerificationId=reader.readLine();

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
                    boolean isActive = false;

                    System.out.print("Employee Designation: ");employeeDesignation=reader.readLine();
                    System.out.print("Employee CTC: ");employeeCTC=in.nextDouble();
                    System.out.print("Year Of Experience: ");yearOfExperience=in.nextInt();
                    if(validateInput){
                        System.out.print("Is Active: ");isActive=in.nextBoolean();
                    }

                    user = new Employee(firstName,middleName,lastName,emailId,gender,age,mobileNumber,password,employeeDesignation,employeeCTC,yearOfExperience,isActive);
                    break;
                }
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

         if(validateInput && user!=null &&!isValidProfile(user)) user=null;

        return user;
    }
    private Nominee getNomineeInput(){
         Nominee nominee;

        String firstName,middleName,lastName,emailId,gender,CKYCVerificationDocument,CKYCVerificationId,mobileNumber;
        int age;

        System.out.print("First Name: ");firstName=in.next();
        System.out.print("Middle Name: ");middleName=in.next();
        System.out.print("Last Name: ");lastName=in.next();
        System.out.print("Email Id: ");emailId=in.next();
        System.out.print("Gender: ");gender=in.next();
        System.out.print("Age: ");age=in.nextInt();
        System.out.print("Mobile Number: ");mobileNumber=in.next();
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
        if(customer.getPANNumber()==null || !Pattern.matches(CommonConstant.PAN_REGEX, customer.getPANNumber())){
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
        if(employee.getEmployeeDesignation()==null || !Pattern.matches(CommonConstant.AT_LEAST_ONE_STRING_REGEX, employee.getEmployeeDesignation())){
            System.out.println("Employee Designation Must Contain At least One Words.");
            isValidEmployee=false;
        }
        if(!(employee.getEmployeeCTC()>=20000)){
            System.out.println("Employee CTC Must Be At least Greater Than 20000.");
            isValidEmployee=false;
        }

        return isValidEmployee;
    }
    private boolean isValidUserCredential(UserCredential userCredential){
        boolean isValid=true;

        if(!Pattern.matches(CommonConstant.EMAIL_ID_REGEX,userCredential.getEmailId())){
            System.out.println("Entered Email Id Format Is Invalid.");
            isValid=false;
        }

        return isValid;
    }
}