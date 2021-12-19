package com.grup2.jaestic_user.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.grup2.jaestic_user.DB.CartItemDBHelper;
import com.grup2.jaestic_user.Models.CartItem;
import com.grup2.jaestic_user.Models.Dish;
import com.grup2.jaestic_user.R;
import java.util.ArrayList;


public class DishDetailsFragment extends Fragment {
    private int inCart = 1;
    Bundle bundle;
    private Dish dish;
    private CartItem cartItem;
    private CartItemDBHelper dbHelper;
    private SQLiteDatabase db;

    public DishDetailsFragment(CartItemDBHelper dbHelper, SQLiteDatabase db) {
        this.dbHelper = dbHelper;
        this.db = db;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dish_details, container, false);

        // Bundle properties
        bundle = getArguments();
        dish = (Dish) bundle.getSerializable("Dish");
        Double originalPrice = dish.getPrice();

        // View Properties
        TextView productName = view.findViewById(R.id.productName);
        TextView productDescription = view.findViewById(R.id.ProductDescription);
        TextView price = view.findViewById(R.id.price);
        Button lessBtn = view.findViewById(R.id.less);
        TextView cartQuantity = view.findViewById(R.id.numQuantity);
        Button moreBtn = view.findViewById(R.id.more);
        Button addToCart = view.findViewById(R.id.addtocart);

        // Custom view properties
        productName.setText(dish.getName());
        productDescription.setText(dish.getDescription());
        price.setText(dish.getPrice() + getContext().getString(R.string.coin));

        // To load the image
        ImageView productImage = view.findViewById(R.id.productImage);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(dish.getImageUserPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(productImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("IMAGE", e.toString());
            }
        });


        // If lessBtn Button is clicked
        lessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inCart > 1) {
                    inCart = inCart - 1;
                }
                price.setText((dish.getPrice() * inCart) + getContext().getString(R.string.coin));
                cartQuantity.setText("" + inCart);
            }
        });

        // If moreBtn Button is clicked
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inCart = inCart + 1;
                cartQuantity.setText("" + inCart);
                price.setText((dish.getPrice() * inCart) + getContext().getString(R.string.coin));
            }
        });

        // If add to cart button clicked
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), getString(R.string.addedItemInCart),
                        Toast.LENGTH_LONG).show();
                Double totalPrice = dish.getPrice() * inCart;
                dish.setPrice(totalPrice);
                cartItem = new CartItem(dish, inCart);
                if (dbHelper.doesDishExists(db, cartItem)) {
                    cartItem.setQuantity(inCart);
                    dbHelper.updateQuantity(db, cartItem);
                } else {
                    dbHelper.insertDish(db, cartItem);
                }
                inCart = 1;
                cartQuantity.setText("" + inCart);
                dish.setPrice(originalPrice);
                price.setText(dish.getPrice() + getContext().getString(R.string.coin));
            }
        });

        return view;
    }
}