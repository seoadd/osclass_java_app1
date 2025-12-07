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
import com.example.osclassapp.activities.RegisterActivity;
import com.example.osclassapp.utils.SessionManager;

public class LoginFragment extends Fragment {
    
    private Button btnLogin, btnRegister;
    private TextView tvGuestMessage;
    private SessionManager sessionManager;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        
        sessionManager = new SessionManager(requireContext());
        
        btnLogin = view.findViewById(R.id.btnLogin);
        btnRegister = view.findViewById(R.id.btnRegister);
        tvGuestMessage = view.findViewById(R.id.tvGuestMessage);
        
        setupClickListeners();
        
        return view;
    }
    
    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
        });
        
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), RegisterActivity.class);
            startActivity(intent);
        });
    }
}