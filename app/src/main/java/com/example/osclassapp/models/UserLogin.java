package com.example.osclassapp.models;

import com.google.gson.annotations.SerializedName;

public class UserLogin {
    @SerializedName("email")
    private String email;
    
    @SerializedName("password")
    private String password;
    
    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    // Getters
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}