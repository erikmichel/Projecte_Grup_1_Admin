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

import com.example.g1_admin.Adapter.HomeViewAdapter;
import com.example.g1_admin.Adapter.OrderAdapter;
import com.example.g1_admin.Adapter.RecyclerTouchListener;
import com.example.g1_admin.Adapter.RecyclerViewAdapter;
import com.example.g1_admin.Adapter.itemSelected;
import com.example.g1_admin.DBHelper.DBHelper;
import com.example.g1_admin.Model.Category;
import com.example.g1_admin.Model.ItemCart;
import com.example.g1_admin.Model.Order;
import com.example.g1_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import javax.annotation.RegEx;

public class OrderListFragment extends Fragment {

    DatabaseReference mDatabase;
    DBHelper dbHelper;

    private OrderAdapter orderAdapter;
    private RecyclerView orderRecyclerView;
    ArrayList<Order> orders;

    public OrderListFragment() {
    }

    public OrderListFragment(DatabaseReference mDatabase, DBHelper dbHelper) {
        this.mDatabase = mDatabase;
        this.dbHelper = dbHelper;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_orders, container, false);

        orderRecyclerView = view.findViewById(R.id.recyclerViewOrders);
        orders = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://admin-987aa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("order");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Order order = dataSnapshot.getValue(Order.class);
                    order.setId(dataSnapshot.getKey());
                    orders.add(order);
                }
                OrderAdapter orderAdapter = new OrderAdapter(orders, getContext());
                orderRecyclerView.setAdapter(orderAdapter);
                orderRecyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
                orderRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /*
        // Action when item is touched in RecyclerView
        orderRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
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

         */

        return view;

    }

}
