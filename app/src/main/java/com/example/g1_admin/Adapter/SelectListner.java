package com.example.g1_admin.Adapter;

import com.example.g1_admin.Moduls.Category;
import com.example.g1_admin.Moduls.Food;

public interface SelectListner {
    void onItemClicked(Category category);
    void onItemClicked(Food food);
}
