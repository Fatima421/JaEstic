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

public class MainTopVentasRecyclerViewHoritzontal extends RecyclerView.Adapter<MainTopVentasRecyclerViewHoritzontal.TopVentasViewHolder> {

    private List<Integer> mViewColors2;
    private List<String> mFood2;
    private LayoutInflater mInflater2;
    private ItemClickListener mClickListener2;

    // data is passed into the constructor
    public MainTopVentasRecyclerViewHoritzontal(Context context, List<Integer> colors2, List<String> foods2) {
        this.mInflater2 = LayoutInflater.from(context);
        this.mViewColors2 = colors2;
        this.mFood2 = foods2;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public TopVentasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater2.inflate(R.layout.item_main_top_ventas, parent, false);
        return new TopVentasViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull TopVentasViewHolder holder, int position) {
        int color2 = mViewColors2.get(position);
        String food2 = mFood2.get(position);
        holder.myView2.setBackgroundColor(color2);
        holder.myTextView2.setText(food2);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mFood2.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class TopVentasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View myView2;
        TextView myTextView2;

        TopVentasViewHolder(View itemView) {
            super(itemView);
            myView2 = itemView.findViewById(R.id.foodImatgeItem2);
            myTextView2 = itemView.findViewById(R.id.foodNameItem2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener2 != null) mClickListener2.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mFood2.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener2 = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}