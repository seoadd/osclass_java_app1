package com.example.osclassapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.osclassapp.R;
import com.example.osclassapp.api.ApiClient;
import com.example.osclassapp.api.OsclassApiService;
import com.example.osclassapp.api.requests.UserLogin;
import com.example.osclassapp.api.responses.LoginResponse;
import com.example.osclassapp.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister, tvSkip;
    private SessionManager sessionManager;
    private OsclassApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(OsclassApiService.class);

        // УБИРАЕМ автоматический переход для авторизованных пользователей
        // Пусть пользователь сам решает, хочет ли он войти

        initViews();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvSkip = findViewById(R.id.tvSkip);

        btnLogin.setOnClickListener(v -> loginUser());
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        // Добавляем кнопку "Пропустить" для гостевого режима
        tvSkip.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        OsclassApiService apiService = ApiClient.getApiService();
        Call<LoginResponse> call = apiService.loginUser(
                ApiClient.getApiKey(),  // key
                "read",                 // type
                "user",                 // object
                "login",                // action
                email,                  // email
                password,               // password
                1                       // onlyResponse
        );

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isSuccess()) {
                        // Сохраняем пользователя в сессии
                        sessionManager.setLogin(true, "", loginResponse.getUser());
                        Toast.makeText(LoginActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Ошибка сервера", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}