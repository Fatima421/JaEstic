package com.grup2.jaestic_user.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    // Properties
    String description;
    String imagePath;
    String imagePathUsers;
    String name;
    ArrayList<Dish> foods;

    // Default constructor
    public Category() { }

    // Specific Constructor
    public Category(String description, String imagePath, String imagePathUsers, String name, ArrayList<Dish> foods) {
        this.imagePath = imagePath;
        this.imagePathUsers = imagePathUsers;
        this.name = name;
        this.description = description;
        this.foods = foods;
    }

    // Getters and Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePathUsers() { return imagePathUsers; }

    public void setImagePathUsers(String imagePathUsers) { this.imagePathUsers = imagePathUsers; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
