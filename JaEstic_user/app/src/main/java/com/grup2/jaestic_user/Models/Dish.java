package com.grup2.jaestic_user.Models;

public class Dish {
    // Properties
    int id, imgId;
    String name, description, price;
    Category category;

    // Constructors
    public Dish() { }

    public Dish(int id, int imgId, String name, String description, String price, Category category) {
        this.id = id;
        this.imgId = imgId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    // Getters
    public int getId() { return id; }
    public int getImgId() { return imgId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public Category getCategory() { return category; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setImgId(int imgId) { this.imgId = imgId; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(Category category) { this.category = category; }
    public void setPrice(String price) { this.price = price; }
}
