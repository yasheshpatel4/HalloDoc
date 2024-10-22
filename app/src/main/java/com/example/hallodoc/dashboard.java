package com.example.hallodoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    BottomNavigationView b;
    ViewPager2 viewPager;
    DrawerLayout drawerLayout; // Drawer layout for handling navigation
    NavigationView navigationView; // NavigationView for the drawer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Set the status bar color to white
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(android.graphics.Color.WHITE);
        }

        // Set the status bar text/icons to dark color for better visibility on white background
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // Initialize views
        b = findViewById(R.id.bottomNavigationView);
        viewPager = findViewById(R.id.view_pager);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Set up the Toolbar with the hamburger icon
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_menu_24); // Ensure ic_hamburger icon exists

        // Set up the NavigationView and the listener for the drawer
        navigationView.setNavigationItemSelectedListener(this);

        // Set up the ViewPager with the sections adapter
        viewPager.setAdapter(new ScreenSlidePagerAdapter(this));

        // Handle bottom navigation item clicks
        b.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                viewPager.setCurrentItem(0);
            } else if (item.getItemId() == R.id.Consult) {
                viewPager.setCurrentItem(1);
            } else if (item.getItemId() == R.id.Appointment) {
                viewPager.setCurrentItem(2);
            } else if (item.getItemId() == R.id.myhealth) {
                viewPager.setCurrentItem(3);
            }
            return true;
        });

        // Handle ViewPager page changes
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        b.setSelectedItemId(R.id.home);
                        break;
                    case 1:
                        b.setSelectedItemId(R.id.Consult);
                        break;
                    case 2:
                        b.setSelectedItemId(R.id.Appointment);
                        break;
                    case 3:
                        b.setSelectedItemId(R.id.myhealth);
                        break;
                }
            }
        });
    }

    // Navigation drawer item selection handler
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            viewPager.setCurrentItem(0);
        } else if (item.getItemId() == R.id.nav_settings) {
            // Navigate to settings
        } else if (item.getItemId() == R.id.nav_share) {
            // Handle share functionality
        } else if (item.getItemId() == R.id.nav_about) {
            // Navigate to About Us
        } else if (item.getItemId() == R.id.nav_logout) {
            // Handle logout
            performLogout();
        }

        // Close the navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // Logout function: clear back stack and go to login screen
    private void performLogout() {
        // Clear any stored user session data if needed
        // For example, if you're using SharedPreferences to store session data, clear it here
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear(); // Clear all data
        editor.apply();

        // Redirect to LoginActivity and clear back stack
        Intent intent = new Intent(dashboard.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // This will clear the back stack
        startActivity(intent);
        finish(); // Finish MainActivity so the user cannot come back to it
    }


    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new home();
                case 1:
                    return new appointment();
                case 2:
                    return new consultation_new();
                case 3:
                    return new myhealth();
                default:
                    return new home();
            }
        }

        @Override
        public int getItemCount() {
            return 4; // Total number of fragments
        }
    }
}
