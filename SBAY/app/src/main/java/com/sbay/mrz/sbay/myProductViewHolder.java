package com.sbay.mrz.sbay;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class myProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //TextView tv_productCat;
    TextView tv_productName;
    TextView tv_productCost;
    //TextView tv_productDesc;
    Button btn_addToCart;
    ImageView iv_productImage;

    private Context context;
    private ArrayList<softwareDetails> softwareDetailsList;
    private softwareDetails softwareDetails;
    private Bundle softwareDetailsBundle;
    private productInfo productInfo;


    public myProductViewHolder(@NonNull View itemView, Context context, ArrayList<softwareDetails> softwareDetailsList) {
        super(itemView);
        //tv_productCat = (TextView)itemView.findViewById(R.id.productCat);
        tv_productName = (TextView) itemView.findViewById(R.id.productName);
        //  tv_productDesc = (TextView)itemView.findViewById(R.id.productDesc);
        tv_productCost = (TextView) itemView.findViewById(R.id.productCost);
        btn_addToCart = (Button) itemView.findViewById(R.id.addToCart);
        iv_productImage = (ImageView) itemView.findViewById(R.id.productImage);

        itemView.setOnClickListener(this);
        this.context = context;
        this.softwareDetailsList = softwareDetailsList;
    }


    @Override
    public void onClick(View v) {
        softwareDetails = softwareDetailsList.get(getAdapterPosition());
        softwareDetailsBundle = new Bundle();
        softwareDetailsBundle.putString("pId", softwareDetails.getProductID());
        softwareDetailsBundle.putString("pName", softwareDetails.getName());
        softwareDetailsBundle.putString("pDesc", softwareDetails.getDescription());
        softwareDetailsBundle.putString("pCat", softwareDetails.getCategory());
        softwareDetailsBundle.putString("pCost", softwareDetails.getCost());
        softwareDetailsBundle.putString("pDemoUrl", softwareDetails.getDemoVideoURl());
        softwareDetailsBundle.putStringArray("pScreenshots", softwareDetails.getScreenShot());
        productInfo = new productInfo();
        productInfo.setArguments(softwareDetailsBundle);

        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, productInfo)
                .add(new productInfo(), "product info")
                .addToBackStack("product info")
                .commit();
    }
}
