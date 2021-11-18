package com.example.g1_admin.Controllers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.g1_admin.Moduls.food;
import com.example.g1_admin.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentcat2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentcat2 extends Fragment {

    public fragmentcat2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragmentcat2, container, false);
        // Inflate the layout for this fragment
        ArrayList<food> data=new ArrayList();

        data.add(new food("product11","100","cat2"));
        data.add(new food("product12","100","cat2"));
        data.add(new food("product13","100","cat2"));
        data.add(new food("product14","100","cat2"));
        data.add(new food("product15","100","cat2"));
        data.add(new food("product16","100","cat2"));
        data.add(new food("product17","100","cat2"));
        data.add(new food("product18","100","cat2"));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCat2);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }
}