package com.example.osclassapp.api.responses;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("seconds")
    private double seconds;

    @SerializedName("blockId")
    private String blockId;

    @SerializedName("response")
    private Object response;

    public boolean isSuccess() {
        return "OK".equals(status);
    }

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public double getSeconds() { return seconds; }
    public String getBlockId() { return blockId; }
    public Object getResponse() { return response; }
}