package com.example.g1_admin.Model;

import java.io.Serializable;

public class Dish implements Serializable {

    String id;
    String imageName;
    String category;
    String name;
    String description;
    double price;
    private String PromotionDate;
    private String PromotionDiscount;

    public Dish(){

    }

    public Dish(String imageName, String category, String name, String description, double price) {
        this.imageName = imageName;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
    }


    public Dish(String id, String imageName, String category, String name, String description, double price) {
        this.id = id;
        this.imageName = imageName;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPromotionDate() {
        return PromotionDate;
    }

    public void setPromotionDate(String promotionDate) {
        PromotionDate = promotionDate;
    }

    public String getPromotionDiscount() {
        return PromotionDiscount;
    }

    public void setPromotionDiscount(String promotionDiscount) {
        PromotionDiscount = promotionDiscount;
    }
}
