package com.bms.functionality.branch;

import com.bms.Main;
import com.bms.bank.Branch;
import com.bms.functionality.CommonConstant;
import com.bms.functionality.account.AccountLogic;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class BranchLogic implements BranchInterface{
    private final Scanner in = Main.globalIn;
    private ArrayList<Double> customerAccountNumbers;
    public BranchLogic(ArrayList<Double> customerAccountNumbers){
        this.customerAccountNumbers=customerAccountNumbers;
    }
    public BranchLogic(){

    }
    public int getBranchId(Connection connection){
        int branchId=0;

        BranchDataLogic dataLogic = new BranchDataLogic();

        if(dataLogic.viewAllBranchId(connection)){
            System.out.print("Select a Branch Id: ");branchId=in.nextInt();
            if(!dataLogic.checkBranchIdPresent(connection,branchId)){
                System.out.println("Entered Branch Id Not Found.");
                branchId=0;
            }
        }else{
            System.out.println("No Branch Record Available.");
        }
        return branchId;
    }

    public boolean changeEmployeeBranch(){
        boolean isEmployeeBranchChanged=false;
        double employeeId;

        BranchDataLogic dataLogic = new BranchDataLogic();

        System.out.print("Employee Id: ");employeeId=in.nextDouble();
        if(dataLogic.updateEmployeeBranchMap(employeeId)){
            System.out.println("Employee Branch Updated Successfully.");
        }else{
            System.out.println("Employee Branch Update Failed.");
        }

        return isEmployeeBranchChanged;
    }
    public boolean changeCustomerBranch(){
        boolean isCustomerBranchChanged=false;

        BranchDataLogic dataLogic = new BranchDataLogic();

        if(dataLogic.updateCustomerBranchMap()){
            System.out.println("Customer Account Branch Updated Successfully.");
        }else{
            System.out.println("Customer Account Branch Update Failed.");
        }

        return isCustomerBranchChanged;
    }

    public boolean registerBranch(){
        boolean isBranchRegistered = false;

        BranchDataLogic dataLogic = new BranchDataLogic();
        Branch branch = getBranchInfo();

        if(dataLogic.insertBranchRecord(branch)){
            System.out.println("Branch IFSC Code: "+ branch.getIFSCCode());
            System.out.println("Branch Record Inserted.");
            isBranchRegistered=true;
        }else{
            System.out.println("Branch Record Insertion Failed.");
        }

        return isBranchRegistered;
    }

    private Branch getBranchInfo(){
        Branch branch;

        String branchName,bankCode;
        long mobileNumber;

        System.out.print("Branch Name: ");branchName=in.next();
        bankCode = CommonConstant.BANK_CODE;
        System.out.print("Branch Contact Number: ");mobileNumber=in.nextLong();

        branch =new Branch(branchName,bankCode,mobileNumber);

        if(!isValidBranchInfo(branch)) branch=null;

        return branch;
    }
    private boolean isValidBranchInfo(Branch branch){
        boolean isValidBranch=true;

        return isValidBranch;
    }
}
