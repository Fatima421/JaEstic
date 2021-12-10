package com.grup2.jaestic_user.Models;

import java.io.Serializable;

public class Dish implements Serializable {
    // Properties
    String description, imagePath, name;
    double price;

    // Constructors
    public Dish() { }

    public Dish(String description, String imagePath, String name, double price) {
        this.description = description;
        this.imagePath = imagePath;
        this.name = name;
        this.price = price;
    }

    // Getters
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    // Setters
    public void setDescription(String description) { this.description = description; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
}
