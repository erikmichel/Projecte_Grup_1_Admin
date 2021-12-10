package com.example.g1_admin.Controllers.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.g1_admin.Adapter.RecyclerViewAdapter;
import com.example.g1_admin.Model.Category;
import com.example.g1_admin.Model.Dish;
import com.example.g1_admin.R;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_category, container, false);


        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        Category category1 = (Category) bundle.getSerializable("category");
        Log.i("categoria","categoraia"+ category1.getCategoryName());
        ArrayList<Dish> data = new ArrayList();
        ArrayList<Dish> dataFilter = new ArrayList();
        SearchView searchItem = (SearchView) view.findViewById(R.id.searchView);
        data.add(new Dish("imagePlaceholder","Starter", "Fries", "Description", 5.99));
        data.add(new Dish("imagePlaceholder","Starter", "Fried Chicken", "Description", 5.99));
        data.add(new Dish("imagePlaceholder","Starter", "Deluxe Fries", "Description", 5.99));
        data.add(new Dish("imagePlaceholder","Starter", "Salad", "Description", 5.99));
        data.add(new Dish("imagePlaceholder","Starter", "Garlic Bread", "Description", 5.99));
        data.add(new Dish("imagePlaceholder","Pizza", "Margherita", "Description", 12.50));
        data.add(new Dish("imagePlaceholder","Pizza", "Pepperoni", "Description", 12.50));
        data.add(new Dish("imagePlaceholder","Pizza", "Hawaiian", "Description", 12.50));
        data.add(new Dish("imagePlaceholder","Pizza", "Buffalo", "Description", 12.50));
        data.add(new Dish("imagePlaceholder","Pizza", "Veggie", "Description", 12.50));
        data.add(new Dish("imagePlaceholder","Pizza", "Cheese", "Description", 12.50));
        data.add(new Dish("imagePlaceholder","Pizza", "Meat", "Description", 12.50));
        data.add(new Dish("imagePlaceholder","Drink", "Cocacola", "Description", 2.99));
        data.add(new Dish("imagePlaceholder","Drink", "Fanta", "Description", 2.99));
        data.add(new Dish("imagePlaceholder","Drink", "Water", "Description", 2.99));
        data.add(new Dish("imagePlaceholder","Drink", "Beer", "Description", 2.99));
        for(int i=0;i<data.size();i++){
            if((data.get(i).getCategory()).equals(category1.getCategoryName())){
                dataFilter.add(data.get(i));
            }
        }
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(dataFilter);
        recyclerView.setAdapter(adapter);
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
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;

    }
}