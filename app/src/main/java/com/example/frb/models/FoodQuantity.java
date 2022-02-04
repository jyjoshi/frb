package com.example.frb.models;

public class FoodQuantity {
    String foodName;
    int quantity;

    public FoodQuantity(String foodName, int quantity) {
        this.foodName = foodName;
        this.quantity = quantity;
    }

    public FoodQuantity() {
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getQuantity() {
        return quantity;
    }
}
