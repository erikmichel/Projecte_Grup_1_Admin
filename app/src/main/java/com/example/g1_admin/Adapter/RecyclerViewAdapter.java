package com.example.g1_admin.Adapter;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g1_admin.Moduls.Food;
import com.example.g1_admin.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Food> array_food;
    private ArrayList<Food> all_items;
    private SelectListner lisnter;

    public RecyclerViewAdapter(ArrayList<Food> arrN, SelectListner lisnter){
        array_food = arrN;
        all_items = new ArrayList<>();
        all_items.addAll(array_food);
        this.lisnter = lisnter;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.etiquetaNom.setText(array_food.get(position).getName());

        holder.etiquetaCOntainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisnter.onItemClicked(array_food.get(position));
            }
        });

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
                List<Food> collect=array_food.stream()
                        .filter(i->i.getName().toLowerCase(Locale.ROOT).contains(StrSearch))
                        .collect(Collectors.toList());
                array_food.clear();
                array_food.addAll(collect);
            }else{
                array_food.clear();
                for(Food i:all_items){
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
        ConstraintLayout etiquetaCOntainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etiquetaNom = itemView.findViewById(R.id.name);
            etiquetaCOntainer = itemView.findViewById(R.id.containerProduct);
        }
    }
}

