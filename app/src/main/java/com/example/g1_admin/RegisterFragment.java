package com.example.g1_admin;

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

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        registerFullname = root.findViewById(R.id.registerFullname);
        registerEmail = root.findViewById(R.id.registerEmail);
        registerPassword = root.findViewById(R.id.registerPassword);
        registerButton = root.findViewById(R.id.btnRegister);
        progressBar = root.findViewById(R.id.progressBar);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(root);
            }
        });

        return root;
    }

    public void registerUser(View view) {
        String fullName = registerFullname.getText().toString().trim();
        String email = registerEmail.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();

        if(fullName.isEmpty()) {
            registerPassword.setError("Fullname is required!");
            registerPassword.requestFocus();
        }

        if(email.isEmpty()) {
            registerEmail.setError("Email is required!");
            registerEmail.requestFocus();
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerEmail.setError("Please provide valid email!");
            registerEmail.requestFocus();
        }

        if(password.isEmpty()) {
            registerPassword.setError("Password is required!");
            registerPassword.requestFocus();
        } else if(password.length() < 6) {
            registerPassword.setError("Min password length should be 6 characters!");
            registerPassword.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "User has been registered succesfully!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                AppCompatActivity app = (AppCompatActivity) view.getContext();
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("fullname", fullName);
                                                bundle.putSerializable("email", email);
                                                bundle.putSerializable("password", password);
                                                UserDetailFragment userDetailFragment = new UserDetailFragment();
                                                userDetailFragment.setArguments(bundle);
                                                app.getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.fragmentContainer, userDetailFragment)
                                                        .commit();
                                            } else {
                                                Toast.makeText(getContext(), "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        } else {
                            Toast.makeText(getContext(), "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}