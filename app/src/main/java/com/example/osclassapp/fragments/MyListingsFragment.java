package com.example.osclassapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.osclassapp.R;
import com.example.osclassapp.adapters.ListingsAdapter;
import com.example.osclassapp.api.ApiClient;
import com.example.osclassapp.api.OsclassApiService;
import com.example.osclassapp.api.responses.ListingsResponse;
import com.example.osclassapp.models.Listing;
import com.example.osclassapp.models.User; // Добавлен импорт
import com.example.osclassapp.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class MyListingsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvEmpty;
    private ListingsAdapter adapter;
    private List<Listing> myListings = new ArrayList<>();
    private OsclassApiService apiService;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_listings, container, false);

        sessionManager = new SessionManager(requireContext());
        apiService = ApiClient.getApiService();

        initViews(view);
        loadMyListings();

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        tvEmpty = view.findViewById(R.id.tvEmpty);

        adapter = new ListingsAdapter(myListings, listing -> onListingClick(listing)); // Исправлена ссылка на метод
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void loadMyListings() {
        if (!sessionManager.isLoggedIn()) {
            showEmptyState("Войдите в систему для просмотра ваших объявлений");
            return;
        }

        User currentUser = sessionManager.getCurrentUser(); // Теперь User распознается
        if (currentUser == null) {
            showEmptyState("Ошибка получения данных пользователя");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);

        Call<ListingsResponse> call = apiService.getUserListings(
                ApiClient.getApiKey(),
                "read",
                "search",
                "items",
                String.valueOf(currentUser.getId()),
                1
        );

        call.enqueue(new Callback<ListingsResponse>() {
            @Override
            public void onResponse(Call<ListingsResponse> call, Response<ListingsResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    ListingsResponse listingsResponse = response.body();
                    if (listingsResponse.isSuccess()) {
                        myListings.clear();
                        myListings.addAll(listingsResponse.getListings());
                        adapter.updateData(myListings);

                        if (myListings.isEmpty()) {
                            showEmptyState("У вас пока нет объявлений");
                        }
                    } else {
                        showEmptyState("Ошибка: " + listingsResponse.getMessage());
                    }
                } else {
                    showEmptyState("Ошибка загрузки объявлений");
                }
            }

            @Override
            public void onFailure(Call<ListingsResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showEmptyState("Ошибка сети: " + t.getMessage());
            }
        });
    }

    // Добавлен метод showEmptyState
    private void showEmptyState(String message) {
        tvEmpty.setText(message);
        tvEmpty.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    // Добавлен метод onListingClick
    private void onListingClick(Listing listing) {
        // Обработка клика на объявление
    }
}