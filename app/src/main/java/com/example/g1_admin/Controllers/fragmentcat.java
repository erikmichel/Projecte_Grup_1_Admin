package com.example.g1_admin.Controllers;

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
import android.widget.TextView;

import com.example.g1_admin.Moduls.Categoria;
import com.example.g1_admin.Moduls.food;
import com.example.g1_admin.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentcat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentcat extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentcat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentcat1.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentcat newInstance(String param1, String param2) {
        fragmentcat fragment = new fragmentcat();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cat, container, false);
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        Categoria categoria1 = (Categoria) bundle.getSerializable("category");
        Log.i("categoria","categoraia"+categoria1.getCategory());
        ArrayList<food> data=new ArrayList();
        ArrayList<food> dataFilter=new ArrayList();
        SearchView searchItem = (SearchView) view.findViewById(R.id.searchView);
        data.add(new food("product1","100","cat1"));
        data.add(new food("product2","100","cat1"));
        data.add(new food("product3","100","cat1"));
        data.add(new food("product4","100","cat1"));
        data.add(new food("product5","100","cat1"));
        data.add(new food("product6","100","cat4"));
        data.add(new food("product7","100","cat4"));
        data.add(new food("product8","100","cat2"));
        data.add(new food("product21","100","cat2"));
        data.add(new food("product22","100","cat2"));
        data.add(new food("product23","100","cat2"));
        data.add(new food("product24","100","cat2"));
        data.add(new food("product25","100","cat3"));
        data.add(new food("product26","100","cat3"));
        data.add(new food("product27","100","cat2"));
        data.add(new food("product28","100","cat2"));
        for(int i=0;i<data.size();i++){
            if((data.get(i).getCategory()).equals(categoria1.getCategory())){
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