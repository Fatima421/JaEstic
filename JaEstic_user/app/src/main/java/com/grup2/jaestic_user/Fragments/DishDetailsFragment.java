package com.grup2.jaestic_user.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.grup2.jaestic_user.LoginScreen;
import com.grup2.jaestic_user.R;


public class DishDetailsFragment extends Fragment {
    private int inCart = 1;
    public DishDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dish_details, container, false);

        // Properties
        TextView productName = view.findViewById(R.id.productName);
        TextView ProductDescription = view.findViewById(R.id.ProductDescription);
        TextView price = view.findViewById(R.id.price);
        Button lessBtn = view.findViewById(R.id.less);
        TextView cartQuantity = view.findViewById(R.id.numQuantity);
        Button moreBtn = view.findViewById(R.id.more);
        Button addtocartBtn = view.findViewById(R.id.addtocart);

        // If lessBtn Button is clicked
        lessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inCart > 0) {
                    inCart = inCart - 1;
                }
                cartQuantity.setText( String.valueOf(inCart));
            }
        });

        // If moreBtn Button is clicked
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inCart = inCart + 1;
                cartQuantity.setText( String.valueOf(inCart));
            }
        });

        return view;
    }
}