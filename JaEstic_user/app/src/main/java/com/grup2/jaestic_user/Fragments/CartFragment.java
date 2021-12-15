package com.grup2.jaestic_user.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grup2.jaestic_user.DB.CartItemDBHelper;
import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.Models.Category;
import com.grup2.jaestic_user.Models.Command;
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
    private CartItemDBHelper dbHelper;
    private SQLiteDatabase db;

    public CartFragment() {
        // Required empty public constructor
    }
    public CartFragment(CartItemDBHelper dbHelper, SQLiteDatabase db) {
        this.dbHelper = dbHelper;
        this.db = db;
    }

    public CartFragment(Bundle bundle) {
        this.bundle = bundle;
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

        // Properties
        CheckBox checkBox = v.findViewById(R.id.cartCheckBox);
        Button buyNow = v.findViewById(R.id.buyNowBtn);

        // Get all data of the cart item from the database
        arrayCartItems = dbHelper.getAllData(db);

        // Create the RecyclerView
        recyclerView = v.findViewById(R.id.cartRecyclerView);
        CartRecyclerViewAdapter adapter = new CartRecyclerViewAdapter(getContext(), arrayCartItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        checkBoxes = adapter.getCheckBoxes();

        // To select or deselect all items
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

        // When buy now button is clicked
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayCartItems = dbHelper.getAllData(db);
                Command command = new Command("hola@gmail.com", arrayCartItems);
                //recyclerView.setVisibility(View.INVISIBLE);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Command2");
                myRef.push().setValue(command);

            }
        });

        return v;
    }
}