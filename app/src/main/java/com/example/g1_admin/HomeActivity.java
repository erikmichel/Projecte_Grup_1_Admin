package com.example.g1_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    // When return true navigationView will show
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.menu_Close);

        // Actionbar options
        actionBarDrawerToggle.syncState(); // Refresh menu button to get correct icon depending on state
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Shows drawer menu button for open and close
        drawerLayout.addDrawerListener(actionBarDrawerToggle); // Sets actionbar as listener for drawer menu

        // When menu option selected
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch(item.getItemId()) {
                    case R.id.nav_products:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_insert:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_promotion:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_ingredients:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        selectedFragment = new FormFragment();
                        break;
                    case R.id.nav_newUser:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_logout:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                getSupportFragmentManager().beginTransaction().add(R.id.home_layout, selectedFragment).commit();

                return true;
            }
        });

    }
}