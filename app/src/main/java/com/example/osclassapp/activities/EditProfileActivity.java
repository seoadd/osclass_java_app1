package com.example.osclassapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.osclassapp.R;
import com.example.osclassapp.utils.SessionManager;

public class EditProfileActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPhone;
    private Button btnSave;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sessionManager = new SessionManager(this);

        initViews();
        loadUserData();
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> updateProfile());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Редактировать профиль");
        }
    }

    private void loadUserData() {
        com.example.osclassapp.models.User user = sessionManager.getCurrentUser();
        if (user != null) {
            etName.setText(user.getName());
            etEmail.setText(user.getEmail());
            etPhone.setText(user.getPhone() != null ? user.getPhone() : "");
        }
    }

    private void updateProfile() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Заполните обязательные поля", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Реализовать обновление профиля через API
        Toast.makeText(this, "Профиль обновлен", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}