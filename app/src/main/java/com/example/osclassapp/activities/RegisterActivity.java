package com.example.osclassapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.osclassapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.osclassapp.api.OsclassApiService;
import com.example.osclassapp.api.ApiClient;
import com.example.osclassapp.api.requests.UserRegistration;
import com.example.osclassapp.api.responses.ApiResponse;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etPhone;
    private Button btnRegister;
    private OsclassApiService apiService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        apiService = ApiClient.getClient().create(OsclassApiService.class);
        
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        btnRegister = findViewById(R.id.btnRegister);
        
        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните обязательные поля", Toast.LENGTH_SHORT).show();
            return;
        }

        OsclassApiService apiService = ApiClient.getApiService();
        Call<ApiResponse> call = apiService.registerUser(
                ApiClient.getApiKey(),  // key
                "insert",               // type
                "user",                 // object
                "register",             // action
                name,                   // name
                email,                  // email
                password,               // password
                phone,                  // phone
                1                       // onlyResponse
        );

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        Toast.makeText(RegisterActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Ошибка сервера", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}