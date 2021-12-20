package com.grup2.jaestic_user.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grup2.jaestic_user.DB.CartItemDBHelper;
import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.Models.Command;
import com.grup2.jaestic_user.R;
import com.grup2.jaestic_user.RecyclerViewAdapters.CartRecyclerViewAdapter;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    // Properties
    private ArrayList<CartItem> arrayCartItems = new ArrayList<>();
    private ArrayList<CheckBox> checkBoxes;
    private CartItemDBHelper dbHelper;
    private SQLiteDatabase db;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    // Constructors
    public CartFragment() { }

    public CartFragment(CartItemDBHelper dbHelper, SQLiteDatabase db) {
        this.dbHelper = dbHelper;
        this.db = db;
    }

    // APP LifeCycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        // View Properties
        CheckBox checkBox = v.findViewById(R.id.cartCheckBox);
        Button buyNow = v.findViewById(R.id.buyNowBtn);
        FloatingActionButton deleteBtn = v.findViewById(R.id.cartDeleteBtn);
        TextView totalPriceTextView = v.findViewById(R.id.cartTotalPrice);
        RecyclerView recyclerView;recyclerView = v.findViewById(R.id.cartRecyclerView);

        // Get all data of the cart item from the database
        arrayCartItems = dbHelper.getAllDishes(db);

        // Create the RecyclerView
        CartRecyclerViewAdapter adapter = new CartRecyclerViewAdapter(getContext(), arrayCartItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        checkBoxes = adapter.getCheckBoxes();

        // Customs view properties
        updateTotalPrice(totalPriceTextView);

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
                arrayCartItems = dbHelper.getAllDishes(db);
                if (arrayCartItems.size() != 0) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String email = user.getEmail();
                    Command command = new Command(email, arrayCartItems);
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("Command");
                    myRef.push().setValue(command);
                    // Empties cart list and database
                    dbHelper.deleteAllDishes(db);
                    arrayCartItems.clear();
                    Toast.makeText(getContext(), getString(R.string.boughtItemsSuccesfully), Toast.LENGTH_LONG).show();
                    // Screen updates with empty cart list
                    CartRecyclerViewAdapter adapter = new CartRecyclerViewAdapter(getContext(), arrayCartItems);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    setPriceText(totalPriceTextView, 0.0);
                }
            }
        });

        // When delete button is pressed
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // checks if there are some dishes in the list
                if (arrayCartItems.size() != 0) {
                   if (areAllCheckboxsChecked()) {
                       checkBox.setChecked(false);
                       // Empties cart list and database
                       dbHelper.deleteAllDishes(db);
                       arrayCartItems.clear();
                       // Toast warns cart list is deleted
                       Toast.makeText(getContext(), getString(R.string.cartDeleteListToast), Toast.LENGTH_LONG).show();
                   } else {
                       for (int i = 0; i < checkBoxes.size(); i++) {
                           if (checkBoxes.get(i).isChecked()) {
                               checkBoxes.get(i).setChecked(false);
                               String name = arrayCartItems.get(i).getDish().getName();
                               dbHelper.deleteDishWhereName(db, name);
                               arrayCartItems.remove(i);
                               // Toast warns cart list is deleted
                               Toast.makeText(getContext(), getString(R.string.cartDeleteItemToast), Toast.LENGTH_LONG).show();
                           }
                       }
                   }
                    // Screen updates with current cart list
                    adapter.notifyDataSetChanged();
                    updateTotalPrice(totalPriceTextView);
                } else {
                    // Toast warns cart list is already empty
                    Toast.makeText(getContext(), getString(R.string.cartDeleteEmptyToast), Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }
    // Check if all checkboxs are checked
    private Boolean areAllCheckboxsChecked() {
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (!checkBoxes.get(i).isChecked()) {
                return false;
            }
        }
        return true;
    }

    // Custom Total Price TextView
    private void updateTotalPrice(TextView totalPriceTextView) {
        double totalPrice = 0.0;
        for (int i = 0; i < arrayCartItems.size(); i++) {
           totalPrice = totalPrice + (arrayCartItems.get(i).getDish().getPrice() * arrayCartItems.get(i).getQuantity());
        }
        setPriceText(totalPriceTextView, totalPrice);
    }

    private void setPriceText(TextView totalPriceTextView, double totalPrice) {
        String totalPriceString = String.format("%.2f %s",
                                                totalPrice, getContext().getString(R.string.coin));
        totalPriceTextView.setText(totalPriceString);
    }
}