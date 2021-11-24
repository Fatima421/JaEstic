package com.grup2.jaestic_user.Models;

import java.io.Serializable;

public class Dish implements Serializable {
    // Properties
    int id;
    String name, description, price, imgUrl;
    Category category;

    // Constructors
    public Dish() { }

    public Dish(int id, String imgUrl, String name, String description, String price, Category category) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    // Getters
    public int getId() { return id; }
    public String getImgUrl() { return imgUrl; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public Category getCategory() { return category; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setImgId(String imgUrl) { this.imgUrl = imgUrl; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(Category category) { this.category = category; }
    public void setPrice(String price) { this.price = price; }
}
