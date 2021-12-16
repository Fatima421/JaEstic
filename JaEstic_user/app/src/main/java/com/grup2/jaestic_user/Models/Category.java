package com.grup2.jaestic_user.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    // Properties
    String description, imagePath, imagePathUsers, name, firebaseKey;

    // Default constructor
    public Category() { }

    // Specific Constructor
    public Category(String description, String imagePath, String imagePathUsers, String name) {
        this.imagePath = imagePath;
        this.imagePathUsers = imagePathUsers;
        this.name = name;
        this.description = description;
    }

    // Getters
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public String getImagePathUsers() { return imagePathUsers; }
    public String getName() { return name; }
    public String getFirebaseKey() { return firebaseKey; }

    // Setters
    public void setDescription(String description) { this.description = description; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setImageUserPath(String imagePathUsers) { this.imagePathUsers = imagePathUsers; }
    public void setName(String name) { this.name = name; }
    public void setFirebaseKey(String firebaseKey) { this.firebaseKey = firebaseKey; }
}
