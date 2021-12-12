package com.example.g1_admin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g1_admin.Model.Order;
import com.example.g1_admin.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<Order> orders;

    public OrderAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtOrderName.setText(orders.get(position).getName());
        holder.txtFirebaseKey.setText(orders.get(position).getFirebaseKey());
        holder.txtOrderKey.setText(String.valueOf(orders.get(position).getKey()));
        holder.txtOrderID.setText(String.valueOf(orders.get(position).getId()));
        holder.txtOrderPrice.setText(String.valueOf(orders.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtOrderName;
        private TextView txtFirebaseKey;
        private TextView txtOrderKey;
        private TextView txtOrderID;
        private TextView txtOrderPrice;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.txtOrderName = (TextView) view.findViewById(R.id.txtOrderName);
            this.txtFirebaseKey = (TextView) view.findViewById(R.id.txtFirebaseKey);
            this.txtOrderKey = (TextView) view.findViewById(R.id.txtOrderKey);
            this.txtOrderID = (TextView) view.findViewById(R.id.txtOrderID);
            this.txtOrderPrice = (TextView) view.findViewById(R.id.txtOrderPrice);
        }

    }

}
