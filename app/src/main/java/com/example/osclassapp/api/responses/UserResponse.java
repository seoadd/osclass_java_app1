package com.example.osclassapp.api.responses;

import com.example.osclassapp.models.User;
import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("user")
    private User user;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}