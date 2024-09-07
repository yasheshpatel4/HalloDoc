package com.example.hallodoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class dashboard extends AppCompatActivity {

    FloatingActionButton fab;
    BottomNavigationView b;
    ViewPager2 viewPager;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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
        cardView = findViewById(R.id.card_view);

        // Set up the Toolbar with the hamburger icon
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_menu_24); // Ensure ic_hamburger icon exists

        // Handle navigation icon click to toggle CardView visibility
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardView.getVisibility() == View.GONE) {
                    cardView.setVisibility(View.VISIBLE);
                } else {
                    cardView.setVisibility(View.GONE);
                }
            }
        });

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
                    return new consultations();
                case 2:
                    return new appointment();
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
