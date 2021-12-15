package com.grup2.jaestic_user.DB;

import android.provider.BaseColumns;

public class CartItemDBCommands {
    private CartItemDBCommands() {}
    public static class CartItemEntry implements BaseColumns {
        public static final String TABLE_NAME ="cartItem";
        public static final String ID = "id";
        public static final String COLUMN_DISH_NAME_TITLE = "name";
        public static final String COLUMN_IMAGE_PATH_TITLE = "imagePath";
        public static final String COLUMN_PRICE_TITLE = "price";
        public static final String COLUMN_QUANTITY_TITLE = "quantity";
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
