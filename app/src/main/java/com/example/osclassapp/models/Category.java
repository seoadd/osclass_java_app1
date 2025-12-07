package com.example.osclassapp.models;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    private int id;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("icon")
    private String icon;
    
    @SerializedName("parent_id")
    private int parentId;
    
    public Category() {}
    
    public Category(int id, String name, String icon, int parentId) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.parentId = parentId;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    
    public int getParentId() { return parentId; }
    public void setParentId(int parentId) { this.parentId = parentId; }
}