package com.example.g1_admin.Adapter;

import com.example.g1_admin.Model.Category;
import com.example.g1_admin.Model.Dish;
import com.example.g1_admin.Model.Order;

public interface SelectListner {
    void onItemClicked(Category category);
    void onItemClicked(Dish dish);
    void onItemClicked(Order order);
}
