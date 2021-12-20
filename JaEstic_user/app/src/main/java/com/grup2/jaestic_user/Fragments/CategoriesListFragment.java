package com.grup2.jaestic_user.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grup2.jaestic_user.DB.CartItemDBHelper;
import com.grup2.jaestic_user.Models.Category;
import com.grup2.jaestic_user.R;
import com.grup2.jaestic_user.RecyclerViewAdapters.CategoryRecyclerViewAdapter;
import java.util.ArrayList;

public class CategoriesListFragment extends Fragment {

    // Properties
    private DatabaseReference databaseReference;
    private ArrayList<Category> categories = new ArrayList<>();
    private RecyclerView recyclerView;
    private CartItemDBHelper dbHelper;
    private SQLiteDatabase db;

    // Constructor
    public CategoriesListFragment() { }

    public CategoriesListFragment(CartItemDBHelper dbHelper, SQLiteDatabase db) {
        this.dbHelper = dbHelper;
        this.db = db;
    }

    // App LifeCycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categories_list, container, false);

        // Create the RecyclerView
        recyclerView = v.findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        // Create the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Categories");
        getFirebaseCategoryData();

        return v;
    }

    // Get data from firebase
    private void getFirebaseCategoryData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot di:dataSnapshot.getChildren()) {
                    Category category=di.getValue(Category.class);
                    if (di.hasChild("imagePathUsers")) {
                        category.setFirebaseKey(di.getKey());
                        categories.add(category);
                    }
                }
                CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(categories, getContext(), dbHelper, db);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "onCancelled:", databaseError.toException());
            }
        });
    }
}