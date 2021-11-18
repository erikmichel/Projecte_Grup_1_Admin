package com.example.g1_admin.Controllers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.g1_admin.Moduls.food;
import com.example.g1_admin.R;

import java.util.ArrayList;

public class fragmentHome extends Fragment {

    public fragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayList<food> data=new ArrayList();
        data.add(new food("product1","100","cat1"));
        data.add(new food("product2","100","cat1"));
        data.add(new food("product3","100","cat1"));
        data.add(new food("product4","100","cat1"));
        data.add(new food("product5","100","cat1"));
        data.add(new food("product6","100","cat1"));
        data.add(new food("product7","100","cat1"));
        data.add(new food("product8","100","cat1"));
        data.add(new food("product11","100","cat2"));
        data.add(new food("product12","100","cat2"));
        data.add(new food("product13","100","cat2"));
        data.add(new food("product14","100","cat2"));
        data.add(new food("product15","100","cat2"));
        data.add(new food("product16","100","cat2"));
        data.add(new food("product17","100","cat2"));
        data.add(new food("product18","100","cat2"));

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        SearchView searchItem = (SearchView) view.findViewById(R.id.searchView);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewHome);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data);
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