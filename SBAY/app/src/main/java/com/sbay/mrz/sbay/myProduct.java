package com.sbay.mrz.sbay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cloudinary.android.MediaManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class myProduct extends Fragment {
    private View rootView;
    private RecyclerView myProductRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private myProductRecycleradapter myProductRecycleradapter;
    private ArrayList<softwareDetails> softwareDetailsList;

    private ApiInterface apiInterface;
    private extraFunctions extraFunctions;
    private Bundle bundle;

    private String pId;
    private String sellerId;
    private String pNmae;
    private String pDesc;
    private String pCost;
    private String pExeUrl;
    private String pDemoUrl;
    private String pHostUrl;
    private String pCat;
    private String[] pScreenshot;
    private String[] pScreenshotPublicId;

    private ProgressBar progressBar;

    public myProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_product, container, false);

        apiInterface = ApiClient.getApiClient("products").create(ApiInterface.class);

        extraFunctions = new extraFunctions();
        extraFunctions.customToast(getActivity(),getContext());

        bundle = this.getArguments();
        if (bundle != null)
            sellerId = bundle.getString("seller_cust_id");

        softwareDetailsList = new ArrayList<>();
        myProductRecyclerView = (RecyclerView)rootView.findViewById(R.id.myRecyclerView);
        layoutManager = new GridLayoutManager(getContext(),1);
        myProductRecycleradapter = new myProductRecycleradapter(softwareDetailsList,getContext());
        myProductRecyclerView.setLayoutManager(layoutManager);
        myProductRecyclerView.setAdapter(myProductRecycleradapter);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progress_circular);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getMySoftwares();
    }

    private void getMySoftwares(){

        Call<List<softwareDetails>> mySoftwaresList = apiInterface.getSellerProducts(sellerId);
        mySoftwaresList.enqueue(new Callback<List<softwareDetails>>() {
            @Override
            public void onResponse(Call<List<softwareDetails>> call, Response<List<softwareDetails>> response) {
                if (response.code() == 200) {
                    if (response.body().size()==0){
                        progressBar.setVisibility(View.GONE);
                        extraFunctions.text.setText(getResources().getString(R.string.npa));
                        extraFunctions.toast.show();
                    }
                    else {
                        softwareDetailsList.clear();
                        for (int index = 0; index < response.body().size(); index++) {
                            pId = response.body().get(index).getProductID();
                            sellerId = response.body().get(index).getSellerID();
                            pNmae = response.body().get(index).getName();
                            pDesc = response.body().get(index).getDescription();
                            pExeUrl = response.body().get(index).getExeURl();
                            pDemoUrl = response.body().get(index).getDemoVideoURl();
                            pHostUrl = response.body().get(index).getHostURL();
                            pCost = response.body().get(index).getCost();
                            pCat = response.body().get(index).getCategory();
                            pScreenshot = response.body().get(index).getScreenShot();
                            pScreenshotPublicId = response.body().get(index).getScreenshotPublicID();
                            softwareDetailsList.add(new softwareDetails(pId, sellerId, pNmae, pDesc, pExeUrl, pDemoUrl, pHostUrl, pCost, pCat, pScreenshot, pScreenshotPublicId));
                            myProductRecycleradapter.notifyDataSetChanged();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    extraFunctions.text.setText(getResources().getString(R.string.sww));
                    extraFunctions.toast.show();
                }
            }

            @Override
            public void onFailure(Call<List<softwareDetails>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                extraFunctions.text.setText(getResources().getString(R.string.sww));
                extraFunctions.toast.show();
            }
        });
    }
}
