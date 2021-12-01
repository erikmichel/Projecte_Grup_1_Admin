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
import androidx.fragment.app.Fragment;

import com.example.g1_admin.DBHelper.DBHelper;
import com.example.g1_admin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FormFragment extends Fragment {

    ImageView image;

    String fileName;
    String cat;

    Uri imageUri;

    StorageReference storageReference;



    DatabaseReference mDatabase;
    DBHelper dbHelper;

    public FormFragment(DatabaseReference mDatabase, DBHelper dbHelper) {
        this.mDatabase = mDatabase;
        this.dbHelper = dbHelper;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View formView = inflater.inflate(R.layout.fragment_form, container, false);

        image = formView.findViewById(R.id.dishImage);
        image.setImageResource(R.drawable.pizza_generic);

        TextView name = formView.findViewById(R.id.txtNameDish);
        TextView description = formView.findViewById(R.id.txtDescription);
        TextView price = formView.findViewById(R.id.txtPrice);

        Spinner spinner = formView.findViewById(R.id.spinner_categories);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == 1){
                    String categorySelected = ((String) adapterView.getItemAtPosition(position));
                }
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
                builder.setMessage(R.string.alert_missage)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                //We assign the value in String of the selected Item of the Spinner.
                                cat = spinner.getSelectedItem().toString();

                                //We upload the image to FireBase Storage.
                                uploadImage();

                                //We transform the currency value to Double
                                Double pr = Double.parseDouble(price.getText().toString());

                                //We call the method to create and upload a plate.
                                dbHelper.addDish(name.getText().toString(), fileName, cat, description.getText().toString(), pr);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
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

            //We assign the URI of the imageWe assign the URI of the image
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

        //We declare the current date including seconds
        Date now = new Date();

        //We create the name of the file taking the category as a reference and adding the current date in the declared format.
        fileName = cat + "_" + formatter.format(now);

        //We declare the fate of the images
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);

        //Let's put the image inside the storage
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        image.setImageURI(null);
                        Toast.makeText(getContext(), "Successfully Uploaded", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        });
    }
}