package com.example.frb.models;

import java.util.ArrayList;
import java.util.List;

public class DataModel {

    private Bill bill;
    private ArrayList<OrderedItem> nestedList;
    private boolean isExpandable;

    public DataModel(Bill bill, ArrayList<OrderedItem> itemList) {
        this.bill = bill;
        this.nestedList = itemList;
        isExpandable = false;
    }

    public Bill getBill() {
        return bill;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public ArrayList<OrderedItem> getNestedList() {
        return nestedList;
    }



    public boolean isExpandable() {
        return isExpandable;
    }
}
