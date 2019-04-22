package com.sbay.mrz.sbay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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

    private AlertDialog.Builder alertDialog;

    public myProductRecycleradapter(ArrayList<softwareDetails> softwareDetailsList, Context context) {
        this.softwareDetailsList = softwareDetailsList;
        this.context = context;
        extraFunctions = new extraFunctions();
        extraFunctions.customToast((Activity) context, context);
        apiInterface = ApiClient.getApiClient("products").create(ApiInterface.class);
        fragmentActivity = (FragmentActivity) context;
        alertDialog = new AlertDialog.Builder(context);
    }

    @NonNull
    @Override
    public myProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View myProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_product_card, parent, false);
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

                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Are you sure you want to delete this product?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        extraFunctions.showProgressDialog(context,"Deleting Product");
                        final String[] pScreenshotsPublicId = softwareDetails.getScreenshotPublicID();
                        Call<softwareDetails> delProductCall = apiInterface.delProduct(softwareDetails.getProductID());
                        delProductCall.enqueue(new Callback<softwareDetails>() {
                            @Override
                            public void onResponse(Call<softwareDetails> call, Response<softwareDetails> response) {
                                String delStatus = response.body().getDelstatus();
                                if (delStatus.equals("deleted")) {
                                    delAsyncTask delAsyncTask = new delAsyncTask(context);
                                    delAsyncTask.execute(pScreenshotsPublicId[0]);
                                    softwareDetailsList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyDataSetChanged();
                                    extraFunctions.hideProgressDialog();
                                    extraFunctions.text.setText(context.getResources().getString(R.string.productdeleted));
                                    extraFunctions.toast.show();
                                } else {
                                    extraFunctions.hideProgressDialog();
                                    extraFunctions.text.setText(context.getResources().getString(R.string.sww));
                                    extraFunctions.toast.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<softwareDetails> call, Throwable t) {
                                extraFunctions.hideProgressDialog();
                                extraFunctions.text.setText(context.getResources().getString(R.string.sww));
                                extraFunctions.toast.show();
                            }
                        });
                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.create().show();

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
                softwareDetailsBundle.putStringArray("pScreenshotsPublicId", softwareDetails.getScreenshotPublicID());
                updateProduct = new updateProduct();
                updateProduct.setArguments(softwareDetailsBundle);
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, updateProduct)
                        .add(new updateProduct(), "updateProduct")
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
