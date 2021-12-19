package com.example.g1_admin.Model;

public class ItemCart {
    private Dish dish;
    private int quantity;

    public ItemCart(){

    }

    public ItemCart(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}