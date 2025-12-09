package com.example.osclassapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.osclassapp.R;
import com.example.osclassapp.api.ApiClient;
import com.example.osclassapp.api.OsclassApiService;
import com.example.osclassapp.api.responses.ApiResponse;
import com.example.osclassapp.api.responses.CategoriesResponse;
import com.example.osclassapp.models.Category;
import com.example.osclassapp.models.Listing;
import com.example.osclassapp.utils.SessionManager;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class AddListingActivity extends AppCompatActivity {
    private EditText etTitle, etDescription, etPrice, etLocation, etContactEmail, etContactPhone;
    private Spinner spCategory;
    private Button btnAddListing, btnSelectImage;
    private ImageView ivListingImage;
    private SessionManager sessionManager;
    private OsclassApiService apiService;
    private List<Category> categories;
    private Uri selectedImageUri; // Добавлена переменная

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getApiService();

        initViews();
        loadCategories();

        btnSelectImage.setOnClickListener(v -> selectImage());
        btnAddListing.setOnClickListener(v -> addListing());
    }

    private void initViews() {
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        etLocation = findViewById(R.id.etLocation);
        etContactEmail = findViewById(R.id.etContactEmail);
        etContactPhone = findViewById(R.id.etContactPhone);
        spCategory = findViewById(R.id.spCategory);
        btnAddListing = findViewById(R.id.btnAddListing);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        ivListingImage = findViewById(R.id.ivListingImage);

        // Устанавливаем email пользователя по умолчанию
        com.example.osclassapp.models.User currentUser = getCurrentUser();
        if (currentUser != null) {
            etContactEmail.setText(currentUser.getEmail());
            etContactPhone.setText(currentUser.getPhone());
        }
    }

    private com.example.osclassapp.models.User getCurrentUser() {
        if (sessionManager.isLoggedIn()) {
            com.example.osclassapp.models.User user = new com.example.osclassapp.models.User();
            // Получаем данные из SessionManager
            user.setEmail("user@example.com"); // Временное значение
            user.setPhone("+79999999999"); // Временное значение
            return user;
        }
        return null;
    }

    private void loadCategories() {
        Call<CategoriesResponse> call = apiService.getCategories(
                ApiClient.getApiKey(),  // key
                "read",                 // type
                "category",             // object
                "categories",           // action
                1                       // onlyResponse
        );

        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CategoriesResponse categoriesResponse = response.body();
                    if (categoriesResponse.isSuccess()) {
                        categories = categoriesResponse.getCategories();
                        setupCategorySpinner();
                    } else {
                        Toast.makeText(AddListingActivity.this, "Ошибка загрузки категорий: " + categoriesResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddListingActivity.this, "Ошибка сервера при загрузке категорий", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                Toast.makeText(AddListingActivity.this, "Ошибка сети при загрузке категорий: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupCategorySpinner() {
        if (categories == null) {
            categories = new ArrayList<>();
        }

        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData(); // Исправлено
            if (selectedImageUri != null) {
                Picasso.get()
                        .load(selectedImageUri)
                        .placeholder(R.drawable.placeholder)
                        .into(ivListingImage);
            }
        }
    }

    private void addListing() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String contactEmail = etContactEmail.getText().toString().trim();
        String contactPhone = etContactPhone.getText().toString().trim();

        // Получаем выбранную категорию
        String selectedCategory = "";
        int selectedCategoryId = 0;
        if (spCategory.getSelectedItem() != null) {
            selectedCategory = spCategory.getSelectedItem().toString();
            // Находим ID выбранной категории
            for (Category category : categories) {
                if (category.getName().equals(selectedCategory)) {
                    selectedCategoryId = category.getId();
                    break;
                }
            }
        }

        if (title.isEmpty() || description.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Заполните обязательные поля", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Введите корректную цену", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedCategoryId == 0) {
            Toast.makeText(this, "Выберите категорию", Toast.LENGTH_SHORT).show();
            return;
        }

        com.example.osclassapp.models.User currentUser = getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show();
            return;
        }

        // Создаем объявление
        Call<ApiResponse> call = apiService.createListing(
                ApiClient.getApiKey(),      // key
                "insert",                   // type
                "item",                     // object
                "add",                      // action
                title,                      // title
                description,                // description
                String.valueOf(price),      // price
                String.valueOf(selectedCategoryId), // category ID
                1                           // onlyResponse
        );

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        Toast.makeText(AddListingActivity.this, "Объявление добавлено", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddListingActivity.this, "Ошибка: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddListingActivity.this, "Ошибка сервера", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(AddListingActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
