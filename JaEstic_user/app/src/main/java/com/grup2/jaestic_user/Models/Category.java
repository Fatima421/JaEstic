package com.grup2.jaestic_user.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    // Properties
    String description, imagePath, name;
    ArrayList<Dish> foods;

    // Constructors
    public Category() { }

    public Category(String description, String imagePath, String name, ArrayList<Dish> foods) {
        this.description = description;
        this.imagePath = imagePath;
        this.name = name;
        this.foods = foods;
    }

    // Getters
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public String getName() { return name; }
    public ArrayList<Dish> getFoods() { return foods; }

    // Setters
    public void setDescription(String description) { this.description = description; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setName(String name) { this.name = name; }
    public void setFoods(ArrayList<Dish> dishes) { this.foods = foods; }

}
