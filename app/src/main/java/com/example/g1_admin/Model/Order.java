package com.example.g1_admin.Model;

public class Order {

    private String firebaseKey;
    private int key;
    private int id;
    private String name;
    private double price;

    public Order(String firebaseKey, int key, int id, String name, double price) {
        this.firebaseKey = firebaseKey;
        this.key = key;
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
