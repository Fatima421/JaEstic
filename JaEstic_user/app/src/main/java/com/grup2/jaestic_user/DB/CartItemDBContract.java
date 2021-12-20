package com.grup2.jaestic_user.DB;

import android.provider.BaseColumns;
public class CartItemDBContract {
    private CartItemDBContract() { }
    // SQLite Table
    public static class CartItemEntry implements BaseColumns {
        public static final String TABLE_NAME ="cartItem";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String IMAGE_PATH = "imagePath";
        public static final String PRICE = "price";
        public static final String QUANTITY = "quantity";
    }
}