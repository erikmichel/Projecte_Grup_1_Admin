package com.example.g1_admin.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.g1_admin.Model.Dish;
import com.example.g1_admin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Dish> array_food;
    private ArrayList<Dish> all_items;
    private SelectListner listener;
    private Context context;

    public RecyclerViewAdapter(ArrayList<Dish> arrN, SelectListner listener, Context context){
        array_food = arrN;
        all_items = new ArrayList<>();
        all_items.addAll(array_food);
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.etiquetaNom.setText(array_food.get(position).getName());

        holder.etiquetaContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(array_food.get(position));
            }
        });
        holder.etiquetaDetails.setText(array_food.get(position).getDescription());
        holder.etiquetaPrice.setText("" + array_food.get(position).getPrice() + "€");

        // Image loader from firebase using glide (Asks firebase for image hosted url using imagePath to storage)
        StorageReference storageReference = FirebaseStorage.getInstance("gs://admin-987aa.appspot.com/").getReference();
        storageReference.child(array_food.get(position).getImageName()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context) // Context from getContext() in HomeFragment
                        .load(uri.toString())
                        .into(holder.etiquetaImage);
                Log.i("IMAGEGLIDE", uri.toString());
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
                List<Dish> collect=array_food.stream()
                        .filter(i->i.getName().toLowerCase(Locale.ROOT).contains(StrSearch))
                        .collect(Collectors.toList());
                array_food.clear();
                array_food.addAll(collect);
            }else{
                array_food.clear();
                for(Dish i:all_items){
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
        ConstraintLayout etiquetaContainer;
        TextView etiquetaDetails;
        TextView etiquetaPrice;
        ImageView etiquetaImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etiquetaNom = itemView.findViewById(R.id.name);
            etiquetaContainer = itemView.findViewById(R.id.containerProduct);
            etiquetaDetails = itemView.findViewById(R.id.details);
            etiquetaPrice = itemView.findViewById(R.id.price);
            etiquetaImage = itemView.findViewById(R.id.imageView);
        }
    }
}

