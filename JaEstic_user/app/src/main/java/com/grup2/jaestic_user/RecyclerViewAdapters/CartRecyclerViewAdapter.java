package com.grup2.jaestic_user.RecyclerViewAdapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.grup2.jaestic_user.Fragments.DishesListFragment;
import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.Models.Category;
import com.grup2.jaestic_user.Models.Dish;
import com.grup2.jaestic_user.R;

import java.util.ArrayList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder> {
    // Properties
    private ArrayList<CartItem> arrayCartItems;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    Context context;

    // Constructor
    public CartRecyclerViewAdapter(Context context, ArrayList<CartItem> arrayCartItems){
        this.context = context;
        this.arrayCartItems = arrayCartItems;
    }

    // App LifeCycle
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout for this fragment
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart, parent, false);
        CartViewHolder holder = new CartViewHolder(view);
        return holder;
    }

    // Adds data to the layout elements defined in ViewHolder through Holder
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int i) {
        // Bundle properties
        Bundle bundle = new Bundle();

        CartItem cartItem = arrayCartItems.get(i);

        // Add checkboxs to the arraylist
        checkBoxes.add(holder.checkBox);

        holder.name.setText(cartItem.getDish().getName());
        String priceString = String.format("%.2f %s",
                                            cartItem.getDish().getPrice(), context.getString(R.string.coin));
        holder.price.setText(priceString);
        holder.quantity.setText(context.getString(R.string.quantity) + " " + cartItem.getQuantity());

        // To load the image
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(cartItem.getDish().getImageUserPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
    }

    public ArrayList<CheckBox> getCheckBoxes() {
        return  checkBoxes;
    }

    // Counts how many items will iterate to display a list
    @Override
    public int getItemCount() { return arrayCartItems.size(); }

    // Initializes Layout properties that will link with RecyclerView (through Holder)
    public class CartViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        TextView quantity;
        ImageView image;
        CheckBox checkBox;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cmdItemName);
            price = itemView.findViewById(R.id.cmdItemPrice);
            image = itemView.findViewById(R.id.cmdItemImage);
            quantity = itemView.findViewById(R.id.cmdItemQuantity);
            checkBox = itemView.findViewById(R.id.cmdCheckBox);
        }
    }
}
