package com.example.g1_admin.Controllers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g1_admin.Controllers.Fragment.CategoryFormFragment;
import com.example.g1_admin.Controllers.Fragment.DishFormFragment;
import com.example.g1_admin.Controllers.Fragment.HomeFragment;
import com.example.g1_admin.Controllers.Fragment.RegisterFragment;
import com.example.g1_admin.DBHelper.DBHelper;
import com.example.g1_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBar actionBar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    View headerView;
    TextView headerUsername, headerEmail;

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

        // Header TextView
        headerView = navigationView.getHeaderView(0);
        headerUsername = headerView.findViewById(R.id.headerUsername);
        headerEmail = headerView.findViewById(R.id.headerEmail);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("FireStore", "DocumentSnapshot data: " + document.getData());
                        headerUsername.setText(document.get("FullName").toString());
                        headerEmail.setText(document.get("UserEmail").toString());
                    } else {
                        Log.d("FireStore", "No such document");
                    }
                } else {
                    Log.d("FireStore", "get failed with ", task.getException());
                }
            }
        });

        // Actionbar options
        actionBarDrawerToggle.syncState(); // Refresh menu button to get correct icon depending on state
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Shows drawer menu button for open and close
        getSupportActionBar().setTitle("Home");
        drawerLayout.addDrawerListener(actionBarDrawerToggle); // Sets actionbar as listener for drawer menu

        // When menu option selected
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_home:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment(dbHelper)).commit();
                        break;
                    case R.id.nav_insertDish:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new DishFormFragment(mDatabase, dbHelper)).commit();
                        break;
                    case R.id.nav_insertCategory:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CategoryFormFragment(mDatabase, dbHelper)).commit();
                        break;
                    case R.id.nav_newUser:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new RegisterFragment()).commit();
                        break;
                    case R.id.nav_logout:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setTitle(R.string.alert_logout_title);
                        builder.setMessage(R.string.alert_logout_message);
                        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences prefs= getSharedPreferences("SharedP", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putBoolean("login", false);
                                editor.commit();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }
                        });
                        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
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