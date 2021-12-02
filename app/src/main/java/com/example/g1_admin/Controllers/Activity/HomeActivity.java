package com.example.g1_admin.Controllers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.g1_admin.Controllers.Fragment.CategoryFormFragment;
import com.example.g1_admin.Controllers.Fragment.DishFormFragment;
import com.example.g1_admin.Controllers.Fragment.HomeFragment;
import com.example.g1_admin.Controllers.Fragment.RegisterFragment;
import com.example.g1_admin.DBHelper.DBHelper;
import com.example.g1_admin.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    // Instance  of the Firebase Database
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://admin-987aa-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

    //
    DBHelper dbHelper = new DBHelper(mDatabase);

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
                switch(item.getItemId()) {
                    case R.id.nav_food:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
                        break;
                    case R.id.nav_insertDish:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new DishFormFragment(mDatabase, dbHelper)).commit();
                        break;
                    case R.id.nav_insertCategory:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CategoryFormFragment()).commit();
                        break;
                    case R.id.nav_newUser:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new RegisterFragment()).commit();
                        break;
                }
                return true;
            }
        });

    }

    public void swapFragment (Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }
}