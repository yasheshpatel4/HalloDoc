package com.example.hallodoc;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class popupmenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Handle click on the hamburger menu
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_hamburger) {
                    showPopupMenu(toolbar.findViewById(R.id.menu_hamburger));
                    return true;
                }
                return false;
            }
        });
    }

    private void showPopupMenu(View anchor) {
        // Create a PopupMenu and inflate the menu resource
        PopupMenu popupMenu = new PopupMenu(popupmenu.this, anchor);
        popupMenu.getMenuInflater().inflate(R.menu.nav_menu, popupMenu.getMenu());

        // Handle menu item clicks
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    // Handle Home action
                    return true;
                } else if (itemId == R.id.nav_settings) {
                    // Handle Settings action
                    return true;
                } else if (itemId == R.id.nav_share) {
                    // Handle Share action
                    return true;
                } else if (itemId == R.id.nav_about) {
                    // Handle About Us action
                    return true;
                } else if (itemId == R.id.nav_logout) {
                    // Handle Logout action
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Show the PopupMenu
        popupMenu.show();
    }
}
