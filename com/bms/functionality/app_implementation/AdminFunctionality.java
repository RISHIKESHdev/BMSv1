package com.bms.functionality.app_implementation;

import com.bms.functionality.app_interface.AdminInterface;
import com.bms.functionality.branch.BranchLogic;
import com.bms.functionality.profile.ProfileLogic;
import com.bms.people.User;

public class AdminFunctionality extends UserFunctionality implements AdminInterface {
    public AdminFunctionality(){

    }
    public AdminFunctionality(User admin){
        super(admin);
    }
    @Override
    public void addAdmin() {
        ProfileLogic profile = new ProfileLogic();
        profile.registerAdmin();
    }

    @Override
    public void addEmployee() {
        ProfileLogic profile = new ProfileLogic();
        profile.registerEmployee();
    }

    @Override
    public void deleteEmployee() {
        ProfileLogic profileLogic = new ProfileLogic();
        profileLogic.deActivateEmployee();
    }

    @Override
    public void updateEmployee() {
        ProfileLogic profileLogic = new ProfileLogic();
        profileLogic.employeeProfileUpdate();
    }

    @Override
    public void addBranch() {
        BranchLogic branchLogic = new BranchLogic();
        branchLogic.registerBranch();
    }

    @Override
    public void changeEmployeeBranch() {
        BranchLogic branchLogic =new BranchLogic();
        branchLogic.changeEmployeeBranch();
    }
}
