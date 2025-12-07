package com.example.osclassapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.osclassapp.R;
import com.example.osclassapp.adapters.MainPagerAdapter;
import com.example.osclassapp.utils.SessionManager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        setupViewPager();
    }

    private void setupViewPager() {
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        MainPagerAdapter pagerAdapter = new MainPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Объявления");
                    break;
                case 1:
                    // Показываем "Мои объявления" только для авторизованных пользователей
                    if (sessionManager.isLoggedIn()) {
                        tab.setText("Мои объявления");
                    } else {
                        tab.setText("Избранное");
                    }
                    break;
                case 2:
                    if (sessionManager.isLoggedIn()) {
                        tab.setText("Профиль");
                    } else {
                        tab.setText("Войти");
                    }
                    break;
            }
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Скрываем пункт "Выйти" если пользователь не авторизован
        MenuItem logoutItem = menu.findItem(R.id.menu_logout);
        if (logoutItem != null) {
            logoutItem.setVisible(sessionManager.isLoggedIn());
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_listing) {
            if (sessionManager.isLoggedIn()) {
                startActivity(new Intent(this, AddListingActivity.class));
            } else {
                // Если не авторизован, переходим на экран входа
                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, "Для добавления объявления необходимо войти в систему", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (item.getItemId() == R.id.menu_logout) {
            sessionManager.logout();
            // Перезагружаем активность для обновления интерфейса
            recreate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}