package com.example.g1_admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class UserDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_detail, container, false);

        TextView txtFullname = root.findViewById(R.id.createdFullname);
        TextView txtEmail = root.findViewById(R.id.createdEmail);
        TextView txtPassword = root.findViewById(R.id.createdPassword);
        Button btnOk = root.findViewById(R.id.createdBtn);

        Bundle bundle = getArguments();

        txtFullname.setText((String) bundle.getSerializable("fullname"));
        txtEmail.setText((String) bundle.getSerializable("email"));
        txtPassword.setText((String) bundle.getSerializable("password"));

        AppCompatActivity app = (AppCompatActivity) root.getContext();
        RegisterFragment registerFragment = new RegisterFragment();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, registerFragment)
                        .commit();
            }
        });

        return root;
    }
}