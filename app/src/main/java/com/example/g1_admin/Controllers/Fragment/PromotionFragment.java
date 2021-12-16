package com.example.g1_admin.Controllers.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.g1_admin.Controllers.Activity.HomeActivity;
import com.example.g1_admin.DBHelper.DBHelper;
import com.example.g1_admin.Model.Dish;
import com.example.g1_admin.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PromotionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PromotionFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    // FIREBASE
    DBHelper dbHelper;

    public PromotionFragment() {
    }

    public PromotionFragment(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // PROMOTION FRAGMENT ELEMENTS
    TextView txtDate;
    TextView txtProductName;
    EditText edtxtDiscount;
    Button btnAddPromotion;

    // DISH OBJECT
    Dish dish;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PromotionFragment newInstance(String param1, String param2) {
        PromotionFragment fragment = new PromotionFragment();
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
        View view = inflater.inflate(R.layout.fragment_promotion, container, false);
        // Inflate the layout for this fragment

        // SET FRAGMENT TITLE TO "PROMOTIONS"
        ((HomeActivity) getActivity()).setActionBarTitle("Promotions");

        // BUNDLE
        Bundle bundle = getArguments();
        dish = (Dish) bundle.getSerializable("Dish");

        // PROMOTION FRAGMENT ELEMENTS
        Button btnCalendar = view.findViewById(R.id.btnCalendar);
        txtDate = view.findViewById(R.id.txtDate);
        txtProductName = view.findViewById(R.id.txtProductName);
        txtProductName.setText(dish.getName());
        edtxtDiscount = view.findViewById(R.id.edtxtDiscount);
        btnAddPromotion = view.findViewById(R.id.btnAddPromotion);

        // CALENDAR BUTTON
        btnCalendar.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  showDatePickerDialog();
              }
          }
        );

        // ADD PROMOTION BUTTON
        btnAddPromotion.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  dish.setPromotionDate(txtDate.getText().toString());
                  dish.setPromotionDiscount(edtxtDiscount.getText().toString());

                  dbHelper.addPromotion(dish.getCategory(), dish.getId(), dish.getPromotionDate(), dish.getPromotionDiscount());
              }
            }
        );

        return view;
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        i1 = i1+1;
        String date = i2 + "/" + i1 + "/" + i;
        txtDate.setText(date);
    }
}