package com.example.g1_admin.Controllers;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g1_admin.Moduls.food;
import com.example.g1_admin.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<food> array_food;
    private ArrayList<food> all_items;
    public RecyclerViewAdapter(ArrayList<food> arrN){

        array_food = arrN;
        all_items = new ArrayList<>();
        all_items.addAll(array_food);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.etiquetaNom.setText(array_food.get(position).getName());
        holder.etiquetacost.setText(array_food.get(position).getCost());
    }

    @Override
    public int getItemCount() {
        return array_food.size();
    }
    public void filter(String StrSearch){
        if(StrSearch.length()==0){
            array_food.clear();
            array_food.addAll(all_items);


        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<food> collect=array_food.stream()
                        .filter(i->i.getName().toLowerCase(Locale.ROOT).contains(StrSearch))
                        .collect(Collectors.toList());
                array_food.clear();
                array_food.addAll(collect);
            }else{
                array_food.clear();
                for(food i:all_items){
                    if(i.getName().toLowerCase(Locale.ROOT).contains(StrSearch)){
                        array_food.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();



    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView etiquetaNom;
        TextView etiquetacost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etiquetaNom = itemView.findViewById(R.id.name);
            etiquetacost= itemView.findViewById(R.id.Product_cost);
        }
    }
}

