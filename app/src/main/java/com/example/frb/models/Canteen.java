package com.example.frb.models;

public class Canteen {
    private String uid;
    private String phone;
    private String email;
    private String password;
    private String name;
    private boolean isOpen;
    private String bankAccountNumber;
    private String bankIFSCCode;
    private String bankMMID;
    private String upiId;
    // Account Number, IFSC code, MMID
    // UPI ID
    // Paytm Account, via phone
    public Canteen(){}

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public void setBankIFSCCode(String bankIFSCCode) {
        this.bankIFSCCode = bankIFSCCode;
    }

    public void setBankMMID(String bankMMID) {
        this.bankMMID = bankMMID;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getUid() {
        return uid;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public String getBankIFSCCode() {
        return bankIFSCCode;
    }

    public String getBankMMID() {
        return bankMMID;
    }

    public String getUpiId() {
        return upiId;
    }

    public Canteen(String uid, String phone, String email, String password, String name, String bankAccountNumber, String bankIFSCCode, String bankMMID, String upiId){
        this.uid = uid;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.name = name;
        this.bankAccountNumber = bankAccountNumber;
        this.bankIFSCCode = bankIFSCCode;
        this.bankMMID = bankMMID;
        this.upiId = upiId;
        this.isOpen = false;
    }

    public Canteen(String uid, String phone, String email, String password, String name, String bankAccountNumber, String bankIFSCCode, String bankMMID){
        this.uid = uid;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.name = name;
        this.bankAccountNumber = bankAccountNumber;
        this.bankIFSCCode = bankIFSCCode;
        this.bankMMID = bankMMID;
        this.upiId = null;
        this.isOpen = false;
    }

    public Canteen(String uid, String phone, String email, String password, String name) {
        this.uid = uid;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.name = name;
        this.bankAccountNumber = null;
        this.bankIFSCCode = null;
        this.bankMMID = null;
        this.upiId = null;
        this.isOpen = false;
    }

    public Canteen(String phone, String name, String password) {
        this.uid = null;
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.bankAccountNumber = null;
        this.bankIFSCCode = null;
        this.bankMMID = null;
        this.upiId = null;
        this.email = null;
        this.isOpen =  false;
    }


}
