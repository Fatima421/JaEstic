package com.grup2.jaestic_user.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.grup2.jaestic_user.Models.Category;
import com.grup2.jaestic_user.R;
import com.grup2.jaestic_user.RecyclerViewAdapters.CategoryRecyclerViewAdapter;

import java.util.ArrayList;

public class CategoriesListFragment extends Fragment {

    // Properties
    private DatabaseReference databaseReference;
    private ArrayList<Category> categories;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categories_list, container, false);


        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        //Adding elements
        /*
        ArrayList<Category> categories = new ArrayList<Category>();
        categories.add(new Category(getActivity().getResources().getDrawable(R.drawable.junk_food).toString()));
        categories.add(new Category(getActivity().getResources().getDrawable(R.drawable.junk_food).toString()));
        categories.add(new Category(getActivity().getResources().getDrawable(R.drawable.junk_food).toString()));
        categories.add(new Category(getActivity().getResources().getDrawable(R.drawable.junk_food).toString()));
*/
        // Create the RecyclerView
        recyclerView = v.findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        // Create the categories array list
        categories = new ArrayList<>();

        // Create the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Categories");
        getImageData();
        return v;
    }

    private void getImageData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot di:dataSnapshot.getChildren()){
                    Category category=di.getValue(Category.class);
                    categories.add(category);
                    Log.i("IMAGE", "onDataChange: "+category.getImagePath());
                  //  Log.i("value received in string", "onDataChange: "+di.getValue().toString());
                }
                CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(categories, getContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "onCancelled:", databaseError.toException());
            }
        });
    }

    private void getImageUrl() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot di:dataSnapshot.getChildren()){
                    Category category=di.getValue(Category.class);
                    categories.add(category);
                    Log.i("IMAGE", "onDataChange: "+category.getImagePath());
                    //  Log.i("value received in string", "onDataChange: "+di.getValue().toString());
                }
                CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(categories, getContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "onCancelled:", databaseError.toException());
            }
        });
    }
}