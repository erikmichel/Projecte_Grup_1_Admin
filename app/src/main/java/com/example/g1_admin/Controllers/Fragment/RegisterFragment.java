package com.example.g1_admin.Controllers.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.g1_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterFragment extends Fragment {
    EditText registerFullname, registerEmail, registerPassword;
    Button registerButton;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_register, container, false);

        // Appbar title and subtitle
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Register");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Form");

        // Firestore init
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Views init
        registerFullname = root.findViewById(R.id.registerFullname);
        registerEmail = root.findViewById(R.id.registerEmail);
        registerPassword = root.findViewById(R.id.registerPassword);
        registerButton = root.findViewById(R.id.btnRegister);
        progressBar = root.findViewById(R.id.progressBar);

        // Register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(root);
            }
        });

        return root;
    }

    // Checks all fields are correct and valid and creates a new admin user in firebase
    public void registerUser(View view) {
        String fullName = registerFullname.getText().toString().trim();
        String email = registerEmail.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();

        // Fullname conditions
        if(fullName.isEmpty()) {
            registerPassword.setError(getString(R.string.fullname_required));
            registerPassword.requestFocus();
        }

        // Email conditions
        if(email.isEmpty()) {
            registerEmail.setError(getString(R.string.email_required));
            registerEmail.requestFocus();
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerEmail.setError(getString(R.string.provide_valid_email));
            registerEmail.requestFocus();
        }

        // Password conditions
        if(password.isEmpty()) {
            registerPassword.setError(getString(R.string.password_required));
            registerPassword.requestFocus();
        } else if(password.length() < 6) {
            registerPassword.setError(getString(R.string.min_password));
            registerPassword.requestFocus();
        }

        // Loading icon visible
        progressBar.setVisibility(View.VISIBLE);

        // Creation of user in firebase with email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    // Writes data of new user on database in order to set it as admin
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Map<String, String> user = new HashMap<>();
                            user.put("FullName", fullName);
                            user.put("UserEmail", email);
                            user.put("isAdmin", "1");

                            db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .set(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {

                                        // Prepares bundle to show new user details on a detailFragment
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            // If
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), getString(R.string.registered_success), Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                AppCompatActivity app = (AppCompatActivity) view.getContext();
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("fullname", fullName);
                                                bundle.putSerializable("email", email);
                                                bundle.putSerializable("password", password);
                                                RegisterDetailFragment registerDetailFragment = new RegisterDetailFragment();
                                                registerDetailFragment.setArguments(bundle);
                                                app.getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.fragmentContainer, registerDetailFragment)
                                                        .commit();
                                            } else {
                                                Toast.makeText(getContext(), getString(R.string.failed_register), Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        } else {
                            Toast.makeText(getContext(), getString(R.string.failed_register), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}