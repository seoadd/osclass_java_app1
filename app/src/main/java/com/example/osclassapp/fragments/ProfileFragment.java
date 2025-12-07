package com.example.osclassapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.osclassapp.R;
import com.example.osclassapp.activities.LoginActivity;
import com.example.osclassapp.api.ApiClient;
import com.example.osclassapp.api.OsclassApiService;
import com.example.osclassapp.api.responses.UserResponse;
import com.example.osclassapp.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private TextView tvUserName, tvUserEmail, tvUserPhone, tvListingsCount;
    private Button btnEditProfile, btnLogout;
    private SessionManager sessionManager;
    private OsclassApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = new SessionManager(requireContext());
        apiService = ApiClient.getApiService();

        initViews(view);
        loadUserProfile();

        return view;
    }

    private void initViews(View view) {
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        tvUserPhone = view.findViewById(R.id.tvUserPhone);
        tvListingsCount = view.findViewById(R.id.tvListingsCount);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnLogout = view.findViewById(R.id.btnLogout);

        btnEditProfile.setOnClickListener(v -> editProfile());
        btnLogout.setOnClickListener(v -> logout());

        // Скрываем кнопки если пользователь не авторизован
        if (!sessionManager.isLoggedIn()) {
            btnEditProfile.setVisibility(View.GONE);
            btnLogout.setVisibility(View.GONE);
            showGuestMode();
        }
    }

    private void loadUserProfile() {
        if (!sessionManager.isLoggedIn()) {
            showGuestMode();
            return;
        }

        Call<UserResponse> call = apiService.getUserProfile(
                ApiClient.getApiKey(),
                "read",
                "user",
                "profile",
                String.valueOf(sessionManager.getCurrentUser().getId()),
                1
        );
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    UserResponse userResponse = response.body();
                    updateProfileInfo(userResponse.getUser());
                } else {
                    // Если ошибка, показываем данные из SessionManager
                    showDataFromSession();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // При ошибке сети показываем данные из SessionManager
                showDataFromSession();
            }
        });
    }

    private void updateProfileInfo(com.example.osclassapp.models.User user) {
        if (user != null) {
            tvUserName.setText(user.getName());
            tvUserEmail.setText(user.getEmail());
            tvUserPhone.setText(user.getPhone() != null ? user.getPhone() : "Не указан");
        }
    }

    private void showDataFromSession() {
        com.example.osclassapp.models.User user = sessionManager.getCurrentUser();
        if (user != null) {
            tvUserName.setText(user.getName());
            tvUserEmail.setText(user.getEmail());
            tvUserPhone.setText(user.getPhone() != null ? user.getPhone() : "Не указан");
        }
    }

    private void showGuestMode() {
        tvUserName.setText("Гость");
        tvUserEmail.setText("Войдите в систему");
        tvUserPhone.setText("Не доступно");
        tvListingsCount.setText("0");
    }

    private void editProfile() {
        // Реализация редактирования профиля
        // Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        // startActivity(intent);
    }

    private void logout() {
        sessionManager.logout();
        // Обновляем интерфейс
        requireActivity().recreate();
    }
}