package com.example.frb.models;

import java.util.ArrayList;

public class CanteenModel {
    private String canteenUid;
    private ArrayList<MenuItem> menuItems;
    private PaymentOptions paymentOptions;
    private ProfileDetails profileDetails;
    private OperationalVariables operationalVariables;

    public CanteenModel(String canteenUid, ArrayList<MenuItem> menuItems, PaymentOptions paymentOptions, ProfileDetails profileDetails, OperationalVariables operationalVariables) {
        this.canteenUid = canteenUid;
        this.menuItems = menuItems;
        this.paymentOptions = paymentOptions;
        this.profileDetails = profileDetails;
        this.operationalVariables = operationalVariables;
    }

    public CanteenModel(String canteenUid, PaymentOptions paymentOptions, ProfileDetails profileDetails, OperationalVariables operationalVariables) {
        this.canteenUid = canteenUid;
        this.paymentOptions = paymentOptions;
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

    public PaymentOptions getPaymentOptions() {
        return paymentOptions;
    }

    public void setPaymentOptions(PaymentOptions paymentOptions) {
        this.paymentOptions = paymentOptions;
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
