package com.bms.functionality.branch;

import com.bms.Main;
import com.bms.bank.Branch;
import com.bms.functionality.CommonConstant;

import java.sql.Connection;
import java.util.Scanner;

public class BranchLogic implements BranchInterface{
    private static final Scanner in = Main.globalIn;
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
