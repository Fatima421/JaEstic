package com.grup2.jaestic_user.RecyclerViewAdapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.grup2.jaestic_user.Fragments.DishDetailsFragment;
import com.grup2.jaestic_user.Models.Dish;
import com.grup2.jaestic_user.R;
import java.util.ArrayList;

public class DishRecyclerViewAdapter extends RecyclerView.Adapter<DishRecyclerViewAdapter.DishViewHolder> {
    // Properties
    private ArrayList<Dish> arrayDishes;
    Context context;

    // Constructor
    public DishRecyclerViewAdapter(Context context, ArrayList<Dish> arrayDishes){
        this.context = context;
        this.arrayDishes = arrayDishes;
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
        DishDetailsFragment dishDetailsFragment = new DishDetailsFragment();
        // Sets text inside TextViews
        Dish dish = arrayDishes.get(i);
        holder.name.setText(dish.getName());

        // Adds item object to bundle and sent to Item Details fragments
        bundle.putSerializable("Dish", dish);
        dishDetailsFragment.setArguments(bundle);

        // When user clicks on item, will navigation to item details fragment
        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity app = (AppCompatActivity) v.getContext();
            app.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dishDetailsFragment, "Dish").commit();
        });
    }

    // Counts how many items will iterate to display a list
    @Override
    public int getItemCount() { return arrayDishes.size(); }

    // Initializes Layout properties that will link with RecyclerView (through Holder)
    public class DishViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lbl_nameDishList);
        }
    }

}