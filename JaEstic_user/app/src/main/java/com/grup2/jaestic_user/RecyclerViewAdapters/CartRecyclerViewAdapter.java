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

import com.grup2.jaestic_user.Fragments.DishesListFragment;
import com.grup2.jaestic_user.Models.Category;
import com.grup2.jaestic_user.R;

import java.util.ArrayList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.CategoryViewHolder> {
    // Properties
    private ArrayList<Category> arrayCategories;
    Context context;

    // Constructor
    public CartRecyclerViewAdapter(Context context, ArrayList<Category> arrayCategories){
        this.context = context;
        this.arrayCategories = arrayCategories;
    }

    // App LifeCycle
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout for this fragment
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish, parent, false);
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
        holder.name.setText(category.getName());

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
        TextView name;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lbl_nameCategoryList);
        }
    }

}
