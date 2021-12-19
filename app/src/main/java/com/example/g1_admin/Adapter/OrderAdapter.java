package com.example.g1_admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g1_admin.Model.Order;
import com.example.g1_admin.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private SelectListner listener;
    private ArrayList<Order> orders;

    public OrderAdapter(ArrayList<Order> orders, SelectListner listener, Context context) {
        this.orders = orders;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtOrderName.setText(orders.get(position).getUser());
        holder.txtOrderId.setText(orders.get(position).getId());
        holder.txtOrderDate.setText(orders.get(position).getDate());
        holder.txtOrderStatus.setText(orders.get(position).getStatus());
        switch(orders.get(position).getStatus()) {
            case "Received":
                holder.txtOrderStatus.setTextColor(context.getResources().getColor(R.color.greenStatus));
                break;
            case "In process":
                holder.txtOrderStatus.setTextColor(context.getResources().getColor(R.color.yellowStatus));
                break;
            case "Sent":
                holder.txtOrderStatus.setTextColor(context.getResources().getColor(R.color.redStatus));
                break;
        }
        holder.itemOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(orders.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout itemOrder;
        private TextView txtOrderName;
        private TextView txtOrderId;
        private TextView txtOrderDate;
        private TextView txtOrderStatus;

        public ViewHolder(@NonNull View view) {
            super(view);
            itemOrder= view.findViewById(R.id.orderItem);
            txtOrderName = view.findViewById(R.id.orderItemUser);
            txtOrderId = view.findViewById(R.id.orderItemId);
            txtOrderDate = view.findViewById(R.id.orderItemDate);
            txtOrderStatus = view.findViewById(R.id.orderItemStatus);
        }

    }

}
