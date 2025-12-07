package com.example.osclassapp.api.responses;

import com.example.osclassapp.models.Category;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CategoriesResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("seconds")
    private double seconds;

    @SerializedName("blockId")
    private String blockId;

    @SerializedName("response")
    private List<Category> response;

    public boolean isSuccess() {
        return "OK".equals(status);
    }

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public double getSeconds() { return seconds; }
    public String getBlockId() { return blockId; }
    public List<Category> getCategories() {
        return response != null ? response : java.util.Collections.emptyList();
    }
}