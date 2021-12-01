package com.example.g1_admin.Moduls;

import java.io.Serializable;
import java.util.ArrayList;

public class Food implements Serializable {
    private String Name;
    private String Cost;
    private String Category;
    private String ingredients;

    public Food(String name, String cost) {
        Name = name;
        Cost = cost;
    }
    public Food(String name, String cost, String category) {
        Name = name;
        Cost = cost;
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
