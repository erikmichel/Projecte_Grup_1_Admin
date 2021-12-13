package com.example.g1_admin.Controllers.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.g1_admin.Adapter.OrderAdapter;
import com.example.g1_admin.Adapter.RecyclerTouchListener;
import com.example.g1_admin.Adapter.RecyclerViewAdapter;
import com.example.g1_admin.Adapter.itemSelected;
import com.example.g1_admin.DBHelper.DBHelper;
import com.example.g1_admin.Model.Order;
import com.example.g1_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import javax.annotation.RegEx;

public class ViewOrders extends Fragment {

    DatabaseReference mDatabase;
    DBHelper dbHelper;

    private OrderAdapter orderAdapter;
    private RecyclerView orderRecyclerView;
    ArrayList<Order> orders = new ArrayList<>();

    public ViewOrders() {
    }

    public ViewOrders(DatabaseReference mDatabase, DBHelper dbHelper) {
        this.mDatabase = mDatabase;
        this.dbHelper = dbHelper;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_orders, container, false);

        ArrayList<Order> orders = dbHelper.getOrders();

        Spinner spinner = view.findViewById(R.id.spinner_categories);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getContext(),
                R.array.state, android.R.layout.simple_spinner_item);
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

        // RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewOrders);
        OrderAdapter adapter = new OrderAdapter(orders);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // Action when item is touched in RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Creates a new Intent to itemSelected class
                Intent intent;
                intent = new Intent(getContext(), itemSelected.class);

                // Gets the Hero object from the ArrayList by position
                // Pass each variable from the Hero into putExtra method
                intent.putExtra("ID", String.valueOf(orders.get(position).getId()));
                intent.putExtra("Name", orders.get(position).getName().toString());
                intent.putExtra("Price", String.valueOf(orders.get(position).getPrice()));

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                // Do nothing
            }
        }));



        return view;

    }

}