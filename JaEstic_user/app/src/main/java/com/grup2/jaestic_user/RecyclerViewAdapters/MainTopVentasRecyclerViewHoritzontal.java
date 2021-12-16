package com.grup2.jaestic_user.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.R;

import java.util.ArrayList;
import java.util.List;

public class MainTopVentasRecyclerViewHoritzontal extends RecyclerView.Adapter<MainTopVentasRecyclerViewHoritzontal.TopVentasViewHolder> {
    private Context context;
    private ArrayList<CartItem> cartItems;

    // data is passed into the constructor
    public MainTopVentasRecyclerViewHoritzontal(Context context, ArrayList<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public TopVentasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_top_ventas, parent, false);
        TopVentasViewHolder holder = new TopVentasViewHolder(view);
        return holder;
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull TopVentasViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.myView2.setBackgroundColor(context.getResources().getColor(R.color.red));
        holder.myTextView2.setText("Pedido " + cartItem.getFirebaseKey());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class TopVentasViewHolder extends RecyclerView.ViewHolder {
        View myView2;
        TextView myTextView2;

        public TopVentasViewHolder(@NonNull View itemView) {
            super(itemView);
            myView2 = itemView.findViewById(R.id.foodImatgeItem2);
            myTextView2 = itemView.findViewById(R.id.foodNameItem2);
        }
    }
}