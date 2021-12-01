package com.example.g1_admin.Controllers.Fragment;

import android.os.Bundle;

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
import com.example.g1_admin.Adapter.SelectListner;
import com.example.g1_admin.Moduls.Category;
import com.example.g1_admin.Moduls.Food;
import com.example.g1_admin.R;

import java.util.ArrayList;


public class CategoryFragment extends Fragment implements SelectListner {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_category, container, false);
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        Category category1 = (Category) bundle.getSerializable("category");
        Log.i("categoria","categoraia"+ category1.getCategory());
        ArrayList<Food> data=new ArrayList();
        ArrayList<Food> dataFilter=new ArrayList();
        SearchView searchItem = (SearchView) view.findViewById(R.id.searchView);
        data.add(new Food("product1","100","cat1"));
        data.add(new Food("product2","100","cat1"));
        data.add(new Food("product3","100","cat1"));
        data.add(new Food("product4","100","cat1"));
        data.add(new Food("product5","100","cat1"));
        data.add(new Food("product6","100","cat4"));
        data.add(new Food("product7","100","cat4"));
        data.add(new Food("product8","100","cat2"));
        data.add(new Food("product21","100","cat2"));
        data.add(new Food("product22","100","cat2"));
        data.add(new Food("product23","100","cat2"));
        data.add(new Food("product24","100","cat2"));
        data.add(new Food("product25","100","cat3"));
        data.add(new Food("product26","100","cat3"));
        data.add(new Food("product27","100","cat2"));
        data.add(new Food("product28","100","cat2"));
        for(int i=0;i<data.size();i++){
            if((data.get(i).getCategory()).equals(category1.getCategory())){
                dataFilter.add(data.get(i));
            }
        }
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(dataFilter,this);
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

    @Override
    public void onItemClicked(Category category) {

    }

    @Override
    public void onItemClicked(Food food) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("food", food);
        PromotionFragment promotionFragment= new PromotionFragment();
        promotionFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, promotionFragment).commit();
    }
}