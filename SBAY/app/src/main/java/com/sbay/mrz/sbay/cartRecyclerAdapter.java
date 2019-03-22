package com.sbay.mrz.sbay;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class cartRecyclerAdapter extends RecyclerView.Adapter<cartViewHolder> {

    private ArrayList<softwareDetails> softwareDetailsList;
    private Context context;
    private extraFunctions extraFunctions;
    private ProductsDatabase productsDatabase;
    private ArrayList<Integer> quantity;
    private int qty;
    private int totalCost;
    private int indCost;
    private TotalCostListener totalCostListener;

    public cartRecyclerAdapter(ArrayList<softwareDetails> softwareDetailsList, Context context, TotalCostListener totalCostListener) {
        this.softwareDetailsList = softwareDetailsList;
        this.context = context;
        this.totalCostListener = totalCostListener;
        extraFunctions = new extraFunctions();
        extraFunctions.customToast((Activity)context,context);
        productsDatabase = Room.databaseBuilder(context,ProductsDatabase.class,"sbay.db").allowMainThreadQueries().build();
        quantity = new ArrayList<>();
        for (int i=0;i<softwareDetailsList.size();i++){
            quantity.add(1);
            totalCost += Integer.parseInt(softwareDetailsList.get(i).getCost());
        }
    }

    @NonNull
    @Override
    public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View myCartView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout,parent,false);
        cartViewHolder cartViewHolder = new cartViewHolder(myCartView);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final cartViewHolder cartViewHolder, final int position) {
        final softwareDetails softwareDetails = softwareDetailsList.get(position);
        cartViewHolder.tv_cartProductName.setText(softwareDetails.getName());
        cartViewHolder.tv_cartProductCat.setText(softwareDetails.getCategory());
        cartViewHolder.tv_cartProductCost.setText(context.getResources().getString(R.string.rs) + softwareDetails.getCost());

        cartViewHolder.tv_cartNumber.setText(String.valueOf(quantity.get(position)));

        String pScreenshots = softwareDetails.getScreenshot();
        //Toast.makeText(context, pScreenshots, Toast.LENGTH_SHORT).show();
        Glide.with(context).load(pScreenshots).into(cartViewHolder.iv_productImage);

        cartViewHolder.btn_cartDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.text.setText(context.getResources().getString(R.string.productdeleted));
                extraFunctions.toast.show();
                productsDatabase.productsData().deleteProduct(softwareDetails);
                qty = quantity.get(position);
                indCost = Integer.parseInt(softwareDetails.getCost());
                indCost*= qty;
                totalCost-= indCost;
                countTotal();
                quantity.remove(position);
                softwareDetailsList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });

        cartViewHolder.btn_cartPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity.set(position,quantity.get(position)+1);
                cartViewHolder.tv_cartNumber.setText(String.valueOf(quantity.get(position)));
                totalCost+=Integer.parseInt(softwareDetails.getCost());
                countTotal();
            }
        });

        cartViewHolder.btn_cartMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity.get(position)>1){
                    quantity.set(position,quantity.get(position)-1);
                    cartViewHolder.tv_cartNumber.setText(String.valueOf(quantity.get(position)));
                    totalCost-=Integer.parseInt(softwareDetails.getCost());
                    countTotal();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return softwareDetailsList.size();
    }

    private void countTotal(){
        totalCostListener.onTotalCostChange(totalCost);
    }

    public interface TotalCostListener{
        void onTotalCostChange(int total);
    }

}
