package com.example.g1_admin.Controllers.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.g1_admin.Model.Category;
import com.example.g1_admin.R;

public class CategoryFormFragment extends Fragment {
    private ImageView imageViewCategory;
    private Category newCategory;
    private Bitmap image;
    private Button btnSetCategoryImage, btnAddCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_category_form, container, false);

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
            Uri uri;
            uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media
                        .getBitmap(getContext().getContentResolver(), uri);
            } catch (Exception e){
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
        if(image == null) {
            Toast.makeText(getContext(), "Image is needed", Toast.LENGTH_LONG).show();
        }
    }
}