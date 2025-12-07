package com.example.osclassapp.api;

import com.example.osclassapp.models.Listing;
import com.example.osclassapp.api.DirectArrayAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.lang.reflect.Type;
import java.util.List;

public class ApiClient {
    private static final String BASE_URL = "https://site.ru/oc-content/plugins/rest/";
    private static final String API_KEY = "Tokw0Dcg7tAD8oKiE8VsFdrLy1HPT5"; // Ваш ключ

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            // Создаем кастомный Gson для обработки прямых массивов
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter((Type) List.class, new DirectArrayAdapter<Listing>(Listing.class))
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static OsclassApiService getApiService() {
        return getClient().create(OsclassApiService.class);
    }

    public static String getApiKey() {
        return API_KEY;
    }
}