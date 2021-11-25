package com.grup2.jaestic_user.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    // Properties
    int id;
    String name, imgUrl;
    ArrayList<Dish> dishes;

    // Constructors
    public Category() { }

    public Category(int id, String imgUrl, String name, ArrayList<Dish> dishes) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.dishes = dishes;
    }

    // Getters
    public int getId() { return id; }
    public String getImgUrl() { return imgUrl; }
    public String getName() { return name; }
    public ArrayList<Dish> getDishes() { return dishes; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
    public void setName(String name) { this.name = name; }
    public void setDishes(ArrayList<Dish> dishes) { this.dishes = dishes; }

}
