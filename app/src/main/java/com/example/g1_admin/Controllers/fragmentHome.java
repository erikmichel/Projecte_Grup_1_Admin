package com.example.g1_admin.Controllers;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g1_admin.Moduls.Categoria;
import com.example.g1_admin.Moduls.food;
import com.example.g1_admin.R;

import java.util.ArrayList;
import androidx.fragment.app.Fragment;

public class fragmentHome extends Fragment implements SelectListner {

    public fragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayList<Categoria> dataCata=new ArrayList();
        dataCata.add(new Categoria("cat1"));
        dataCata.add(new Categoria("cat2"));
        dataCata.add(new Categoria("cat3"));
        dataCata.add(new Categoria("cat4"));
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        //SearchView searchItem = (SearchView) view.findViewById(R.id.searchView);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewHome);
        HomeViewAdapter adapter = new HomeViewAdapter(dataCata,this);
        recyclerView.setAdapter(adapter);
        /*searchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }});*/
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));

        return view;
    }

    @Override
    public void onItemClicked(Categoria categoria) {
        /*Intent Redrict= new Intent(getContext(), fragmentcat.class);
        startActivity(Redrict);
    */
        Bundle bundle = new Bundle();
        bundle.putSerializable("category", categoria);
        fragmentcat categoriaFragment= new fragmentcat();
        categoriaFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.fragment_container, categoriaFragment).commit();
    }

}