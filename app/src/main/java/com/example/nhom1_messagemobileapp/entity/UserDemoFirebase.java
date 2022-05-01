package com.example.nhom1_messagemobileapp.entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserDemoFirebase {
    private String email;
    private String password;

    public UserDemoFirebase(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDemoFirebase() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
