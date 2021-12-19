package com.example.g1_admin.Controllers.Fragment;

import static android.app.Activity.RESULT_OK;

import static java.lang.Thread.sleep;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CategoryFormFragment extends Fragment {
    private ImageView imageViewCategory;
    String defaultImageIdentificator;
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

        // Views
        categoryFormName = root.findViewById(R.id.categoryFormName);
        btnSetCategoryImage = root.findViewById(R.id.btnSetCategoryImage);
        btnAddCategory = root.findViewById(R.id.btnAddCategory);
        imageViewCategory = root.findViewById(R.id.categoryImage);
        imageViewCategory.setImageResource(R.drawable.pizza_generic);
        defaultImageIdentificator = imageViewCategory.getDrawable().toString();

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dialog alert to confirm action
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.alert_title);
                builder.setMessage(R.string.alert_missage);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //We check that the user has changed the default image
                        if (imageViewCategory.getDrawable().toString().equals(defaultImageIdentificator)) {
                            Toast.makeText(getContext(), getString(R.string.no_default_image), Toast.LENGTH_SHORT).show();
                        } else {
                            uploadNewCategory();
                        }
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), getString(R.string.operation_cancelled), Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

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
            Toast.makeText(getContext(), getString(R.string.cat_image_required), Toast.LENGTH_LONG).show();
            return;
        }

        // Name check
        if(name.isEmpty()) {
            categoryFormName.setError(getString(R.string.cat_name_required));
            categoryFormName.requestFocus();
            return;
        }
        uploadImage();
        dbHelper.addCategory(name, imagePath);
    }

    public void uploadImage(){
        // We declare the date formatter
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.FRANCE);

        // We declare the current date including seconds
        Date now = new Date();

        // We create the name of the file taking the category as a reference and adding the current date in the declared format.
        imagePath = "images/" + name + "_" + formatter.format(now);

        // We declare the fate of the images
        storageReference = FirebaseStorage.getInstance().getReference(imagePath);

        // Let's put the image inside the storage
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageViewCategory.setImageURI(null);
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