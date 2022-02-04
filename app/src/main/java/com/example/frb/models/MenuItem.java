package com.example.frb.models;

public class MenuItem {
    String imgUri;
    String uid;
    String name;
    String description;
    String price;
    String qty;
    String result;

    public MenuItem(){}

    public MenuItem(String imgUri, String uid, String name, String description, String price){
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUri = imgUri;
    }

    public MenuItem(String name, String price, String qty, String result) {
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.result = result;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
