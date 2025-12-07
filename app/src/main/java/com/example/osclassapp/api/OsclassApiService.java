package com.example.osclassapp.api;

import com.example.osclassapp.api.responses.*;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OsclassApiService {

    // ==================== ПОИСК ОБЪЯВЛЕНИЙ ====================

    @GET("api.php")
    Call<ListingsResponse> getListings(
            @Query("key") String apiKey,
            @Query("type") String type,
            @Query("object") String object,
            @Query("action") String action,
            @Query("onlyResponse") int onlyResponse
    );

    @GET("api.php")
    Call<ListingsResponse> getListingsByCategory(
            @Query("key") String apiKey,
            @Query("type") String type,
            @Query("object") String object,
            @Query("action") String action,
            @Query("sCategory") String categoryId,
            @Query("onlyResponse") int onlyResponse
    );

    // ==================== КАТЕГОРИИ ====================

    @GET("api.php")
    Call<CategoriesResponse> getCategories(
            @Query("key") String apiKey,
            @Query("type") String type,
            @Query("object") String object,
            @Query("action") String action,
            @Query("onlyResponse") int onlyResponse
    );

    // ==================== ПОЛЬЗОВАТЕЛИ ====================

    @GET("api.php")
    Call<LoginResponse> loginUser(
            @Query("key") String apiKey,
            @Query("type") String type,
            @Query("object") String object,
            @Query("action") String action,
            @Query("email") String email,
            @Query("password") String password,
            @Query("onlyResponse") int onlyResponse
    );

    @GET("api.php")
    Call<ApiResponse> registerUser(
            @Query("key") String apiKey,
            @Query("type") String type,
            @Query("object") String object,
            @Query("action") String action,
            @Query("name") String name,
            @Query("email") String email,
            @Query("password") String password,
            @Query("phone") String phone,
            @Query("onlyResponse") int onlyResponse
    );

    // ==================== РАБОТА С ОБЪЯВЛЕНИЯМИ ====================

    @GET("api.php")
    Call<ApiResponse> createListing(
            @Query("key") String apiKey,
            @Query("type") String type,
            @Query("object") String object,
            @Query("action") String action,
            @Query("s_title") String title,
            @Query("s_description") String description,
            @Query("f_price") String price,
            @Query("fk_i_category_id") String categoryId,
            @Query("onlyResponse") int onlyResponse
    );

    @GET("api.php")
    Call<ListingsResponse> getUserListings(
            @Query("key") String apiKey,
            @Query("type") String type,
            @Query("object") String object,
            @Query("action") String action,
            @Query("userId") String userId,
            @Query("onlyResponse") int onlyResponse
    );

    // Альтернативный метод с большим количеством параметров
    @GET("api.php")
    Call<ApiResponse> createListingFull(
            @Query("key") String apiKey,
            @Query("type") String type,
            @Query("object") String object,
            @Query("action") String action,
            @Query("s_title") String title,
            @Query("s_description") String description,
            @Query("f_price") String price,
            @Query("fk_i_category_id") String categoryId,
            @Query("s_contact_name") String contactName,
            @Query("s_contact_email") String contactEmail,
            @Query("s_contact_phone") String contactPhone,
            @Query("s_city") String city,
            @Query("onlyResponse") int onlyResponse
    );

    // Добавьте этот метод в OsclassApiService
    @GET("api.php")
    Call<UserResponse> getUserProfile(
            @Query("key") String apiKey,
            @Query("type") String type,
            @Query("object") String object,
            @Query("action") String action,
            @Query("userId") String userId,
            @Query("onlyResponse") int onlyResponse
    );
}