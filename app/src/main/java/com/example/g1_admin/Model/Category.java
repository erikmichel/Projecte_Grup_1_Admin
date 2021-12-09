package com.example.g1_admin.Model;

import java.io.Serializable;

public class Category implements Serializable {
    private String id;
    private String imageName;
    private String categoryName;

    public Category(String imageName, String name) {
        this.imageName = imageName;
        this.categoryName = name;
    }


    public Category(String id, String imageName, String name) {
        this.id = id;
        this.imageName = imageName;
        this.categoryName = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String name) {
        this.categoryName = name;
    }
}
