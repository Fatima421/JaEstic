package com.grup2.jaestic_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.grup2.jaestic_user.DB.CartItemDBHelper;
import com.grup2.jaestic_user.Fragments.CartFragment;
import com.grup2.jaestic_user.Fragments.CategoriesListFragment;
import com.grup2.jaestic_user.Fragments.DishDetailsFragment;
import com.grup2.jaestic_user.Fragments.MainFragment;

public class NavigationActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private CartItemDBHelper dbHelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemClock.sleep(1000);
        setTheme(R.style.Theme_JaEstic_user);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);

        //Creation of the dbHelper
        dbHelper = new CartItemDBHelper(this);
        db = dbHelper.getWritableDatabase();

        // Properties
        BottomNavigationView bottomNav = findViewById(R.id.main_menu);

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new MainFragment(dbHelper, db);
                    break;

                case R.id.nav_food:
                    selectedFragment = new CategoriesListFragment(dbHelper, db);
                    break;

                case R.id.nav_cart:
                    selectedFragment = new CartFragment(dbHelper, db);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });
    }

}