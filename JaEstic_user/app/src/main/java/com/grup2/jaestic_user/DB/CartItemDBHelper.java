package com.grup2.jaestic_user.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.grup2.jaestic_user.DB.CartItemDBCommands;
import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.Models.Dish;

import java.util.ArrayList;

public class CartItemDBHelper extends SQLiteOpenHelper {
    // Properties
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cartItem.db";
    int id;

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + CartItemDBCommands.CartItemEntry.TABLE_NAME +
            "(" + CartItemDBCommands.CartItemEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CartItemDBCommands.CartItemEntry.COLUMN_DISH_NAME_TITLE + " TEXT, "
            + CartItemDBCommands.CartItemEntry.COLUMN_IMAGE_PATH_TITLE + " TEXT, "
            + CartItemDBCommands.CartItemEntry.COLUMN_PRICE_TITLE + " DOUBLE, "
            + CartItemDBCommands.CartItemEntry.COLUMN_QUANTITY_TITLE + " INT)";

    public CartItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("sqlite", SQL_CREATE_ENTRIES);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void insertCartItem(SQLiteDatabase db, CartItem cartItem){
        //Check the bd is open
        if (db.isOpen()){
            //Creation of the register for insert object with the content values
            ContentValues values = new ContentValues();

            //Insert the incidence getting all values
            values.put(CartItemDBCommands.CartItemEntry.COLUMN_DISH_NAME_TITLE, cartItem.getDish().getName());
            values.put(CartItemDBCommands.CartItemEntry.COLUMN_IMAGE_PATH_TITLE, cartItem.getDish().getImageUserPath());
            values.put(CartItemDBCommands.CartItemEntry.COLUMN_PRICE_TITLE, cartItem.getDish().getPrice());
            values.put(CartItemDBCommands.CartItemEntry.COLUMN_QUANTITY_TITLE, cartItem.getQuantity());
            db.insert(CartItemDBCommands.CartItemEntry.TABLE_NAME, null, values);

        } else {
            Log.i("sql","Database is closed");
        }
    }

    //This function selects all the data from the CartItem table and creates a new cartItem object with the values
    public ArrayList<CartItem> getAllData(SQLiteDatabase db){
        ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
        Dish dish;

        //cursor to be able to add all the anime from the db to the array list
        Cursor resultSet = db.rawQuery("Select * from cartItem",null);
        if (resultSet.moveToFirst()) {
            do {
                id = resultSet.getInt(0);
                String dishName = resultSet.getString(1);
                String dishImage = resultSet.getString(2);
                Double dishPrice = resultSet.getDouble(3);
                int quantity = resultSet.getInt(4);
                dish = new Dish("", "", dishImage, dishName, dishPrice);
                cartItems.add(new CartItem(dish, quantity));
            } while (resultSet.moveToNext());
        }
        resultSet.close();

        return cartItems;
    }

    //This function deletes all data from an anime table
    public void deleteAllData(SQLiteDatabase db) {
        db.execSQL("delete from "+ CartItemDBCommands.CartItemEntry.TABLE_NAME);
    }

    /*
    public void updateData(SQLiteDatabase db, String newAnimeTitle, String newGenre, String newRanking) {
        Cursor resultSet = db.rawQuery("Select * from anime",null);
        if (resultSet.moveToFirst()) {
            do {
                id = resultSet.getInt(0);
            } while (resultSet.moveToNext());
        }
        resultSet.close();
        ContentValues values =  new ContentValues();
        values.put(AnimeEntry.COLUMN_NAME_TITLE, newAnimeTitle);
        values.put(AnimeEntry.COLUMN_GENRE_TITLE, newGenre);
        values.put(AnimeEntry.COLUMN_RANKING_TITLE, newRanking);

        db.update(AnimeEntry.TABLE_NAME, values, "ID=" + id, null);
    }

     */
}
