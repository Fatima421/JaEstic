package com.grup2.jaestic_user.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grup2.jaestic_user.R;

import java.util.List;

public class MainParaRepetirRecyclerViewHoritzontal extends RecyclerView.Adapter<MainParaRepetirRecyclerViewHoritzontal.TopVentasViewHolder> {

    private List<Integer> mViewColors;
    private List<String> mFood;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MainParaRepetirRecyclerViewHoritzontal(Context context, List<Integer> colors, List<String> foods) {
        this.mInflater = LayoutInflater.from(context);
        this.mViewColors = colors;
        this.mFood = foods;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public TopVentasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_main_para_repetir, parent, false);
        return new TopVentasViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull TopVentasViewHolder holder, int position) {
        int color = mViewColors.get(position);
        String food = mFood.get(position);
        holder.myView.setBackgroundColor(color);
        holder.myTextView.setText(food);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mFood.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class TopVentasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View myView;
        TextView myTextView;

        TopVentasViewHolder(View itemView) {
            super(itemView);
            myView = itemView.findViewById(R.id.foodImatgeItem);
            myTextView = itemView.findViewById(R.id.foodNameItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mFood.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}