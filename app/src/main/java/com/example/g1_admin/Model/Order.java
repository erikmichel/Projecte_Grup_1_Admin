package com.example.g1_admin.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private String id;

    public String user;
    public ArrayList<ItemCart> products;
    private String status;
    private String date;

    public Order() {
    }

    public Order(String user, ArrayList<ItemCart> products, String status, String date) {
        this.user = user;
        this.products = products;
        this.status = status;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<ItemCart> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ItemCart> products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}