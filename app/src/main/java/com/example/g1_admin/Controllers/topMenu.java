package com.example.g1_admin.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.g1_admin.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class topMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setText("Home");
        BottomNavigationView bottomNav = findViewById(R.id.top_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragmentHome()).commit();
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_all:
                    titulo.setText("Home");
                    selectedFragment = new fragmentHome();
                    break;
                case R.id.nav_cat1:
                    titulo.setText("Cat1");
                    selectedFragment = new fragmentcat1();
                    break;
                case R.id.nav_cat2:
                    titulo.setText("Cat2");
                    selectedFragment = new fragmentcat2();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });
    }
}