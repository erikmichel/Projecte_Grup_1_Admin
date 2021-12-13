package com.example.g1_admin.Controllers.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.g1_admin.Adapter.HomeViewAdapter;
import com.example.g1_admin.Adapter.SelectListner;
import com.example.g1_admin.DBHelper.DBHelper;
import com.example.g1_admin.Model.Category;
import com.example.g1_admin.Model.Dish;
import com.example.g1_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements SelectListner {
    DBHelper dbHelper;
    ArrayList<Category> categories;
    RecyclerView recyclerView;

    public HomeFragment() {
    }

    public HomeFragment(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        // ActionBar Subtitle
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Categories");

        recyclerView = view.findViewById(R.id.recyclerViewHome);
        categories = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://admin-987aa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("category");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    categories.add(category);
                    Log.v("IMAGE", "onDataChange: " + category.getCategoryName());
                    HomeViewAdapter adapter = new HomeViewAdapter(categories, HomeFragment.this, getContext());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("IMAGE", "onDataChange: LSDFJOIDSFDSFIODSFODSFJOIDSFODSF");
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));

        return view;
    }

    @Override
    public void onItemClicked(Category category) {
        /*Intent Redrict= new Intent(getContext(), fragmentcat.class);
        startActivity(Redrict);
    */
        Bundle bundle = new Bundle();
        bundle.putSerializable("category", category.getCategoryName());
        CategoryFragment categoriaFragment= new CategoryFragment();
        categoriaFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, categoriaFragment).commit();

        // ActionBar subtitle
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(category.getCategoryName());
    }

    @Override
    public void onItemClicked(Dish dish) {

    }
}