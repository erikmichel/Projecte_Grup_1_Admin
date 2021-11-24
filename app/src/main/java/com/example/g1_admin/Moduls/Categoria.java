package com.example.g1_admin.Moduls;

import java.io.Serializable;

public class Categoria implements Serializable {
    private String category;
    public Categoria(String ctr){
        this.category=ctr;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
