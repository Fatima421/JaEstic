package com.grup2.jaestic_user.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
//propeties
    String name;
    String email;


    // Constructors
    public User() { }

    public User(String email) {
        this.email = email;
    }

    public User(String name, String email) {
        this.email = email;
        this.name = name;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.name = name; }
}
