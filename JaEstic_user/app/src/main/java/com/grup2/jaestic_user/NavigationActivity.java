package com.grup2.jaestic_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.grup2.jaestic_user.Fragments.CartFragment;
import com.grup2.jaestic_user.Fragments.CategoriesListFragment;
import com.grup2.jaestic_user.Fragments.MainFragment;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);

        BottomNavigationView bottomNav = findViewById(R.id.main_menu);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
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