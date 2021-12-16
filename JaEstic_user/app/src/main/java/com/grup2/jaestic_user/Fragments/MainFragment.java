package com.grup2.jaestic_user.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grup2.jaestic_user.DB.CartItemDBHelper;
import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.Models.Category;
import com.grup2.jaestic_user.Models.Dish;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.grup2.jaestic_user.R;
import com.grup2.jaestic_user.RecyclerViewAdapters.CategoryRecyclerViewAdapter;
import com.grup2.jaestic_user.RecyclerViewAdapters.DishRecyclerViewAdapter;
import com.grup2.jaestic_user.RecyclerViewAdapters.MainParaRepetirRecyclerViewHoritzontal;
import com.grup2.jaestic_user.RecyclerViewAdapters.MainTopVentasRecyclerViewHoritzontal;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    Bundle bundle;
    Category category;
    private DatabaseReference ordersDatabase;
//    private DatabaseReference lastOrderDatabase;
    private ArrayList<CartItem> arrayCartItems =  new ArrayList<CartItem>();

    private CartItemDBHelper dbHelper;
    private SQLiteDatabase db;

    public MainFragment(CartItemDBHelper dbHelper, SQLiteDatabase db) {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        if (user != null) {
            Log.i ("email", "" + email);
        }

        ordersDatabase = FirebaseDatabase.getInstance().getReference("Command").child("cartItem");

//        ordersDatabase = FirebaseDatabase.getInstance().getReference("Command").child(category.getFirebaseKey()).child("Foods");


        // Add dishes in an ArrayList and send it to RecyclerView
        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                arrayCartItems.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    if (dataSnapshot.child("email").equals(email)) {
                        CartItem cartItem = postSnapshot.getValue(CartItem.class);
                        cartItem.setFirebaseKey(dataSnapshot.getKey());
                        arrayCartItems.add(cartItem);
                    }
                }

                // Creates Recycler View "Top Ventas"
                RecyclerView orderRecyclerView = view.findViewById(R.id.topVentasRecyclerView);
                MainTopVentasRecyclerViewHoritzontal adapter = new MainTopVentasRecyclerViewHoritzontal(getContext(), arrayCartItems);
                orderRecyclerView.setAdapter(adapter);
                orderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("JaEstic", "Failed to read value.", error.toException());
            }
        });

        return view;
    }
}

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//       View view = inflater.inflate(R.layout.fragment_main, container, false);
//
//        // data to populate the RecyclerView with
//        ArrayList<Integer> viewColors = new ArrayList<>();
//        viewColors.add(Color.BLUE);
//
//        ArrayList<String> foodNames = new ArrayList<>();
//        foodNames.add("Hamburguesas");
//
//        // data to populate the RecyclerView with
//        ArrayList<Integer> viewColors2 = new ArrayList<>();
//        viewColors2.add(Color.BLUE);
//
//        ArrayList<String> foodNames2 = new ArrayList<>();
//        foodNames2.add("Hamburguesas");
//
//
//        LinearLayoutManager horizontalLayoutManager
//                = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(horizontalLayoutManager);
//
//        LinearLayoutManager horizontalLayoutManager2
//                = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerView2.setLayoutManager(horizontalLayoutManager2);
//
//        adapterParaRepetir = new MainParaRepetirRecyclerViewHoritzontal(this.getActivity(), viewColors, foodNames);
//    //    adapterParaRepetir.setClickListener(this);
//        recyclerView.setAdapter(adapterParaRepetir);
//
//        adapterTopVentas = new MainTopVentasRecyclerViewHoritzontal(this.getActivity(), viewColors2, foodNames2);
//   //     adapterTopVentas.setClickListener(this);
//        recyclerView2.setAdapter(adapterTopVentas);
//
//       return view;
//    }
//

