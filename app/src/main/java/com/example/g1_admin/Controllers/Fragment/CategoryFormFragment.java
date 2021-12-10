package com.example.g1_admin.Controllers.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.g1_admin.DBHelper.DBHelper;
import com.example.g1_admin.Model.Category;
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

public class CategoryFormFragment extends Fragment {
    private ImageView imageViewCategory;
    private EditText categoryFormName;
    private Category newCategory;
    private Bitmap image;
    private Button btnSetCategoryImage, btnAddCategory;
    private Uri imageUri;

    String name;
    String imagePath;
    DatabaseReference mDatabase;
    DBHelper dbHelper;
    StorageReference storageReference;

    public CategoryFormFragment(DatabaseReference mDatabase, DBHelper dbHelper) {
        this.mDatabase = mDatabase;
        this.dbHelper = dbHelper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_category_form, container, false);

        categoryFormName = root.findViewById(R.id.categoryFormName);
        btnSetCategoryImage = root.findViewById(R.id.btnSetCategoryImage);
        btnAddCategory = root.findViewById(R.id.btnAddCategory);
        imageViewCategory = root.findViewById(R.id.imageViewCategory);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadNewCategory();
            }
        });
        btnSetCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryImage();
            }
        });


        return root;
    }

    private void setCategoryImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;

        if(requestCode == 10 && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media
                        .getBitmap(getContext().getContentResolver(), imageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 20 && resultCode == RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");
        }

        if(bitmap != null){
            imageViewCategory.setImageBitmap(bitmap);
            image = bitmap;
        }
    }

    public void uploadNewCategory() {
        name = categoryFormName.getText().toString();

        // Image check
        if(image == null) {
            Toast.makeText(getContext(), "Category image is required", Toast.LENGTH_LONG).show();
            return;
        }

        // Name check
        if(name.isEmpty()) {
            categoryFormName.setError("Category name is required");
            categoryFormName.requestFocus();
            return;
        }
        uploadImage();
        dbHelper.addCategory(name, imagePath);
    }

    public void uploadImage(){
        //We declare the date formatter
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.FRANCE);

        //We declare the current date including seconds
        Date now = new Date();

        //We create the name of the file taking the category as a reference and adding the current date in the declared format.
        imagePath = "images/" + name + "_" + formatter.format(now);

        //We declare the fate of the images
        storageReference = FirebaseStorage.getInstance().getReference(imagePath);

        //Let's put the image inside the storage
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageViewCategory.setImageURI(null);
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