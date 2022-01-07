package com.example.quickfix;

import com.google.firebase.database.DatabaseReference;

public class User {

    public String firstname, lastname, email, role, branch;
    public DatabaseReference branchkey;

    public DatabaseReference getBranchkey() {
        return branchkey;
    }

    public void setBranchkey(DatabaseReference branchkey) {
        this.branchkey = branchkey;
    }

    public User(){}

    public User(String f, String l, String e, String r, String b){
        firstname = f;
        lastname = l;
        email = e;
        role = r;
        branch = b;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getBranch() {
        return branch;
    }
}
