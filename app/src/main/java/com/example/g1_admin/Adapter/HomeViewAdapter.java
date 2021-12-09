package com.example.g1_admin.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g1_admin.Model.Category;
import com.example.g1_admin.R;

import java.util.ArrayList;

public class HomeViewAdapter extends RecyclerView.Adapter<HomeViewAdapter.ViewHolder> {

    private ArrayList<Category> array_Cat;
    private SelectListner lisnter;
    public HomeViewAdapter(ArrayList<Category> cat, SelectListner lisnter){
        array_Cat = cat;
        this.lisnter=lisnter;
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
        holder.etiquetaCat.setText(array_Cat.get(position).getCategoryName());
        holder.etiquetaCOntainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisnter.onItemClicked(array_Cat.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return array_Cat.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView etiquetaCat;
        ConstraintLayout etiquetaCOntainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etiquetaCat = itemView.findViewById(R.id.name);
            etiquetaCOntainer = itemView.findViewById(R.id.containerCat);
        }
    }
}

