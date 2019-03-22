package com.sbay.mrz.sbay;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class cart extends Fragment implements cartRecyclerAdapter.TotalCostListener {
    private View rootView;

    private TextView tv_cartTotalcost;
    private Button btn_checkout;

    private RecyclerView cartRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private cartRecyclerAdapter cartRecyclerAdapter;
    private ArrayList<softwareDetails> softwareDetailsList;

    private extraFunctions extraFunctions;
    private ProductsDatabase productsDatabase;

    private int totalCost;

    public cart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        extraFunctions = new extraFunctions();
        extraFunctions.customToast(getActivity(), getContext());
        productsDatabase = Room.databaseBuilder(getContext(), ProductsDatabase.class, "sbay.db").allowMainThreadQueries().build();

        softwareDetailsList = (ArrayList<softwareDetails>) productsDatabase.productsData().getAllProducts();
        if (softwareDetailsList.size() > 0) {
            cartRecyclerView = (RecyclerView) rootView.findViewById(R.id.cart_RecyclerView);
            layoutManager = new GridLayoutManager(getContext(), 1);
            cartRecyclerAdapter = new cartRecyclerAdapter(softwareDetailsList, getContext(),this);
            cartRecyclerView.setLayoutManager(layoutManager);
            cartRecyclerView.setAdapter(cartRecyclerAdapter);
            calTotalCost();
        } else {
            extraFunctions.text.setText(getResources().getString(R.string.emptycart));
            extraFunctions.toast.show();
        }

        tv_cartTotalcost = (TextView) rootView.findViewById(R.id.totalCost);
        btn_checkout = (Button) rootView.findViewById(R.id.btn_checkOut);

        tv_cartTotalcost.setText(getContext().getResources().getString(R.string.rs) + String.valueOf(totalCost));
        
        return rootView;
    }


    private void calTotalCost() {
        for (int i = 0; i < softwareDetailsList.size(); i++)
            totalCost += Integer.parseInt(softwareDetailsList.get(i).getCost());
    }

    @Override
    public void onTotalCostChange(int total) {
        tv_cartTotalcost.setText(getContext().getResources().getString(R.string.rs) + String.valueOf(total));
    }
}
