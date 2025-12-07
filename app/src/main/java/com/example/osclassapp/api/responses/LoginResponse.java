package com.example.osclassapp.api.responses;

import com.example.osclassapp.models.User;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("seconds")
    private double seconds;

    @SerializedName("blockId")
    private String blockId;

    @SerializedName("response")
    private User response;

    public boolean isSuccess() {
        return "OK".equals(status);
    }

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public double getSeconds() { return seconds; }
    public String getBlockId() { return blockId; }
    public User getUser() { return response; }
}