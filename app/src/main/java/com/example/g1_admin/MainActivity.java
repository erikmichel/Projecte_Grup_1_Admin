package com.example.g1_admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CustomAuthActivity";

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private  EditText email;
    private EditText password;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        // If signed in application refers user to Menu class
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(this, MainMenu.class);
        Button btnLogin = findViewById(R.id.btnLogin);
        email = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);

        // Initialize Firebase Auth & Firebase Store
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        // Button to Login into the application with email & password from Firebase
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    public void registerNewUser() {
        fAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {

            // If user logged successfully shows Toast and refers user to Menu class
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Logged in Successfully.", Toast.LENGTH_SHORT).show();
                checkUserAccessLevel(authResult.getUser().getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {

            // On failure shows Toast
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Wrong Email or Password.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateUI(FirebaseUser user){
        Log.i(TAG, "aquí");
    }

    // Checks if the user is administrator
    private void checkUserAccessLevel(String uid) {

        DocumentReference df = fStore.collection("Users").document(uid);

        // Checks from the Firebase data if user is Administrator
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("LOGIN", "onSuccess: " + documentSnapshot.getData());

                // Identify user access level
                // If user is administrator redirects to Menu class
                if (documentSnapshot.getString("isAdmin") != null)
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
    }


}