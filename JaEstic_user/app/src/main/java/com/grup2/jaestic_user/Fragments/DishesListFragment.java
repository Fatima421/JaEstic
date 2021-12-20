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
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grup2.jaestic_user.DB.CartItemDBHelper;
import com.grup2.jaestic_user.Models.Category;
import com.grup2.jaestic_user.Models.Dish;
import com.grup2.jaestic_user.R;
import com.grup2.jaestic_user.RecyclerViewAdapters.DishRecyclerViewAdapter;
import java.util.ArrayList;

public class DishesListFragment extends Fragment {
    // Properties
    private Bundle bundle;
    private Category category;
    private DatabaseReference dishesDatabase;
    private ArrayList<Dish> arrayDishes =  new ArrayList<>();
    private CartItemDBHelper dbHelper;
    private SQLiteDatabase db;

    public DishesListFragment(CartItemDBHelper dbHelper, SQLiteDatabase db) {
        this.dbHelper = dbHelper;
        this.db = db;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflates the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dishes_list, container, false);
        // Bundle properties
        bundle = getArguments();
        category = (Category) bundle.getSerializable("Category");
        // Firebase reference
        dishesDatabase = FirebaseDatabase.getInstance().getReference("Categories").child(category.getFirebaseKey()).child("Foods");

        // Layout properties linked to add logic
        TextView lblTitle = view.findViewById(R.id.listDishTitle);
        // Customs layout properties
        lblTitle.setText(category.getName());

        // Add dishes in an ArrayList and send it to RecyclerView
        dishesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                arrayDishes.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if (postSnapshot.hasChild("imageUserPath")) {
                        Dish dish = postSnapshot.getValue(Dish.class);
                        arrayDishes.add(dish);
                    }
                }
                // Creates Recycler View
                RecyclerView recyclerView = view.findViewById(R.id.dishRecyclerView);
                DishRecyclerViewAdapter adapter = new DishRecyclerViewAdapter(arrayDishes, getContext(), dbHelper, db);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("JaEstic", "Failed to read value.", error.toException());
            }
        });
        return view;
    }
}