package com.example.g1_admin.Controllers.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.g1_admin.R;

public class DishFormFragment extends Fragment {

    ImageView image;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View formView = inflater.inflate(R.layout.fragment_dish_form, container, false);

        image = (ImageView) formView.findViewById(R.id.dishImage);
        image.setImageResource(R.drawable.pizza_generic);

        Spinner spinner = (Spinner) formView.findViewById(R.id.spinner_categories);

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

        Button add_category = (Button) formView.findViewById(R.id.add_btn);
        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.alert_title);
                builder.setMessage(R.string.alert_missage)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getContext(), "Added the category", Toast.LENGTH_LONG).show();
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

        Button changeImage = (Button) formView.findViewById(R.id.btnChangeImage);
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        return formView;
    }

    private void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;

        if(requestCode == 10 && resultCode == RESULT_OK){

            Uri uri;
            uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media
                        .getBitmap(getContext().getContentResolver(), uri);


            }catch (Exception e){
                e.printStackTrace();
            }
        } else if (requestCode == 20 && resultCode == RESULT_OK){

            bitmap = (Bitmap) data.getExtras().get("data");

        }

        if(bitmap != null){
            image.setImageBitmap(bitmap);
        }


    }
}