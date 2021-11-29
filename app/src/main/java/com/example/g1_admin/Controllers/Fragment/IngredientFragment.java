package com.example.g1_admin.Controllers.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.g1_admin.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientFragment extends Fragment {

    // CHECKBOX
    CheckBox checkBox;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IngredientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientFragment newInstance(String param1, String param2) {
        IngredientFragment fragment = new IngredientFragment();
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
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        // Inflate the layout for this fragment

        // FORM FRAGMENT ELEMENTS
        EditText edtxtIngredient = view.findViewById(R.id.edtxtIngredient);
        Button btnAdd = view.findViewById(R.id.btn_add);
        LinearLayout linearLayout = view.findViewById(R.id.linear_layout);

        // ADD BUTTON
        btnAdd.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                    String ingredient = edtxtIngredient.getText().toString();

                    if(ingredient.isEmpty()) {
                        Toast.makeText(getActivity(), "Ingredient name cannot be empty", Toast.LENGTH_LONG).show();
                    } else {
                        checkBox = new CheckBox(getActivity().getApplicationContext());
                        checkBox.setText(ingredient);
                        linearLayout.addView(checkBox);
                    }
              }
          }
        );

        return view;
    }
}