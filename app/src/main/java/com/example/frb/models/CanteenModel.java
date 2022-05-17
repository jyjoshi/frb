package com.example.frb.models;

import java.util.ArrayList;

public class CanteenModel {
    private String canteenUid;
    private ArrayList<MenuItem> menuItems;
    private BankDetails bankDetails;
    private ProfileDetails profileDetails;
    private OperationalVariables operationalVariables;

    public CanteenModel(){}

    public CanteenModel(String canteenUid, ArrayList<MenuItem> menuItems, BankDetails bankDetails, ProfileDetails profileDetails, OperationalVariables operationalVariables) {
        this.canteenUid = canteenUid;
        this.menuItems = menuItems;
        this.bankDetails = bankDetails;
        this.profileDetails = profileDetails;
        this.operationalVariables = operationalVariables;
    }

    public String getCanteenUid() {
        return canteenUid;
    }

    public void setCanteenUid(String canteenUid) {
        this.canteenUid = canteenUid;
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }

    public ProfileDetails getProfileDetails() {
        return profileDetails;
    }

    public void setProfileDetails(ProfileDetails profileDetails) {
        this.profileDetails = profileDetails;
    }

    public OperationalVariables getOperationalVariables() {
        return operationalVariables;
    }

    public void setOperationalVariables(OperationalVariables operationalVariables) {
        this.operationalVariables = operationalVariables;
    }
}
