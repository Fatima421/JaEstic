package com.grup2.jaestic_user.Models;

import java.util.ArrayList;

public class Command {
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

/*
    Command
        1- Fatima421@gmail.com
            C/asdf
            Items
                1 - pizza 3 24
                2 - fasd
                3 - asdf

        2- Erik
            c/asdf
            Items
                1-asdf
                2  - asdf


 */