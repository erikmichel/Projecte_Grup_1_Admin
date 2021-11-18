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
 * Use the {@link fragmentcat1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentcat1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentcat1() {
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
    public static fragmentcat1 newInstance(String param1, String param2) {
        fragmentcat1 fragment = new fragmentcat1();
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
        View view=inflater.inflate(R.layout.fragment_fragmentcat1, container, false);
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
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCat1);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;

    }
}