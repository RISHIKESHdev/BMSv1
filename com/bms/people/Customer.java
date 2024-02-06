package com.bms.people;

import com.bms.accounts.Account;

import java.util.List;

public class Customer extends User {
    private long CIFNumber;
    private String PANNumber;
    private String CKYCVerificationDocument;
    private String CKYCVerificationId;
    private List<Account> linkedAccountList;

    public Customer(String firstName, String middleName, String lastName, String emailId, String gender, int age, long mobileNumber, String password, long CIFNumber, String PANNumber, String CKYCVerificationDocument, String CKYCVerificationId) {
        super(firstName, middleName, lastName, emailId, gender, age, mobileNumber, password);
        this.CIFNumber = CIFNumber;
        this.PANNumber = PANNumber;
        this.CKYCVerificationDocument = CKYCVerificationDocument;
        this.CKYCVerificationId = CKYCVerificationId;
    }

    public long getCIFNumber() {
        return CIFNumber;
    }

    public void setCIFNumber(long CIFNumber) {
        this.CIFNumber = CIFNumber;
    }

    public String getPANNumber() {
        return PANNumber;
    }

    public void setPANNumber(String PANNumber) {
        this.PANNumber = PANNumber;
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

    public List<Account> getLinkedAccountList() {
        return linkedAccountList;
    }

    public void setLinkedAccountList(Account account) {
        this.linkedAccountList.add(account);
    }
}