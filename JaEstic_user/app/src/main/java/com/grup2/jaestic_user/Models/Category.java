package com.grup2.jaestic_user.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    // Properties
    String description;
    String imagePath;
    String name;

    // Default constructor
    public Category() {}

    // Specific Constructor
    public Category(String description, String imagePath, String name) {
        this.imagePath = imagePath;
        this.name = name;
        this.description = description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
