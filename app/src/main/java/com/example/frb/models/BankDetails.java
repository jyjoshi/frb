package com.example.frb.models;

public class BankDetails {
    private String accountNumber;
    private String IFSC_Code;
    private String holderName;
    private String bankName;
    private String bankMMID;

    public BankDetails(){}

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIFSC_Code() {
        return IFSC_Code;
    }

    public void setIFSC_Code(String IFSC_Code) {
        this.IFSC_Code = IFSC_Code;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankMMID() {
        return bankMMID;
    }

    public void setBankMMID(String bankMMID) {
        this.bankMMID = bankMMID;
    }

    public BankDetails(String accountNumber, String IFSC_Code, String holderName) {
        this.accountNumber = accountNumber;
        this.IFSC_Code = IFSC_Code;
        this.holderName = holderName;
        this.bankMMID = null;
        this.bankMMID = null;
    }




}
