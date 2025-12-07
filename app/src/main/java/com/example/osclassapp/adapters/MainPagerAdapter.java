package com.example.osclassapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.osclassapp.fragments.*;
import com.example.osclassapp.utils.SessionManager;

public class MainPagerAdapter extends FragmentStateAdapter {
    private SessionManager sessionManager;

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        sessionManager = new SessionManager(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ListingsFragment(); // Все объявления - доступно всем
            case 1:
                if (sessionManager.isLoggedIn()) {
                    return new MyListingsFragment(); // Мои объявления для авторизованных
                } else {
                    return new FavoritesFragment(); // Избранное для гостей
                }
            case 2:
                if (sessionManager.isLoggedIn()) {
                    return new ProfileFragment(); // Профиль для авторизованных
                } else {
                    return new LoginFragment(); // Экран входа для гостей
                }
            default:
                return new ListingsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}