package com.grup2.jaestic_user.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Command implements Serializable {
    String email;
    ArrayList<CartItem> cartItem;

    public Command() {
    }

    public Command(String email, ArrayList<CartItem> cartItem) {
        this.email = email;
        this.cartItem = cartItem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<CartItem> getCartItem() {
        return cartItem;
    }

    public void setCartItem(ArrayList<CartItem> cartItem) {
        this.cartItem = cartItem;
    }
}