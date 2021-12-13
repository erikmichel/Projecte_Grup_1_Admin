package com.example.g1_admin.Adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.g1_admin.R;

public class itemSelected extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_order);

        TextView id = findViewById(R.id.txtDetailID);
        TextView name = findViewById(R.id.txtDetailName);
        TextView price = findViewById(R.id.txtDetalPrice);

        ImageButton bnt = findViewById(R.id.btnGoBack);

        // Initialize bundle and get the extras
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();

        // We pass the values from the bundle into strings
        String str1 = bundle.getString("ID");
        String str2 = bundle.getString("Name");
        String str3 = bundle.getString("Price");

        // We set the string into the text views
        if (bundle != null) {
            id.setText(str1);
            name.setText(str2);
            price.setText(str3);
        }

        // Button to go back to list page
        bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}