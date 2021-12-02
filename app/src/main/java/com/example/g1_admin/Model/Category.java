package com.example.g1_admin.Model;

import java.io.Serializable;

public class Category implements Serializable {
    private String category;
    public Category(String ctr){
        this.category=ctr;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
