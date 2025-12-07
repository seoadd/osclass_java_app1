package com.example.osclassapp.models;

import com.google.gson.annotations.SerializedName;

public class Listing {
    @SerializedName("pk_i_id")
    private int id;

    @SerializedName("s_title")
    private String title;

    @SerializedName("s_description")
    private String description;

    @SerializedName("f_price")
    private double price;

    @SerializedName("s_currency")
    private String currency;

    @SerializedName("s_city")
    private String location;

    @SerializedName("s_category_name")
    private String category;

    @SerializedName("fk_i_user_id")
    private int userId;

    @SerializedName("s_user_name")
    private String userName;

    @SerializedName("dt_pub_date")
    private String createdAt;

    @SerializedName("s_contact_email")
    private String contactEmail;

    @SerializedName("s_contact_phone")
    private String contactPhone;

    // Добавьте поле для изображения
    @SerializedName("s_image")
    private String image;

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCurrency() { return currency != null ? currency : "RUB"; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getLocation() { return location != null ? location : ""; }
    public void setLocation(String location) { this.location = location; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    // Добавьте геттер для изображения
    public String getImage() {
        return image != null ? image : "";
    }

    public void setImage(String image) { this.image = image; }
}