package com.example.frb.models;

public class OrderedItem {
    String foodName;
    String qty;
    String price;
    String result;

    public OrderedItem(){}

    public OrderedItem(String foodName, String qty, String price, String result) {
        this.foodName = foodName;
        this.qty = qty;
        this.price = price;
        this.result = result;
    }


    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
