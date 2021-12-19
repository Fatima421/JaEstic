package com.grup2.jaestic_user.RecyclerViewAdapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.Models.Command;
import com.grup2.jaestic_user.R;

import java.util.ArrayList;

public class RepeatOrderRecyclerViewAdapter extends RecyclerView.Adapter<RepeatOrderRecyclerViewAdapter.RepeatOrderViewHolder> {
    private Context context;
    private ArrayList<Command> arrayCommands;
    private ArrayList<CartItem> cartItems;
    Command command;

    // data is passed into the constructor
    public RepeatOrderRecyclerViewAdapter(Context context, ArrayList<Command> arrayCommands) {
        this.context = context;
        this.arrayCommands = arrayCommands;
        for (int i = 0; i < arrayCommands.size(); i++) {
            this.command = arrayCommands.get(i);
        }
        for (int i = 0; i < command.getCartItem().size(); i++) {
            this.cartItems = arrayCommands.get(i).getCartItem();
        }
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public RepeatOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_to_repeat, parent, false);
        RepeatOrderViewHolder holder = new RepeatOrderViewHolder(view);
        return holder;
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull RepeatOrderViewHolder holder, int position) {
        // Getting the command
        Command command = arrayCommands.get(position);
        CartItem cartItem = cartItems.get(position);

        // Getting all the cart items
        for (int i = 0; i < command.getCartItem().size(); i++) {
            // Set name of the dish in the command
            holder.foodName.setText(command.getCartItem().get(i).getDish().getName());

            Log.i("a", ".................."+ command.getCartItem().get(i).getDish().getName());

            // Set price of the dish in the command
            holder.foodPrice.setText((Double.toString(command.getCartItem().get(i).getDish().getPrice())) + "€");

            // Reference to an image file in Cloud Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();

            // Set the image of the dish in the command
            if(!command.getCartItem().get(i).getDish().getImageUserPath().equals("") || (!command.getCartItem().get(i).getDish().getImagePath().equals(""))) {
                storageReference.child(command.getCartItem().get(i).getDish().getImageUserPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context)
                                .load(uri.toString())
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.foodImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("IMAGE", e.toString());
                    }
                });
            } else {
                holder.foodImage.setImageResource(R.drawable.junk_food);
            }
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class RepeatOrderViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName;
        TextView foodPrice;

        public RepeatOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImatgeItemMain);
            foodName = itemView.findViewById(R.id.foodNameItemMain);
            foodPrice = itemView.findViewById(R.id.foodPrice);
        }
    }
}