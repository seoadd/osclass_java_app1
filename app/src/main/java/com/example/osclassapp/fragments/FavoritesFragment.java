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

public class FavoritesFragment extends Fragment {
    
    private TextView tvEmptyFavorites;
    private Button btnLoginToSave;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        
        tvEmptyFavorites = view.findViewById(R.id.tvEmptyFavorites);
        btnLoginToSave = view.findViewById(R.id.btnLoginToSave);
        
        btnLoginToSave.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
        });
        
        return view;
    }
}