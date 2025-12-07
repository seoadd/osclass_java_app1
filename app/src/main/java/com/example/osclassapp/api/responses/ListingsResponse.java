package com.example.osclassapp.api.responses;

import com.example.osclassapp.models.Listing;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListingsResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("seconds")
    private double seconds;

    @SerializedName("blockId")
    private String blockId;

    @SerializedName("response")
    private List<Listing> response;

    public boolean isSuccess() {
        return "OK".equals(status);
    }

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public double getSeconds() { return seconds; }
    public String getBlockId() { return blockId; }
    public List<Listing> getListings() {
        return response != null ? response : java.util.Collections.emptyList();
    }
}