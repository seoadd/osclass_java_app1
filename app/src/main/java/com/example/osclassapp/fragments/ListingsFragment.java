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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class ListingsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvError;
    private ListingsAdapter adapter;
    private List<Listing> listings = new ArrayList<>();
    private OsclassApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listings, container, false);

        apiService = ApiClient.getApiService();

        initViews(view);
        loadListings(); // Добавлен вызов метода

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        tvError = view.findViewById(R.id.tvError);

        adapter = new ListingsAdapter(listings, listing -> onListingClick(listing)); // Исправлена ссылка на метод
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    // Добавлен метод loadListings
    private void loadListings() {
        progressBar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);

        Call<ListingsResponse> call = apiService.getListings(
                ApiClient.getApiKey(),
                "read",
                "search",
                "items",
                1
        );

        call.enqueue(new Callback<ListingsResponse>() {
            @Override
            public void onResponse(Call<ListingsResponse> call, Response<ListingsResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    ListingsResponse listingsResponse = response.body();
                    if (listingsResponse.isSuccess()) {
                        listings.clear();
                        listings.addAll(listingsResponse.getListings());
                        adapter.updateData(listings);

                        if (listings.isEmpty()) {
                            showError("Объявления не найдены");
                        }
                    } else {
                        showError("Ошибка: " + listingsResponse.getMessage());
                    }
                } else {
                    showError("Ошибка сервера: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ListingsResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showError("Ошибка сети: " + t.getMessage());
            }
        });
    }

    // Добавлен метод showError
    private void showError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

    // Добавлен метод onListingClick
    private void onListingClick(Listing listing) {
        // Обработка клика на объявление
        // Intent intent = new Intent(getActivity(), ListingDetailActivity.class);
        // intent.putExtra("listing", listing);
        // startActivity(intent);
    }
}