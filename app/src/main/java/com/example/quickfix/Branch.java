package com.example.quickfix;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Branch {

    public String address, phone, price;
    public DatabaseReference branchkey;

    public DatabaseReference getBranchkey() {
        return branchkey;
    }

    public void setBranchkey(DatabaseReference branchkey) {
        this.branchkey = branchkey;
    }

    public ArrayList<String> services;

    public Branch(){}

    public Branch(String a, String p, String price, ArrayList<String> s){
        address = a;
        phone = p;
        this.price = price;
        services = s;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getPrice() {
        return price;
    }

    public ArrayList<String> getServices() {
        return services;
    }
}
