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
import com.grup2.jaestic_user.Fragments.DishesListFragment;
import com.grup2.jaestic_user.Models.Category;
import com.grup2.jaestic_user.R;
import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder> {
    // Properties
    private ArrayList<Category> arrayCategories;
    private Context context;
    private CartItemDBHelper dbHelper;
    private SQLiteDatabase db;

    // Constructor
    public CategoryRecyclerViewAdapter(ArrayList<Category> arrayCategories, Context c, CartItemDBHelper dbHelper, SQLiteDatabase db){
        this.arrayCategories = arrayCategories;
        this.context = c;
        this.dbHelper = dbHelper;
        this.db = db;
    }

    // App LifeCycle
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout for this fragment
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        CategoryViewHolder holder = new CategoryViewHolder(view);
        return holder;
    }

    // Adds data to the layout elements defined in ViewHolder through Holder
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int i) {
        // Bundle properties
        Bundle bundle = new Bundle();
        DishesListFragment dishesListFragment = new DishesListFragment(dbHelper, db);
        // Sets text inside TextViews
        Category category = arrayCategories.get(i);

        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        if(!category.getImagePathUsers().equals("") || (!category.getImagePath().equals(""))) {
            storageReference.child(category.getImagePathUsers()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        } else {
            holder.image.setImageResource(R.drawable.junk_food);
        }

        // Adds item object to bundle and sent to Item Details fragments
        bundle.putSerializable("Category", category);
        dishesListFragment.setArguments(bundle);

        // When user clicks on item, will navigation to item details fragment
        // Manage screen back navagation with .addToBackStack(null)
        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity app = (AppCompatActivity) v.getContext();
            app.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dishesListFragment, "Category").addToBackStack(null).commit();
        });
    }

    // Counts how many items will iterate to display a list
    @Override
    public int getItemCount() { return arrayCategories.size(); }

    // Initializes Layout properties that will link with RecyclerView (through Holder)
    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.categoryImage);
        }
    }
}
