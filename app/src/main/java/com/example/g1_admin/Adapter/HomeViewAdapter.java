package com.example.g1_admin.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
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
import com.example.g1_admin.Model.Category;
import com.example.g1_admin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HomeViewAdapter extends RecyclerView.Adapter<HomeViewAdapter.ViewHolder> {

    private ArrayList<Category> array_Cat;
    private SelectListner lisnter;
    private Context context;
    public HomeViewAdapter(ArrayList<Category> cat, SelectListner lisnter, Context context){
        array_Cat = cat;
        this.lisnter = lisnter;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.etiquetaCat.setText(array_Cat.get(position).getCategoryName());
        holder.etiquetaCOntainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisnter.onItemClicked(array_Cat.get(position));
            }
        });

        // Image loader from firebase using glide (Asks firebase for image hosted url using imagePath to storage)
        StorageReference storageReference = FirebaseStorage.getInstance("gs://admin-987aa.appspot.com/").getReference();
        storageReference.child(array_Cat.get(position).getImagePath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context) // Context from getContext() in HomeFragment
                    .load(uri.toString())
                    .into(holder.etiquetaImatge);
                Log.i("IMAGEGLIDE", uri.toString());
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
        ImageView etiquetaImatge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etiquetaCat = itemView.findViewById(R.id.name);
            etiquetaCOntainer = itemView.findViewById(R.id.containerCat);
            etiquetaImatge = itemView.findViewById(R.id.imageView);
        }
    }
}

