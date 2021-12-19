package com.example.g1_admin.Controllers.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.g1_admin.Adapter.CategoriesRecyclerView;
import com.example.g1_admin.Adapter.SelectListner;
import com.example.g1_admin.DBHelper.DBHelper;
import com.example.g1_admin.Model.Category;
import com.example.g1_admin.Model.Dish;
import com.example.g1_admin.Model.Order;
import com.example.g1_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CategoryListFragment extends Fragment implements SelectListner {
    DBHelper dbHelper;
    DatabaseReference mDatabase;
    ArrayList<Category> categories;
    RecyclerView recyclerView;

    public CategoryListFragment() {
    }

    public CategoryListFragment(DBHelper dbHelper, DatabaseReference mDatabase) {
        this.dbHelper = dbHelper;
        this.mDatabase = mDatabase;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_category_list, container, false);

        // ActionBar Subtitle
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Categories");

        recyclerView = view.findViewById(R.id.recyclerViewHome);
        categories = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://admin-987aa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("category");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categories.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    categories.add(category);
                }
                CategoriesRecyclerView adapter = new CategoriesRecyclerView(categories, CategoryListFragment.this, getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    @Override
    public void onItemClicked(Category category) {
        /*Intent Redrict= new Intent(getContext(), fragmentcat.class);
        startActivity(Redrict);
    */
        Bundle bundle = new Bundle();
        bundle.putSerializable("category", category.getCategoryName());
        DishListFragment categoriaFragment= new DishListFragment(dbHelper, mDatabase);
        categoriaFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, categoriaFragment).commit();

        // ActionBar subtitle
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(category.getCategoryName());
    }

    @Override
    public void onItemClicked(Dish dish) {

    }

    @Override
    public void onItemClicked(Order order) {

    }
}