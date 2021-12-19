package com.example.g1_admin.Controllers.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.g1_admin.DBHelper.DBHelper;
import com.example.g1_admin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class DishFormFragment extends Fragment {
    TextView name, description, price;
    ImageView image;
    String defaultImageIdentificator;

    String imagePath;
    String cat;

    Uri imageUri;

    StorageReference storageReference;

    DatabaseReference mDatabase;
    DBHelper dbHelper;

    public DishFormFragment(DatabaseReference mDatabase, DBHelper dbHelper) {
        this.mDatabase = mDatabase;
        this.dbHelper = dbHelper;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View formView = inflater.inflate(R.layout.fragment_dish_form, container, false);

        // Appbar title and subtitle
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Insert");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Dish");

        image = formView.findViewById(R.id.dishImage);
        image.setImageResource(R.drawable.pizza_generic);
        defaultImageIdentificator = image.getDrawable().toString();

        name = formView.findViewById(R.id.txtNameDish);
        description = formView.findViewById(R.id.txtDescription);
        price = formView.findViewById(R.id.txtPrice);

        Spinner spinner = formView.findViewById(R.id.spinner_categories);
        
        ArrayList<String> categories = new ArrayList<>();
        mDatabase.child("category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    categories.clear();
                    // Gets the child's from Order
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        String name = ds.child("categoryName").getValue().toString();

                        categories.add(name);
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, categories);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item); // The drop down view
                    spinner.setAdapter(spinnerArrayAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Do nothing
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String categorySelected = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button add_category = formView.findViewById(R.id.add_btn);
        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.alert_title);
                builder.setMessage(R.string.alert_missage);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //We check that the user has changed the default image
                        if (image.getDrawable().toString().equals(defaultImageIdentificator)) {
                            Toast.makeText(getContext(), getString(R.string.no_default_image), Toast.LENGTH_SHORT).show();
                        } else {
                            //We assign the value in String of the selected Item of the Spinner.
                            cat = spinner.getSelectedItem().toString();

                            //We upload the image to FireBase Storage.
                            uploadImage();

                            //We transform the currency value to Double
                            Double pr = Double.parseDouble(price.getText().toString());

                            //We call the method to create and upload a plate.
                            dbHelper.addDish(name.getText().toString(), imagePath, cat, description.getText().toString(), pr);
                        }
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Button changeImage = formView.findViewById(R.id.btnChangeImage);
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeImage();
            }
        });

        return formView;
    }

    private void changeImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Declared the bitmap
        Bitmap bitmap = null;

        if(requestCode == 10 && resultCode == RESULT_OK){

            //We assign the URI of the image
            imageUri = data.getData();

            try {
                //We assign the previously chosen image to our bitmap
                bitmap = MediaStore.Images.Media
                        .getBitmap(getContext().getContentResolver(), imageUri);


            }catch (Exception e){
                e.printStackTrace();
            }
        } else if (requestCode == 20 && resultCode == RESULT_OK){

            bitmap = (Bitmap) data.getExtras().get("data");

        }

        if(bitmap != null){
            //We update our ImageView for the chosen image.
            image.setImageBitmap(bitmap);
        }


    }

    private void uploadImage(){
        //We declare the date formatter
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.FRANCE);

        //We declare the current date cluding seconds
        Date now = new Date();

        //We create the name of the file taking the category as a reference and adding the current date in the declared format.
        imagePath = "images/" + name.getText().toString() + "_" + formatter.format(now);

        //We declare the fate of the images
        storageReference = FirebaseStorage.getInstance().getReference(imagePath);

        //Let's put the image inside the storage
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        image.setImageURI(null);
                        Toast.makeText(getContext(), getString(R.string.success_uploaded), Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), getString(R.string.failed_upload), Toast.LENGTH_SHORT).show();
            }
        });
    }
}