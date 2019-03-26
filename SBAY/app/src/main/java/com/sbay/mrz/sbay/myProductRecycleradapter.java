package com.sbay.mrz.sbay;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class myProductRecycleradapter extends RecyclerView.Adapter<myProductViewHolder> {
    private ArrayList<softwareDetails> softwareDetailsList;
    private Context context;
    private extraFunctions extraFunctions;
    private ApiInterface apiInterface;
    private FragmentActivity fragmentActivity;
    private Bundle softwareDetailsBundle;
    private updateProduct updateProduct;

    public myProductRecycleradapter(ArrayList<softwareDetails> softwareDetailsList, Context context) {
        this.softwareDetailsList = softwareDetailsList;
        this.context = context;
        extraFunctions = new extraFunctions();
        extraFunctions.customToast((Activity)context,context);
        apiInterface = ApiClient.getApiClient("products").create(ApiInterface.class);
        fragmentActivity = (FragmentActivity) context;
    }

    @NonNull
    @Override
    public myProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View myProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_product_card,parent,false);
        myProductViewHolder myProductViewHolder = new myProductViewHolder(myProductView);
        return myProductViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myProductViewHolder myProductViewHolder, final int position) {
        final softwareDetails softwareDetails = softwareDetailsList.get(position);

        myProductViewHolder.tv_productName.setText(softwareDetails.getName());
        myProductViewHolder.tv_productCat.setText(softwareDetails.getCategory());

        String[] pScreenshots = softwareDetails.getScreenShot();
        Glide.with(context).load(pScreenshots[0]).into(myProductViewHolder.iv_productImage);

        myProductViewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<softwareDetails> delProductCall = apiInterface.delProduct(softwareDetails.getProductID());
                delProductCall.enqueue(new Callback<softwareDetails>() {
                    @Override
                    public void onResponse(Call<softwareDetails> call, Response<softwareDetails> response) {
                        String delStatus = response.body().getDelstatus();
                        if (delStatus.equals("deleted")){
                            extraFunctions.text.setText(context.getResources().getString(R.string.productdeleted));
                            extraFunctions.toast.show();
                            softwareDetailsList.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                        }
                        else {
                            extraFunctions.text.setText(context.getResources().getString(R.string.sww));
                            extraFunctions.toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<softwareDetails> call, Throwable t) {
                        extraFunctions.text.setText(context.getResources().getString(R.string.sww));
                        extraFunctions.toast.show();
                    }
                });
            }
        });

        myProductViewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                softwareDetailsBundle = new Bundle();
                softwareDetailsBundle.putString("pId", softwareDetails.getProductID());
                softwareDetailsBundle.putString("pSellerId", softwareDetails.getSellerID());
                softwareDetailsBundle.putString("pName", softwareDetails.getName());
                softwareDetailsBundle.putString("pDesc", softwareDetails.getDescription());
                softwareDetailsBundle.putString("pCost", softwareDetails.getCost());
                softwareDetailsBundle.putString("pCat", softwareDetails.getCategory());
                softwareDetailsBundle.putString("pExeUrl", softwareDetails.getExeURl());
                softwareDetailsBundle.putString("pDemoUrl", softwareDetails.getDemoVideoURl());
                softwareDetailsBundle.putString("pHostUrl", softwareDetails.getHostURL());
                softwareDetailsBundle.putStringArray("pScreenshots", softwareDetails.getScreenShot());
                updateProduct = new updateProduct();
                updateProduct.setArguments(softwareDetailsBundle);
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,updateProduct)
                        .add(new updateProduct(),"updateProduct")
                        .addToBackStack("updateProduct")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return softwareDetailsList.size();
    }

}
