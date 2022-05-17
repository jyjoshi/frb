package com.example.frb.models;

public class PaymentOptions {
    private BankDetails bankDetails;
    private String rzp;

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }

    public String getRzp() {
        return rzp;
    }

    public void setRzp(String rzp) {
        this.rzp = rzp;
    }
}
