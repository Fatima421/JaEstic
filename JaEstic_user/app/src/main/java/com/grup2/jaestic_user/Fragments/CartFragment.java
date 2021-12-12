package com.grup2.jaestic_user.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.Models.Category;
import com.grup2.jaestic_user.Models.Dish;
import com.grup2.jaestic_user.R;
import com.grup2.jaestic_user.RecyclerViewAdapters.CartRecyclerViewAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    // Properties
    private DatabaseReference databaseReference;
    private ArrayList<CartItem> arrayCartItems = new ArrayList<>();
    private ArrayList<CheckBox> checkBoxes;
    RecyclerView recyclerView;
    Bundle bundle;
    CartItem cartItem;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        CheckBox checkBox = v.findViewById(R.id.cartCheckBox);


        // Bundle properties
        bundle = getArguments();
        cartItem = (CartItem) bundle.getSerializable("CartItem");
        arrayCartItems.add(new CartItem(new Dish(), 0));
        //arrayCartItems.add(cartItem);

        /*// Create the categories array list
        arrayCartItems = new ArrayList<>();
        arrayCartItems.add(new Dish(2, "", "Pizza", "", "12,90", (new Category())));
        arrayCartItems.add(new Dish(2, "", "Pizza", "", "12,90", (new Category())));
        arrayCartItems.add(new Dish(2, "", "Pizza", "", "12,90", (new Category())));
        arrayCartItems.add(new Dish(2, "", "Pizza", "", "12,90", (new Category())));
        arrayCartItems.add(new Dish(2, "", "Pizza", "", "12,90", (new Category())));
        arrayCartItems.add(new Dish(2, "", "Pizza", "", "12,90", (new Category())));
        arrayCartItems.add(new Dish(2, "", "Pizza", "", "12,90", (new Category())));
        arrayCartItems.add(new Dish(2, "", "Pizza", "", "12,90", (new Category())));
        */

        // Create the RecyclerView
        recyclerView = v.findViewById(R.id.cartRecyclerView);
        CartRecyclerViewAdapter adapter = new CartRecyclerViewAdapter(getContext(), arrayCartItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        checkBoxes = adapter.getCheckBoxes();

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    for (int i = 0; i < checkBoxes.size(); i++) {
                        CheckBox currentCheckBox = checkBoxes.get(i);
                        currentCheckBox.setChecked(true);
                    }
                } else {
                    for (int i = 0; i < checkBoxes.size(); i++) {
                        CheckBox currentCheckBox = checkBoxes.get(i);
                        currentCheckBox.setChecked(false);
                    }
                }
            }
        });

        return v;
    }
}