package com.example.g1_admin.Adapter;

import com.example.g1_admin.Model.Category;
import com.example.g1_admin.Model.Dish;

public interface SelectListner {
    void onItemClicked(Category category);
    void onItemClicked(Dish dish);
}
