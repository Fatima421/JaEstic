package com.grup2.jaestic_user.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.grup2.jaestic_user.R;
import com.grup2.jaestic_user.RecyclerViewAdapters.MainParaRepetirRecyclerViewHoritzontal;
import com.grup2.jaestic_user.RecyclerViewAdapters.MainTopVentasRecyclerViewHoritzontal;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private MainParaRepetirRecyclerViewHoritzontal adapterParaRepetir;
    private MainTopVentasRecyclerViewHoritzontal adapterTopVentas;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_main, container, false);

        // data to populate the RecyclerView with
        ArrayList<Integer> viewColors = new ArrayList<>();
        viewColors.add(Color.BLUE);
        viewColors.add(Color.YELLOW);
        viewColors.add(Color.MAGENTA);
        viewColors.add(Color.RED);
        viewColors.add(Color.BLACK);
        viewColors.add(Color.GREEN);
        viewColors.add(Color.CYAN);
        viewColors.add(Color.GRAY);
        viewColors.add(Color.DKGRAY);

        ArrayList<String> foodNames = new ArrayList<>();
        foodNames.add("Hamburguesas");
        foodNames.add("Frankfurt");
        foodNames.add("Sushi");
        foodNames.add("China");
        foodNames.add("Tacos");
        foodNames.add("Vegetariano");
        foodNames.add("Vegano");
        foodNames.add("Celiaco");
        foodNames.add("Bocatas");

        // data to populate the RecyclerView with
        ArrayList<Integer> viewColors2 = new ArrayList<>();
        viewColors2.add(Color.BLUE);
        viewColors2.add(Color.YELLOW);
        viewColors2.add(Color.MAGENTA);
        viewColors2.add(Color.RED);
        viewColors2.add(Color.BLACK);
        viewColors2.add(Color.GREEN);
        viewColors2.add(Color.CYAN);
        viewColors2.add(Color.GRAY);
        viewColors2.add(Color.DKGRAY);

        ArrayList<String> foodNames2 = new ArrayList<>();
        foodNames2.add("Hamburguesas");
        foodNames2.add("Frankfurt");
        foodNames2.add("Sushi");
        foodNames2.add("China");
        foodNames2.add("Tacos");
        foodNames2.add("Vegetariano");
        foodNames2.add("Vegano");
        foodNames2.add("Celiaco");
        foodNames2.add("Bocatas");

        // set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.paraRepetirRecyclerView);
        RecyclerView recyclerView2 = view.findViewById(R.id.topVentasRecyclerView);

        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        LinearLayoutManager horizontalLayoutManager2
                = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(horizontalLayoutManager2);

        adapterParaRepetir = new MainParaRepetirRecyclerViewHoritzontal(this.getActivity(), viewColors, foodNames);
    //    adapterParaRepetir.setClickListener(this);
        recyclerView.setAdapter(adapterParaRepetir);

        adapterTopVentas = new MainTopVentasRecyclerViewHoritzontal(this.getActivity(), viewColors2, foodNames2);
   //     adapterTopVentas.setClickListener(this);
        recyclerView2.setAdapter(adapterTopVentas);

       return view;
    }


}