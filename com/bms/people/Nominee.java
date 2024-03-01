package com.bms.people;

public class Nominee {
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailId;
    private String gender;
    private int age;
    private String mobileNumber;
    private String CKYCVerificationDocument;
    private String CKYCVerificationId;

    public Nominee(String firstName, String middleName, String lastName, String emailId, String gender, int age, String mobileNumber, String CKYCVerificationDocument, String CKYCVerificationId) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.gender = gender;
        this.age = age;
        this.mobileNumber = mobileNumber;
        this.CKYCVerificationDocument = CKYCVerificationDocument;
        this.CKYCVerificationId = CKYCVerificationId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCKYCVerificationDocument() {
        return CKYCVerificationDocument;
    }

    public void setCKYCVerificationDocument(String CKYCVerificationDocument) {
        this.CKYCVerificationDocument = CKYCVerificationDocument;
    }

    public String getCKYCVerificationId() {
        return CKYCVerificationId;
    }

    public void setCKYCVerificationId(String CKYCVerificationId) {
        this.CKYCVerificationId = CKYCVerificationId;
    }
}
