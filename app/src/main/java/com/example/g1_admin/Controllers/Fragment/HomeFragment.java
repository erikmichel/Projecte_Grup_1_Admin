package com.example.g1_admin.Controllers.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.g1_admin.Adapter.HomeViewAdapter;
import com.example.g1_admin.Adapter.SelectListner;
import com.example.g1_admin.Controllers.Activity.HomeActivity;
import com.example.g1_admin.Controllers.Activity.LoginActivity;
import com.example.g1_admin.Moduls.Category;
import com.example.g1_admin.Moduls.Food;
import com.example.g1_admin.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements SelectListner {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayList<Category> dataCata=new ArrayList();
        dataCata.add(new Category("cat1"));
        dataCata.add(new Category("cat2"));
        dataCata.add(new Category("cat3"));
        dataCata.add(new Category("cat4"));
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        //SearchView searchItem = (SearchView) view.findViewById(R.id.searchView);

        // SET FRAGMENT TITLE TO "HOME"
        ((HomeActivity) getActivity()).setActionBarTitle("Home");

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
    public void onItemClicked(Category category) {
        /*Intent Redrict= new Intent(getContext(), fragmentcat.class);
        startActivity(Redrict);
    */
        Bundle bundle = new Bundle();
        bundle.putSerializable("category", category);
        CategoryFragment categoriaFragment= new CategoryFragment();
        categoriaFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, categoriaFragment).commit();
    }

    @Override
    public void onItemClicked(Food food) {

    }
}