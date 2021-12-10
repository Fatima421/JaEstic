package com.grup2.jaestic_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.grup2.jaestic_user.Fragments.CartFragment;
import com.grup2.jaestic_user.Fragments.CategoriesListFragment;
import com.grup2.jaestic_user.Fragments.MainFragment;

public class NavigationActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemClock.sleep(1000);
        setTheme(R.style.Theme_JaEstic_user);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);

        BottomNavigationView bottomNav = findViewById(R.id.main_menu);

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new MainFragment();
                    break;

                case R.id.nav_food:
                    selectedFragment = new CategoriesListFragment();
                    break;

                case R.id.nav_cart:
                    selectedFragment = new CartFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });
    }

}