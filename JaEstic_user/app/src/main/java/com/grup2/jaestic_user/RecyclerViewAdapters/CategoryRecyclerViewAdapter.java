package com.grup2.jaestic_user.RecyclerViewAdapters;

import android.content.Context;
import android.media.Image;
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
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.grup2.jaestic_user.Fragments.DishesListFragment;
import com.grup2.jaestic_user.Models.Category;
import com.grup2.jaestic_user.R;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder> {
    // Properties
    private ArrayList<Category> arrayCategories;
    private Context context;

    // Constructor
    public CategoryRecyclerViewAdapter(ArrayList<Category> arrayCategories, Context c){
        this.arrayCategories = arrayCategories;
        this.context = c;
    }

    // App LifeCycle
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout for this fragment
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        CategoryViewHolder holder = new CategoryViewHolder(view);
        return holder;
    }

    // Adds data to the layout elements defined in ViewHolder through Holder
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int i) {
        // Bundle properties
        Bundle bundle = new Bundle();
        DishesListFragment dishesListFragment = new DishesListFragment();
        // Sets text inside TextViews
        Category category = arrayCategories.get(i);

        // Create a storage reference from our app

        // Reference to an image file in Cloud Storage


        // ArrayList<String> images = storageReference.child("CategoryImages/").listAll();

        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        Task<ListResult> images = storageReference.child("CategoryImages/").listAll();

        storageReference.child("CategoryImages/asian.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.i("IMAGE", ":::::::::::::::::::::::::::::::::::" + uri.toString());
                Glide.with(context)
                        .load(uri.toString())
                        .into(holder.image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("IMAGE", e.toString());
            }
        });



        // Adds item object to bundle and sent to Item Details fragments
        bundle.putSerializable("Category", category);
        dishesListFragment.setArguments(bundle);

        // When user clicks on item, will navigation to item details fragment
        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity app = (AppCompatActivity) v.getContext();
            app.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dishesListFragment, "Dish").commit();
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

    /*
    @GlideModule
    public class MyAppGlideModule extends AppGlideModule {

        @Override
        public void registerComponents(Context context, Glide glide, Registry registry) {
            // Register FirebaseImageLoader to handle StorageReference
            registry.append(StorageReference.class, InputStream.class,
                    new FirebaseImageLoader.Factory());
        }
    }

     */

}
