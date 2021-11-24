package com.grup2.jaestic_user.Models;

import java.util.ArrayList;

public class User {
    // Properties
    int id;
    String name;
    ArrayList<Dish> favourites;

    // Constructors
    public User() { }

    public User(int id, String name, ArrayList<Dish> favorites) {
        this.id = id;
        this.name = name;
        this.favourites = favorites;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public ArrayList<Dish> getFavourites() { return favourites; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setFavourites(ArrayList<Dish> favourites) { this.favourites = favourites; }

}
