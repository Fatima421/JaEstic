package com.grup2.jaestic_user.RecyclerViewAdapters;

import android.content.Context;
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

import com.grup2.jaestic_user.Fragments.DishesListFragment;
import com.grup2.jaestic_user.Models.Dish;
import com.grup2.jaestic_user.R;

import java.util.ArrayList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder> {
    // Properties
    private ArrayList<Dish> arrayCartItems;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    Context context;

    // Constructor
    public CartRecyclerViewAdapter(Context context, ArrayList<Dish> arrayCartItems){
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
        DishesListFragment dishesListFragment = new DishesListFragment();

        // Add checkboxs to the arraylist
        checkBoxes.add(holder.checkBox);

        // Sets text inside TextViews
        // Category category = arrayCartItems.get(i);
        // holder.name.setText(category.getName());
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
        ImageView image;
        CheckBox checkBox;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cmdItemName);
            price = itemView.findViewById(R.id.cmdItemPrice);
            image = itemView.findViewById(R.id.cmdItemImage);
            checkBox = itemView.findViewById(R.id.cmdCheckBox);
        }
    }
}
