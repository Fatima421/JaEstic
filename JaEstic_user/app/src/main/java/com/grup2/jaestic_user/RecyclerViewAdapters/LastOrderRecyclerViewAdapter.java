package com.grup2.jaestic_user.RecyclerViewAdapters;

import android.content.Context;
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

import com.grup2.jaestic_user.Fragments.DishesListFragment;
import com.grup2.jaestic_user.Fragments.OrderDetailsFragment;
import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.Models.Command;
import com.grup2.jaestic_user.R;

import java.util.ArrayList;

public class LastOrderRecyclerViewAdapter extends RecyclerView.Adapter<LastOrderRecyclerViewAdapter.LastOrderViewHolder> {
    private Context context;
    private ArrayList<Command> arrayOrders;
    private Command order;

    // data is passed into the constructor
    public LastOrderRecyclerViewAdapter(Context context, ArrayList<Command> arrayOrders) {
        this.context = context;
        this.arrayOrders = arrayOrders;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public LastOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_last_orders, parent, false);
        LastOrderViewHolder holder = new LastOrderViewHolder(view);
        return holder;
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull LastOrderViewHolder holder, int position) {
        Bundle bundle = new Bundle();
        OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
        // Getting the command
        order = arrayOrders.get(position);

        holder.orderName.setText(context.getString(R.string.commandBody) + " " + (position+1));
        String totalPriceString = String.format("%.2f %s",
                                                order.getTotalPrice(), context.getString(R.string.coin));
        holder.orderPrice.setText(totalPriceString);
        holder.orderQuantity.setText("" + order.getTotalQuantity());
        holder.orderImage.setImageResource(R.drawable.command);

        // Adds item object to bundle and sent to Order Details fragments
        bundle.putSerializable("Order", order);
        orderDetailsFragment.setArguments(bundle);

        // When user clicks on item, will navigation to order details fragment
        // Manage screen back navagation with .addToBackStack(null)
        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity app = (AppCompatActivity) v.getContext();
            app.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, orderDetailsFragment, "Order").addToBackStack(null).commit();
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return arrayOrders.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class LastOrderViewHolder extends RecyclerView.ViewHolder {
        ImageView orderImage;
        TextView orderName, orderPrice, orderQuantity;

        public LastOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderImage = itemView.findViewById(R.id.imageLastOrder);
            orderName = itemView.findViewById(R.id.nameLastOrder);
            orderPrice = itemView.findViewById(R.id.priceLastOrder);
            orderQuantity = itemView.findViewById(R.id.quantityLastOrder);
        }
    }
}