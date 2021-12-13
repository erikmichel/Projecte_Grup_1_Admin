package com.example.g1_admin.Adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
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
        Spinner spinner = findViewById(R.id.spinnerDetailOrder);

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

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.state, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapterSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == 1){
                    String categorySelected = ((String) adapterView.getItemAtPosition(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
}