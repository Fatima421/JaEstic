package com.grup2.jaestic_user.RecyclerViewAdapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.grup2.jaestic_user.DB.CartItemDBHelper;
import com.grup2.jaestic_user.Fragments.DishDetailsFragment;
import com.grup2.jaestic_user.Models.Dish;
import com.grup2.jaestic_user.R;

import java.util.ArrayList;
import java.util.List;

public class MainParaRepetirRecyclerViewHoritzontal extends RecyclerView.Adapter<MainParaRepetirRecyclerViewHoritzontal.ParaRepetirViewHolder> {

//    private List<Integer> mViewColors;
    private List<String> mFood;
    private LayoutInflater mInflater;
//    private MainTopVentasRecyclerViewHoritzontal.ItemClickListener mClickListener;
    Context context;
    StorageReference storageReference;
    private ArrayList<Dish> arrayDishes;
    boolean heartPressed = false;
    boolean cartPressed = false;
    private CartItemDBHelper dbHelper;
    private SQLiteDatabase db;

    // data is passed into the constructor
    public MainParaRepetirRecyclerViewHoritzontal(Context context, List<Integer> colors, List<String> foods) {
        this.mInflater = LayoutInflater.from(context);
//        this.mViewColors = colors;
//        this.mFood = foods;
        this.arrayDishes = arrayDishes;
        this.context = context;
        this.dbHelper = dbHelper;
        this.db = db;
    }

    public MainParaRepetirRecyclerViewHoritzontal(ArrayList<Dish> arrayDishes, Context context, CartItemDBHelper dbHelper, SQLiteDatabase db) {
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ParaRepetirViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_main_para_repetir, parent, false);
        return new ParaRepetirViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParaRepetirViewHolder holder, int i) {

        // Bundle properties
        Bundle bundle = new Bundle();
        DishDetailsFragment dishDetailsFragment = new DishDetailsFragment(dbHelper, db);
        // Sets text inside TextViews
        Dish dish = arrayDishes.get(i);
        holder.name.setText(dish.getName());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(dish.getImageUserPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri.toString())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("IMAGE", e.toString());
            }
        });

        // Adds item object to bundle and sent to Item Details fragments
        bundle.putSerializable("Dish", dish);
        dishDetailsFragment.setArguments(bundle);

        // When user clicks on item, will navigation to item details fragment
        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity app = (AppCompatActivity) v.getContext();
            app.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dishDetailsFragment, "Dish").commit();
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return arrayDishes.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ParaRepetirViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageView image;
        public ParaRepetirViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.listDishName);
            image = itemView.findViewById(R.id.listDishImage);
        }

        @Override
        public void onClick(View v) {

        }
    }
}