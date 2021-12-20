package com.grup2.jaestic_user.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.Models.Dish;
import com.grup2.jaestic_user.R;

import java.util.ArrayList;

public class OrderDetailsRecyclerViewAdapter extends RecyclerView.Adapter<OrderDetailsRecyclerViewAdapter.OrderViewHolder> {
    // Properties
    Context context;
    private ArrayList<CartItem> arrayDishes;

    // Constructor
    public OrderDetailsRecyclerViewAdapter(Context context, ArrayList<CartItem> arrayDishes) {
        this.arrayDishes = arrayDishes;
        this.context = context;
    }

    // App LifeCycle
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout for this fragment
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        OrderViewHolder holder = new OrderViewHolder(view);
        return holder;
    }

    // Adds data to the layout elements defined in ViewHolder through Holder
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int i) {
        CartItem cartItem = arrayDishes.get(i);
        Dish dish = cartItem.getDish();
        holder.name.setText(dish.getName());
        holder.quantity.setText("x" + cartItem.getQuantity());
        String priceString = String.format("%.2f %s",
                            dish.getPrice() * cartItem.getQuantity(), context.getString(R.string.coin));
        holder.price.setText(priceString);
    }

    // Counts how many items will iterate to display a list
    @Override
    public int getItemCount() { return arrayDishes.size(); }

    // Initializes Layout properties that will link with RecyclerView (through Holder)
    public class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView quantity;
        TextView price;
        TextView name;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            quantity = itemView.findViewById(R.id.orderDetailsQuantityDish);
            name = itemView.findViewById(R.id.orderDetailsNameDish);
            price = itemView.findViewById(R.id.orderDetailsPriceDish);
        }
    }

}