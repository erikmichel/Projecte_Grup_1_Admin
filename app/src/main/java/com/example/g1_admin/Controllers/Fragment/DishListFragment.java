package com.example.g1_admin.Controllers.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.g1_admin.Adapter.DishRecyclerView;
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


public class DishListFragment extends Fragment implements SelectListner {

    DBHelper dbHelper;
    DatabaseReference mDatabase;

    private ArrayList<Dish> data = new ArrayList();

    private DishRecyclerView adapter;

    public DishListFragment() {
    }

    public DishListFragment(DBHelper dbHelper, DatabaseReference mDatabase) {
        this.dbHelper = dbHelper;
        this.mDatabase = mDatabase;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dish_list, container, false);

        Bundle bundle = getArguments();

        // Gets the selected category name
        String categoryName = (String) bundle.getSerializable("category");

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        SelectListner selectListner = this;

        DatabaseReference reference = FirebaseDatabase.getInstance("https://admin-987aa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("dish");
        // Gets all the nodes from a category from the firebase and adds them to an ArrayList
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    data.clear();
                    // Gets the child's from Order
                    for (DataSnapshot dataSnapshot: snapshot.child(categoryName).getChildren()) {

                        Log.i("getDishesFirebase", dataSnapshot.child("name").getValue().toString());

                        Dish d = dataSnapshot.getValue(Dish.class);
                        d.setId(dataSnapshot.getKey());
                        data.add(d);

                    }

                    // Sets the adapter for the RecyclerView
                    adapter = new DishRecyclerView(data, selectListner, getContext());
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Do nothing
            }
        });

        // Searcher that lets search items by name
        SearchView searchItem = (SearchView) view.findViewById(R.id.searchView);
        searchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }});

        // Divider for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return view;

    }

    @Override
    public void onItemClicked(Category category) {

    }

    @Override
    public void onItemClicked(Dish dish) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Dish", dish);
        PromotionFragment promotionFragment= new PromotionFragment(dbHelper, mDatabase);
        promotionFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, promotionFragment).commit();
    }

    @Override
    public void onItemClicked(Order order) {

    }
}