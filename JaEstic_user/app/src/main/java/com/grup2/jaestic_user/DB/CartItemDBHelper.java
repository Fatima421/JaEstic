package com.grup2.jaestic_user.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.Models.Dish;
import com.grup2.jaestic_user.DB.CartItemDBContract.*;

import java.util.ArrayList;

public class CartItemDBHelper extends SQLiteOpenHelper {
    // Global and constant properties
    // Log
    public static final String TAGLOG = "Jaestic";
    public static final String CLOSEDDB = "Database is closed";
    public static final String EMPTYDB = "Database is empty";
    // Database
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contacts.db";

    // Table
    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + CartItemEntry.TABLE_NAME + "("
            + CartItemEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CartItemEntry.NAME + " TEXT, "
            + CartItemEntry.IMAGE_PATH + " TEXT, "
            + CartItemEntry.PRICE + " DOUBLE, "
            + CartItemEntry.QUANTITY + " INT)";

    // Queries
    public static final String SQL_SELECT_ALL = "SELECT * FROM " + CartItemEntry.TABLE_NAME;
    public static final String SQL_DELETE_ALL = "DELETE FROM " + CartItemEntry.TABLE_NAME;

    public CartItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_TABLE); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) { }

    // Creates CartItem object with its values and adds to the table.
    public void insertDish(SQLiteDatabase db, CartItem cartItem){
        // Check the bd is open
        if (db.isOpen()){
            // Creation of the register for insert object with the content values
            ContentValues values = new ContentValues();
            // Insert the incidence getting all values
            values.put(CartItemEntry.NAME, cartItem.getName());
            values.put(CartItemEntry.IMAGE_PATH, cartItem.getImageUserPath());
            values.put(CartItemEntry.PRICE, cartItem.getPrice());
            values.put(CartItemEntry.QUANTITY, cartItem.getQuantity());
            // SQLite execution (Insert row)
            db.insert(CartItemEntry.TABLE_NAME, null, values);
        } else {
            Log.i(TAGLOG, CLOSEDDB);
        }
    }

    // This function selects all the data from the CartItem table and creates a new cartItem object with the values
    public ArrayList<CartItem> getAllDishes(SQLiteDatabase db){
        ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
        Dish dish;
        // cursor to be able to add all the dishes from the db to the array list
        Cursor cursor = db.rawQuery(SQL_SELECT_ALL,null);
        // Checks if database is open
        if (db.isOpen()) {
            // Checks if cursor can move inside table
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String dishName = cursor.getString(1);
                    String dishImage = cursor.getString(2);
                    Double dishPrice = cursor.getDouble(3);
                    int quantity = cursor.getInt(4);
                    dish = new Dish("", "", dishImage, dishName, dishPrice);
                    // Add values to CartItem
                    cartItems.add(new CartItem(dish, quantity));
                } while (cursor.moveToNext());
            } else {
                Log.i(TAGLOG, EMPTYDB);
            }
        } else {
            Log.i(TAGLOG, CLOSEDDB);
        }
        cursor.close();
        return cartItems;
    }

    // This function deletes all dishes from a cart table
    public void deleteAllDishes(SQLiteDatabase db) {
        // Checks if database is open
        if (db.isOpen()) {
            db.execSQL(SQL_DELETE_ALL);
        } else {
            Log.i(TAGLOG, CLOSEDDB);
        }
    }

    // This function deletes a dish if exists
    public void deleteDishWhereName(SQLiteDatabase db, String name) {
        final String SQL_DELETE_WHERE_NAME = "DELETE FROM "+ CartItemEntry.TABLE_NAME
                + " WHERE " + CartItemEntry.NAME + " = '" + name + "'";
        // Checks if database is open
        if (db.isOpen()) {
            db.execSQL(SQL_DELETE_WHERE_NAME);
        } else {
            Log.i(TAGLOG, CLOSEDDB);
        }
    }

    // Checks if dish exists
    public boolean doesDishExists(SQLiteDatabase db, CartItem cartItem) {
        boolean exists = false;
        final String SQL_SELECT_WHERE_NAME = "SELECT " + CartItemEntry.NAME +
                " FROM " + CartItemEntry.TABLE_NAME +
                " WHERE " + CartItemEntry.NAME + "= '" + cartItem.getName() + "'";
        // Checks if database is open
        if (db.isOpen()) {
            Cursor cursor= db.rawQuery(SQL_SELECT_WHERE_NAME,null);
            exists = (cursor.getCount() > 0);
            cursor.close();
        } else {
            Log.i(TAGLOG, CLOSEDDB);
        }
        return exists;
    }

    // Gets quantity before modify it
    public Integer getOldQuantity(SQLiteDatabase db, CartItem cartItem) {
        int quantity = 0;
        // Checks if database is open
        if (db.isOpen()) {
            // Gets quantity before modify it
            final String SQL_GET_QUANTITY = "SELECT " + CartItemEntry.QUANTITY +
                    " FROM " + CartItemEntry.TABLE_NAME +
                    " WHERE " + CartItemEntry.NAME + "= '" + cartItem.getName() + "'";
            // SQLite execution (SET where name)
            Cursor cursor= db.rawQuery(SQL_GET_QUANTITY,null);
            if (cursor.moveToFirst()) {
                quantity = (cursor.getInt(0));
            }
            cursor.close();
        } else {
            Log.i(TAGLOG, CLOSEDDB);
        }
        return quantity;
    }

    // Get old quantity and add it to new quantity
    public void updateQuantity(SQLiteDatabase db, CartItem cartItem) {
        // Checks if database is open
        if (db.isOpen()) {
            // Updates dish quantity
            final String SQL_UPDATE_QUANTITY = "UPDATE " + CartItemEntry.TABLE_NAME + " SET "
                    + CartItemEntry.QUANTITY + " = " + (getOldQuantity(db, cartItem)+cartItem.getQuantity())
                    + " WHERE " + CartItemEntry.NAME + " = '" + cartItem.getName() + "'";
            // SQLite execution (SET where name)
            db.execSQL(SQL_UPDATE_QUANTITY);
        } else {
            Log.i(TAGLOG, CLOSEDDB);
        }
    }

}
