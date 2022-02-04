package com.example.frb.models;

public class Bill {
    String time;

    public Bill(String time, String totalPrice, String transactionId, String phone, Integer token) {
        this.time = time;
        this.totalPrice = totalPrice;
        this.transactionId = transactionId;
        this.phone = phone;
        this.token = token;
    }

    public Bill(String time, String totalPrice, String transactionId, String phone, String address, Integer token) {
        this.time = time;
        this.totalPrice = totalPrice;
        this.transactionId = transactionId;
        this.phone = phone;
        this.address = address;
        this.token = token;
    }

    String totalPrice;
    String transactionId;
    String phone;
    String address;
    Integer token;

    public Bill(){}

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public Bill(String time, String totalPrice, String transactionId, String phone) {
        this.time = time;
        this.totalPrice = totalPrice;
        this.transactionId = transactionId;
        this.phone = phone;
    }

    public Bill(String time, String totalPrice, String transactionId, String phone, String address) {
        this.time = time;
        this.totalPrice = totalPrice;
        this.transactionId = transactionId;
        this.phone = phone;
        this.address = address;
    }

    public String getAddress(){ return address; }

    public String getTime() {
        return time;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getPhone() {
        return phone;
    }
}


