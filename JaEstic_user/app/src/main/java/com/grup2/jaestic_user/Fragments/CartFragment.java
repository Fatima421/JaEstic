package com.grup2.jaestic_user.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grup2.jaestic_user.DB.CartItemDBHelper;
import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.Models.Category;
import com.grup2.jaestic_user.Models.Dish;
import com.grup2.jaestic_user.R;
import com.grup2.jaestic_user.RecyclerViewAdapters.CartRecyclerViewAdapter;

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
        // Layout properties linked to add logic
        CheckBox checkBox = v.findViewById(R.id.cartCheckBox);
        FloatingActionButton deleteBtn = v.findViewById(R.id.cartDeleteBtn);

        // Get all data of the cart item from the database
        arrayCartItems = dbHelper.getAllData(db);

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

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // checks if there are some dishes in the list
                if (arrayCartItems.size() != 0) {
                   if (areAllCheckboxsChecked()) {
                       // Empties cart list
                       arrayCartItems.clear();
                       checkBox.setChecked(false);
                       // Toast warns cart list is deleted
                       Toast.makeText(getContext(), getString(R.string.cartDeleteListToast), Toast.LENGTH_LONG).show();
                   } else {
                       for (int i = 0; i < checkBoxes.size(); i++) {
                           if (checkBoxes.get(i).isChecked()) {
                               checkBoxes.get(i).setChecked(false);
                               arrayCartItems.remove(i);
                               // Toast warns cart list is deleted
                               Toast.makeText(getContext(), getString(R.string.cartDeleteItemToast), Toast.LENGTH_LONG).show();
                           }
                       }
                   }
                    // Screen updates with current cart list
                    adapter.notifyDataSetChanged();
                } else {
                    // Toast warns cart list is already empty
                    Toast.makeText(getContext(), getString(R.string.cartDeleteEmptyToast), Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }
    public Boolean areAllCheckboxsChecked() {
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (!checkBoxes.get(i).isChecked()) {
                Log.i("Jaestic", "Not checked: "+ i + " " + checkBoxes.get(i).isChecked());
                return false;
            }
            Log.i("Jaestic", "Checked: " + i + " "+ checkBoxes.get(i).isChecked());
        }
        Log.i("Jaestic", "ALL CHECKED!");
        return true;
    }
}