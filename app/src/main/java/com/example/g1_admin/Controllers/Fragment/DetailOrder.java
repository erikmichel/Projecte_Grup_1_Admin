package com.example.g1_admin.Controllers.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.g1_admin.R;

public class DetailOrder extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_order, container, false);

        ImageButton bntExit = view.findViewById(R.id.btnGoBack);
        TextView idOrderInfo = view.findViewById(R.id.txtDetailIDBlank);
        TextView userNameInfo = view.findViewById(R.id.txtUserNameBlank);
        TextView quantityInfo = view.findViewById(R.id.txtQuantityBlank);
        TextView priceInfo = view.findViewById(R.id.txtOrderPriceBlank);
        Spinner spinnerState = view.findViewById(R.id.spinnerDetailOrder);

        // Button to go back to list page
        bntExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewOrders = new Intent(getContext(), ViewOrders.class);
                startActivity(viewOrders);
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getContext(), R.array.state, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerState.setAdapter(adapterSpinner);

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        return view;
    }

}