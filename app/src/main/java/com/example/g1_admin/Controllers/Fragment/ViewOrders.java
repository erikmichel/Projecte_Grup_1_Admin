package com.example.g1_admin.Controllers.Fragment;

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
import android.widget.TextView;

import com.example.g1_admin.Adapter.OrderAdapter;
import com.example.g1_admin.Adapter.RecyclerViewAdapter;
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

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewOrders);
        OrderAdapter adapter = new OrderAdapter(orders);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return view;

    }

}