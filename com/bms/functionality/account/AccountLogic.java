package com.bms.functionality.account;

import com.bms.Application;
import com.bms.Main;
import com.bms.accounts.Account;
import com.bms.accounts.CurrentAccount;
import com.bms.accounts.FixedDepositAccount;
import com.bms.accounts.SavingAccount;
import com.bms.accounts.loan.Gold;
import com.bms.accounts.loan.Home;
import com.bms.accounts.loan.Loan;
import com.bms.functionality.CommonConstant;
import com.bms.people.Customer;
import com.bms.people.User;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class AccountLogic implements AccountInterface{
    private final Scanner in = Main.globalIn;
    private ArrayList<Double> customerAccountNumbers;
    private User loggedInUserInfo;
    public AccountLogic(ArrayList<Double> customerAccountNumbers){
        this.customerAccountNumbers=customerAccountNumbers;
    }
    public AccountLogic(ArrayList<Double> loggedInCustomerAccountNumbers, User loggedInUserInfo){
        this.loggedInUserInfo=loggedInUserInfo;
        this.customerAccountNumbers=loggedInCustomerAccountNumbers;
    }
    public AccountLogic(){

    }

    public int getAccountCode(){
        int cardCode;

        System.out.println("1. FD Account.");
        System.out.println("2. Current Account.");
        System.out.println("3. Saving Account.");

        System.out.print("Enter Account Code: ");cardCode=in.nextInt();

        return cardCode;
    }
    public FixedDepositAccount registerFDAccount(){
        FixedDepositAccount FDAccount;

        AccountDataLogic dataLogic = new AccountDataLogic(customerAccountNumbers,loggedInUserInfo);
        FDAccount = (FixedDepositAccount) getAccountInfo(2);

        if(FDAccount!=null && dataLogic.insertFixedDepositAccount(FDAccount)) {
            System.out.println("FD Account Number: "+FDAccount.getAccountNumber());
            System.out.println("FD Account Record Inserted.");
        }else{
            System.out.println("FD Account Creation Failed.");
        }

        return FDAccount;
    }
    public CurrentAccount registerCurrentAccount(){
        CurrentAccount currentAccount;

        AccountDataLogic dataLogic = new AccountDataLogic(customerAccountNumbers,loggedInUserInfo);
        currentAccount = (CurrentAccount) getAccountInfo(1);

        if(currentAccount!=null && dataLogic.insertCurrentAccount(currentAccount)) {
            System.out.println("Current Account Number: "+currentAccount.getAccountNumber());
            System.out.println("Current Account Record Inserted.");
        }else{
            System.out.println("Current Account Creation Failed.");
        }

        return currentAccount;
    }
    public SavingAccount registerSavingAccount(){
        SavingAccount savingAccount;

        AccountDataLogic dataLogic = new AccountDataLogic(customerAccountNumbers,loggedInUserInfo);
        savingAccount = (SavingAccount) getAccountInfo(3);

        if(savingAccount!=null && dataLogic.insertSavingAccount(savingAccount)) {
            System.out.println("Saving Account Number: "+savingAccount.getAccountNumber());
            System.out.println("Saving Account Record Inserted.");
        }else{
            System.out.println("Saving Account Creation Failed.");
        }

        return savingAccount;
    }
    public ArrayList<Double> getCustomerAccountNumberOnLogin(double CIFNumber){
        ArrayList<Double> customerAccountNumbers = new ArrayList<>();

        AccountDataLogic dataLogic = new AccountDataLogic();

        if(dataLogic.getCustomerAccountNumbers(customerAccountNumbers,CIFNumber)){
            System.out.println("Logged In Customer Account Numbers: ");
            for(double accountNumber: customerAccountNumbers){
                System.out.println(accountNumber);
            }
        }else{
            System.out.println("No Account Found For the Customer.");
            customerAccountNumbers=null;
        }

        return customerAccountNumbers;
    }
    public double getAccountNumberOnUserRequest(Connection connection){
        double accountNumber = 0;

        AccountDataLogic dataLogic = new AccountDataLogic();

        accountNumber=getCustomerAccountNumber();
        if(!(accountNumber!=0)){
            accountNumber=dataLogic.getAccountNumber(connection);
        }

        return accountNumber;
    }
    public double getCustomerAccountNumber(){
        double accountNumber = 0;

        if(!customerAccountNumbers.isEmpty()){
            for(Double customerAccountNumber: customerAccountNumbers){
                System.out.println("Logged In Customer Account Number:-");
                System.out.println(customerAccountNumber);
                System.out.print("Select From Above Account Number: ");accountNumber=in.nextDouble();
            }
        }

        return accountNumber;
    }

    private Account getAccountInfo(int accountCode){
        Account account=null;

        double currentBalance,availableBalance,creditScore;
        LocalDateTime accountInceptionDateTime;

        System.out.print("Initial Current Balance: ");currentBalance=in.nextDouble();
        availableBalance=currentBalance;
        creditScore= CommonConstant.INITIAL_CREDIT_SCORE;
        accountInceptionDateTime= Main.currentDateTime;

        switch(accountCode){
            case 1:{
                double overDraftLimit;

                overDraftLimit=CommonConstant.INITIAL_OVER_DRAFT_LIMIT;

                account=new CurrentAccount(currentBalance,availableBalance,creditScore,accountInceptionDateTime,overDraftLimit);

                break;
            }
            case 2:{
                int tenure;
                LocalDateTime matureDateTime;
                double rateOfInterest;

                System.out.print("FD Tenure: ");tenure=in.nextInt();
                matureDateTime=Main.currentDateTime.plusYears(tenure);
                System.out.print("FD Rate Of Interest: ");rateOfInterest=in.nextDouble();

                account=new FixedDepositAccount(currentBalance,availableBalance,creditScore,accountInceptionDateTime,tenure,matureDateTime,rateOfInterest);

                break;
            }
            case 3:{
                double minimumAccountBalance,withdrawalLimit,rateOfInterest;

                minimumAccountBalance=CommonConstant.MIN_SAVING_ACCOUNT_BALANCE;
                System.out.print("Saving Account Withdrawal Limit: ");withdrawalLimit=in.nextDouble();
                System.out.print("Saving Account Rate Of Interest: ");rateOfInterest=in.nextDouble();

                account=new SavingAccount(currentBalance,availableBalance,creditScore,accountInceptionDateTime,minimumAccountBalance,withdrawalLimit,rateOfInterest);

                break;
            }
        }

        if(account== null || !isValidAccount(account)) account = null;

        return account;
    }

    private boolean isValidAccount(Account account){
        boolean isValidAccount=true;

        if(!(account.getCurrentBalance()>=CommonConstant.MIN_ACCOUNT_BALANCE)){
            System.out.println("Account Current Balance must be At lest 500.");
            isValidAccount=false;
        }
        if(!(account.getAvailableBalance()>=CommonConstant.MIN_ACCOUNT_BALANCE)){
            System.out.println("Account Available Balance must be At lest 500.");
            isValidAccount=false;
        }

        if(account instanceof CurrentAccount) isValidAccount=isValidCurrentAccount((CurrentAccount) account);
        else if(account instanceof FixedDepositAccount) isValidAccount=isValidFDAccount((FixedDepositAccount) account);
        else if(account instanceof SavingAccount) isValidAccount=isValidSavingAccount((SavingAccount) account);

        return isValidAccount;
    }

    private boolean isValidCurrentAccount(CurrentAccount currentAccount){
        return true;
    }
    private boolean isValidFDAccount(FixedDepositAccount fixedDepositAccount){
        boolean isValidFDAccount=true;

        if(!(fixedDepositAccount.getTenure()>=1 && fixedDepositAccount.getTenure()<=10)){
            System.out.println("FD Tenure can be only between 1 to 10 years.");
            isValidFDAccount=false;
        }
        if(!(fixedDepositAccount.getRateOfInterest()>=2 && fixedDepositAccount.getRateOfInterest()<=30)){
            System.out.println("FD Rate Of Interest can only be between 2 to 30 years.");
            isValidFDAccount=false;
        }

        return isValidFDAccount;
    }
    private boolean isValidSavingAccount(SavingAccount savingAccount){
        boolean isValidSavingAccount=true;

        if(!(savingAccount.getRateOfInterest()>=2 && savingAccount.getRateOfInterest()<=10)){
            System.out.println("Saving Account Rate Of Interest can only be between 2 to 30 years.");
            isValidSavingAccount=false;
        }
        if(!(savingAccount.getWithdrawalLimit()>=100 && savingAccount.getWithdrawalLimit()<=500000)){
            System.out.println("Saving Account Withdrawal Limit can only be between 100 to 500000 years.");
            isValidSavingAccount=false;
        }

        return isValidSavingAccount;
    }


    public Loan registerGoldLoan(){
        Loan goldLoan;

        AccountDataLogic dataLogic = new AccountDataLogic(customerAccountNumbers);
        goldLoan = getLoanInfo(1);

        if(goldLoan!=null && dataLogic.insertGoldLoan((Gold)goldLoan)) {
            System.out.println("Gold Loan Id: "+goldLoan.getLoanId());
            System.out.println("Gold Loan Record Inserted.");
        }else{
            System.out.println("Gold Loan Creation Failed.");
        }

        return goldLoan;
    }
    public Loan registerHomeLoan(){
        Loan homeLoan;

        AccountDataLogic dataLogic = new AccountDataLogic();
        homeLoan = getLoanInfo(2);

        if(homeLoan!=null && dataLogic.insertHomeLoan((Home)homeLoan)) {
            System.out.println("Home Loan Id: "+homeLoan.getLoanId());
            System.out.println("Home Loan Record Inserted.");
        }else{
            System.out.println("Home Loan Creation Failed.");
        }

        return homeLoan;
    }

    private Loan getLoanInfo(int loanCode){
        Loan loan = null;

        switch(loanCode){
            case 1:{
                loan = getGoldLoanInfo();
                break;
            }
            case 3:{
                loan = getHomeInfo();
                break;
            }
        }

        if(loan==null || !isValidLoanInfo(loan)) loan = null;

        return loan;
    }

    private boolean isValidLoanInfo(Loan loan){
        boolean isValidLoan = true;

        if(CommonConstant.MINIMUM_LOAN_AMOUNT> loan.getLoanAmount()){
            System.out.println("Loan Amount Must Be At least "+ CommonConstant.MINIMUM_LOAN_AMOUNT);
            isValidLoan=false;
        }

        return isValidLoan;
    }

    private Loan getGoldLoanInfo(){
        Loan gold;
        String goldPurity;
        double weightInGram,loanAmount,interestRate,goldValuePerGram;

        System.out.print("Loan Amount: ");loanAmount=in.nextDouble();
        System.out.print("Loan Interest Rate: ");interestRate=in.nextDouble();
        System.out.print("Gold Purity: ");goldPurity=in.next();
        System.out.print("Gold Value Per Gram: ");goldValuePerGram=in.nextDouble();
        System.out.print("Gold Weigh In Gram: ");weightInGram=in.nextDouble();

        gold = new Gold(loanAmount,interestRate,Loan.LoanType.SECURED,goldPurity,goldValuePerGram,weightInGram);

        return gold;
    }
    private Loan getHomeInfo(){
        Loan home;
        int totalArea,builtUpArea,totalNoOfFloors,totalValue;
        double loanAmount,interestRate;

        System.out.print("Loan Amount: ");loanAmount=in.nextDouble();
        System.out.print("Loan Interest Rate: ");interestRate=in.nextDouble();
        System.out.print("Home Total Area: ");totalArea=in.nextInt();
        System.out.print("Home Built Up Area: ");builtUpArea=in.nextInt();
        System.out.print("Home Total No Of Floors: ");totalNoOfFloors=in.nextInt();
        System.out.print("Home Total Value: ");totalValue=in.nextInt();

        home = new Home(loanAmount,interestRate,Loan.LoanType.SECURED,totalArea,builtUpArea,totalNoOfFloors,totalValue);

        return home;
    }
}