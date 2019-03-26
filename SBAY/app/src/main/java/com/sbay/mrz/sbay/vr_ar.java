package com.sbay.mrz.sbay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class vr_ar extends Fragment {
    private View rootView;

    private RecyclerView myRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private customRecyclerAdapter1 customRecyclerAdapter1;
    private ArrayList<softwareDetails> softwareDetailsList;

    private ApiInterface apiInterface;

    private String productId;
    private String productCat;
    private String productName;
    private String productDesc;
    private String productCost;
    private String productDemoVideoUrl;
    private String[] productScreenshots;

    private extraFunctions extraFunctions;


    public vr_ar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_vr_ar, container, false);

        apiInterface = ApiClient.getApiClient("products").create(ApiInterface.class);

        extraFunctions  = new extraFunctions();
        extraFunctions.customToast(getActivity(),getContext());

        softwareDetailsList = new ArrayList<>();
        myRecyclerView = (RecyclerView)rootView.findViewById(R.id.myRecyclerView);
        layoutManager = new GridLayoutManager(getContext(),1);
        customRecyclerAdapter1 = new customRecyclerAdapter1(softwareDetailsList,getContext());
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setAdapter(customRecyclerAdapter1);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getSoftwares();
    }

    private void getSoftwares(){

        Call<List<softwareDetails>> softwareDetalisListCall = apiInterface.vrar();
        softwareDetalisListCall.enqueue(new Callback<List<softwareDetails>>() {
            @Override
            public void onResponse(Call<List<softwareDetails>> call, Response<List<softwareDetails>> response) {
                softwareDetailsList.clear();
                for (int index=0;index<response.body().size();index++)
                {
                    productId = response.body().get(index).getProductID();
                    productCat = response.body().get(index).getCategory();
                    productName = response.body().get(index).getName();
                    productDesc = response.body().get(index).getDescription();
                    productCost = response.body().get(index).getCost();
                    productDemoVideoUrl = response.body().get(index).getDemoVideoURl();
                    productScreenshots = response.body().get(index).getScreenShot();
                    softwareDetailsList.add(new softwareDetails(productId,productName,productDesc,productCat,productCost,productDemoVideoUrl,productScreenshots));
                    customRecyclerAdapter1.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<softwareDetails>> call, Throwable t) {
                extraFunctions.text.setText(getResources().getString(R.string.sww));
                extraFunctions.toast.show();
            }
        });
    }
}