package com.grup2.jaestic_user.Models;

import java.io.Serializable;

public class CartItem implements Serializable {
    // Properties
    Dish dish;
    int quantity;

    // Constructor
    public CartItem() {}

    public CartItem(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Dish getDish() {
        return dish;
    }
    public String getDescription() { return dish.description; }
    public String getImageUserPath() { return dish.imageUserPath; }
    public String getName() { return dish.name; }
    public double getPrice() { return dish.price; }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}