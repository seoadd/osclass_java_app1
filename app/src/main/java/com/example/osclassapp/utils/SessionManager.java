package com.example.osclassapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.osclassapp.models.User;

public class SessionManager {
    private static final String PREF_NAME = "OsclassApp";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_PHONE = "userPhone";
    private static final String KEY_TOKEN = "token";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn, String token, User user) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.putString(KEY_TOKEN, token);
        if (user != null) {
            editor.putInt(KEY_USER_ID, user.getId());
            editor.putString(KEY_USER_NAME, user.getName());
            editor.putString(KEY_USER_EMAIL, user.getEmail());
            editor.putString(KEY_USER_PHONE, user.getPhone() != null ? user.getPhone() : "");
        }
        editor.apply();
    }

    // Добавьте этот метод
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, "");
    }

    public User getCurrentUser() {
        if (!isLoggedIn()) return null;

        User user = new User();
        user.setId(pref.getInt(KEY_USER_ID, 0));
        user.setName(pref.getString(KEY_USER_NAME, ""));
        user.setEmail(pref.getString(KEY_USER_EMAIL, ""));
        user.setPhone(pref.getString(KEY_USER_PHONE, ""));

        return user;
    }

    // Добавьте этот метод
    public void logout() {
        editor.clear();
        editor.apply();
    }

    public void updateUserInfo(User user) {
        if (user != null) {
            editor.putString(KEY_USER_NAME, user.getName());
            editor.putString(KEY_USER_EMAIL, user.getEmail());
            editor.putString(KEY_USER_PHONE, user.getPhone() != null ? user.getPhone() : "");
            editor.apply();
        }
    }
}