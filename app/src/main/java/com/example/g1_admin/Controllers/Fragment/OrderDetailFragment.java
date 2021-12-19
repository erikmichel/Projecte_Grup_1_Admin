package com.example.g1_admin.Controllers.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.example.g1_admin.DBHelper.DBHelper;
import com.example.g1_admin.Model.Order;
import com.example.g1_admin.R;
import com.google.firebase.database.DatabaseReference;

public class OrderDetailFragment extends Fragment {

    DatabaseReference mDatabase;
    DBHelper dbHelper;

    int spinnerState;
    String categorySelected;

    public OrderDetailFragment(DatabaseReference mDatabase, DBHelper dbHelper) {
        this.mDatabase = mDatabase;
        this.dbHelper = dbHelper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);

        // Bundle
        Bundle bundle = getArguments();
        Order order = (Order) bundle.getSerializable("order");

        Log.i("Bundlefunsiona", order.getUser());
        Log.i("Bundlefunsiona", order.getId());
        Log.i("Bundlefunsiona", order.getDate());
        Log.i("Bundlefunsiona", order.getStatus());
        Log.i("Bundlefunsiona", order.getProducts().toString());

        ImageButton bntExit = view.findViewById(R.id.btnGoBack);
        // Button to go back to list page
        bntExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewOrders = new Intent(getContext(), OrderListFragment.class);
                startActivity(viewOrders);
            }
        });

        TextView idOrderInfo = view.findViewById(R.id.txtDetailIDBlank);
        idOrderInfo.setText(order.getId());

        TextView userNameInfo = view.findViewById(R.id.txtUserNameBlank);
        userNameInfo.setText(order.getUser());

        TextView dateOrder = view.findViewById(R.id.txtOrderDateBlank);
        dateOrder.setText(order.getDate());


        Spinner spinnerState = view.findViewById(R.id.spinnerDetailOrder);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getContext(), R.array.state, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerState.setAdapter(adapterSpinner);
        if(order.getStatus().equals("Received")){
            spinnerState.setSelection(0);
        } else if (order.getStatus().equals("In process")) {
            spinnerState.setSelection(1);
        } else {
            spinnerState.setSelection(2);
        }

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    categorySelected = ((String) adapterView.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button changeStateBtn = view.findViewById(R.id.changeStateButton);
        changeStateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.replaceState(order.getId(), categorySelected);
                Toast.makeText(getContext(), getString(R.string.changed_status), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}