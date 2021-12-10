package com.example.g1_admin.Model;

import java.io.Serializable;

public class Category implements Serializable {
    private String categoryName;
    private String id;
    private String imagePath;

    public Category() {
    }

    public Category(String categoryName, String id, String imagePath) {
        this.categoryName = categoryName;
        this.id = id;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String name) {
        this.categoryName = name;
    }
}
