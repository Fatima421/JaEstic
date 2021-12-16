package com.grup2.jaestic_user.DB;

import android.provider.BaseColumns;

public class CartItemDBContract {
    private CartItemDBContract() { }
    public static class CartItemEntry implements BaseColumns {
        public static final String TABLE_NAME ="cartItem";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String IMAGE_PATH = "imagePath";
        public static final String PRICE = "price";
        public static final String QUANTITY = "quantity";
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
