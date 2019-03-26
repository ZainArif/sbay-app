package com.sbay.mrz.sbay;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class customRecyclerAdapter extends RecyclerView.Adapter<productsViewHolder> {

    private ArrayList<softwareDetails> softwareDetailsList;
    private Context context;
    private extraFunctions extraFunctions;
    private ProductsDatabase productsDatabase;

    public customRecyclerAdapter(ArrayList<com.sbay.mrz.sbay.softwareDetails> softwareDetailsList, Context context) {
        this.softwareDetailsList = softwareDetailsList;
        this.context = context;
        extraFunctions = new extraFunctions();
        extraFunctions.customToast((Activity)context,context);
        productsDatabase = Room.databaseBuilder(context,ProductsDatabase.class,"sbay.db").allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public productsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View myProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.productcard,parent,false);
        productsViewHolder productsViewHolder = new productsViewHolder(myProductView,context,softwareDetailsList);
        return productsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull productsViewHolder productsViewHolder, final int position) {
        final softwareDetails softwareDetails = softwareDetailsList.get(position);
        //productsViewHolder.tv_productCat.setText(softwareDetails.getCategory());
        productsViewHolder.tv_productName.setText(softwareDetails.getName());
       // productsViewHolder.tv_productDesc.setText(softwareDetails.getpDescription());
        productsViewHolder.tv_productCost.setText(context.getResources().getString(R.string.rs) + softwareDetails.getCost());

        final String[] pScreenshots = softwareDetails.getScreenShot();
        Glide.with(context).load(pScreenshots[0]).into(productsViewHolder.iv_productImage);

        productsViewHolder.btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                softwareDetails details = productsDatabase.productsData().getProductById(softwareDetails.getProductID());
                if (details==null){
                    details = new softwareDetails(softwareDetails.getProductID(),
                                                  softwareDetails.getName(),
                                                  softwareDetails.getDescription(),
                                                  softwareDetails.getCategory(),
                                                  softwareDetails.getCost(),
                                                  softwareDetails.getDemoVideoURl(),
                                                  pScreenshots[0]);
                    productsDatabase.productsData().insertProduct(details);
                    extraFunctions.text.setText(context.getResources().getString(R.string.productadded));
                    extraFunctions.toast.show();
                }
                else {
                    extraFunctions.text.setText(context.getResources().getString(R.string.productalreadyadded));
                    extraFunctions.toast.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return softwareDetailsList.size();
    }

}
