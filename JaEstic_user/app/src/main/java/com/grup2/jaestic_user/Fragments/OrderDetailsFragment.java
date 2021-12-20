package com.grup2.jaestic_user.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.Models.Command;
import com.grup2.jaestic_user.R;
import com.grup2.jaestic_user.RecyclerViewAdapters.OrderDetailsRecyclerViewAdapter;

import java.util.ArrayList;

public class OrderDetailsFragment extends Fragment {
    // Properties
    private Bundle bundle;
    private Command command;
    private DatabaseReference cartItemDatabase;

    // Constructors
    public OrderDetailsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        // Properties
        ArrayList<CartItem> arrayCartItems = new ArrayList<CartItem>();

        // View Properties
        TextView orderReferenceTextView = view.findViewById(R.id.textOrderReference);
        TextView totalQuantityTextView = view.findViewById(R.id.textTotalQuantity);
        TextView totalPriceTextView = view.findViewById(R.id.orderDetailTotalPrice);
        ImageView imageOrder = view.findViewById(R.id.imageOrderPlaceHolder);

        // Bundle properties
        bundle = getArguments();
        command = (Command) bundle.getSerializable("Order");
        cartItemDatabase = FirebaseDatabase.getInstance().getReference("Command").child(""+command.getFirebaseKey()).child("cartItem");

        // Customs View Properties
        orderReferenceTextView.setText(getContext().getString(R.string.commandBody) + " " + command.getFirebaseKey());
        totalQuantityTextView.setText("Total " + getContext().getString(R.string.quantity) + " " + command.getTotalQuantity());
        imageOrder.setImageResource(R.drawable.command);
        String totalPriceString = String.format("Total %.2f %s",
                command.getTotalPrice(), getContext().getString(R.string.coin));
        totalPriceTextView.setText(totalPriceString);

        // Firebase
        cartItemDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                arrayCartItems.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CartItem cartItem = postSnapshot.getValue(CartItem.class);
                    arrayCartItems.add(cartItem);
                }

                RecyclerView recyclerView = view.findViewById(R.id.orderRecyclerView);
                OrderDetailsRecyclerViewAdapter adapter = new OrderDetailsRecyclerViewAdapter(getContext(), arrayCartItems);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }
        @Override
        public void onCancelled (DatabaseError error){
            Log.w("JaEstic", "Failed to read value.", error.toException());
        }
    });
/*        // RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.orderRecyclerView);
        OrderDetailsRecyclerViewAdapter adapter = new OrderDetailsRecyclerViewAdapter(getContext(), arrayCartItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));*/

        return view;
}

}