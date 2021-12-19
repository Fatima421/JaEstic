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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.grup2.jaestic_user.DB.CartItemDBHelper;
import com.grup2.jaestic_user.Fragments.DishDetailsFragment;
import com.grup2.jaestic_user.Models.Dish;
import com.grup2.jaestic_user.R;
import java.util.ArrayList;

public class DishRecyclerViewAdapter extends RecyclerView.Adapter<DishRecyclerViewAdapter.DishViewHolder> {
    // Properties
    Context context;
    StorageReference storageReference;
    private ArrayList<Dish> arrayDishes;
    boolean heartPressed = false;
    private CartItemDBHelper dbHelper;
    private SQLiteDatabase db;

    // Constructor
    public DishRecyclerViewAdapter(ArrayList<Dish> arrayDishes, Context context, CartItemDBHelper dbHelper, SQLiteDatabase db) {
        this.arrayDishes = arrayDishes;
        this.context = context;
        this.dbHelper = dbHelper;
        this.db = db;
    }

    // App LifeCycle
    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout for this fragment
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish, parent, false);
        DishViewHolder holder = new DishViewHolder(view);
        return holder;
    }

    // Adds data to the layout elements defined in ViewHolder through Holder
    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int i) {
        // Bundle properties
        Bundle bundle = new Bundle();
        DishDetailsFragment dishDetailsFragment = new DishDetailsFragment(dbHelper, db);
        // Sets text inside TextViews
        Dish dish = arrayDishes.get(i);
        holder.name.setText(dish.getName());
        holder.description.setText(dish.getDescription());
        holder.price.setText(dish.getPrice() + "€");
        holder.favorite.setImageResource(R.drawable.ic_heart_empty);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        if((!dish.getImageUserPath().equals("")) || (!dish.getImagePath().equals(""))) {
            storageReference.child(dish.getImageUserPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context)
                            .load(uri.toString())
                            .placeholder(R.drawable.junk_food)
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
        } else {
            holder.image.setImageResource(R.drawable.junk_food);
        }


        // Adds item object to bundle and sent to Item Details fragments
        bundle.putSerializable("Dish", dish);
        dishDetailsFragment.setArguments(bundle);

        // When user clicks on item, will navigation to item details fragment
        // Manage screen back navagation with .addToBackStack(null)
        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity app = (AppCompatActivity) v.getContext();
            app.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dishDetailsFragment, "Dish").addToBackStack(null).commit();
        });

        holder.favorite.setOnClickListener(v -> {
            AppCompatActivity app = (AppCompatActivity) v.getContext();
            int current = (heartPressed == false) ? R.drawable.ic_heart : R.drawable.ic_heart_empty;
            heartPressed = (current == R.drawable.ic_heart_empty) ? false : true;
            holder.favorite.setImageResource(current);
        });
    }

    // Counts how many items will iterate to display a list
    @Override
    public int getItemCount() { return arrayDishes.size(); }

    // Initializes Layout properties that will link with RecyclerView (through Holder)
    public class DishViewHolder extends RecyclerView.ViewHolder{
        TextView name, description, price;
        ImageView favorite, image;
        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.listDishName);
            description = itemView.findViewById(R.id.listDishDescription);
            price = itemView.findViewById(R.id.listDishPrice);
            favorite = itemView.findViewById(R.id.imgHeart);
            image = itemView.findViewById(R.id.listDishImage);
        }
    }

}