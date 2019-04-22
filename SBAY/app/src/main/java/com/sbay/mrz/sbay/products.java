package com.sbay.mrz.sbay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class products extends Fragment {
    private View rootView;

    private RecyclerView myRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private customRecyclerAdapter customRecyclerAdapter;
    private ArrayList<softwareDetails> softwareDetailsList;

    private LinearLayout mobileApps;
    private LinearLayout webApps;
    private LinearLayout vr_ar;
    private LinearLayout ai;
    private LinearLayout ecommerce;
    private LinearLayout iot;

    private ApiInterface apiInterface;

    private String productId;
    private String productCat;
    private String productName;
    private String productDesc;
    private String productCost;
    private String productDemoVideoUrl;
    private String[] productScreenshots;
    private String productHostUrl;

    private extraFunctions extraFunctions;

    private Bundle softwareTypeBundle;
    private categoryProducts categoryProducts;

    public products() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_products, container, false);
        apiInterface = ApiClient.getApiClient("products").create(ApiInterface.class);

        extraFunctions  = new extraFunctions();
        extraFunctions.customToast(getActivity(),getContext());

        softwareDetailsList = new ArrayList<>();
        myRecyclerView = (RecyclerView)rootView.findViewById(R.id.myRecyclerView);
        layoutManager = new GridLayoutManager(getContext(),2);
        customRecyclerAdapter = new customRecyclerAdapter(softwareDetailsList,getContext());
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setAdapter(customRecyclerAdapter);

        mobileApps = (LinearLayout)rootView.findViewById(R.id.mobileApps);
        mobileApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                softwareTypeBundle = new Bundle();
                softwareTypeBundle.putString("pCatType",getResources().getString(R.string.mobileapp));
                categoryProducts = new categoryProducts();
                categoryProducts.setArguments(softwareTypeBundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, categoryProducts)
                        .addToBackStack("mobile")
                        .commit();
            }
        });

        webApps = (LinearLayout)rootView.findViewById(R.id.webApps);
        webApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                softwareTypeBundle = new Bundle();
                softwareTypeBundle.putString("pCatType",getResources().getString(R.string.webapp));
                categoryProducts = new categoryProducts();
                categoryProducts.setArguments(softwareTypeBundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, categoryProducts)
                        .addToBackStack(null)
                        .commit();
            }
        });

        vr_ar = (LinearLayout)rootView.findViewById(R.id.vr_ar);
        vr_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                softwareTypeBundle = new Bundle();
                softwareTypeBundle.putString("pCatType",getResources().getString(R.string.vr_ar));
                categoryProducts = new categoryProducts();
                categoryProducts.setArguments(softwareTypeBundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, categoryProducts)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ai = (LinearLayout)rootView.findViewById(R.id.ai);
        ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                softwareTypeBundle = new Bundle();
                softwareTypeBundle.putString("pCatType",getResources().getString(R.string.ai));
                categoryProducts = new categoryProducts();
                categoryProducts.setArguments(softwareTypeBundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, categoryProducts)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ecommerce = (LinearLayout)rootView.findViewById(R.id.ecommerce);
        ecommerce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                softwareTypeBundle = new Bundle();
                softwareTypeBundle.putString("pCatType",getResources().getString(R.string.ecommerce));
                categoryProducts = new categoryProducts();
                categoryProducts.setArguments(softwareTypeBundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, categoryProducts)
                        .addToBackStack(null)
                        .commit();
            }
        });

        iot = (LinearLayout)rootView.findViewById(R.id.iot);
        iot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                softwareTypeBundle = new Bundle();
                softwareTypeBundle.putString("pCatType",getResources().getString(R.string.iot));
                categoryProducts = new categoryProducts();
                categoryProducts.setArguments(softwareTypeBundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, categoryProducts)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getSoftwares();
    }

    private void getSoftwares(){


        Call<List<softwareDetails>> softwareDetalisListCall = apiInterface.allSoftwares();
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
                    productHostUrl = response.body().get(index).getHostURL();
                    softwareDetailsList.add(new softwareDetails(productId,productName,productDesc,productCat,productCost,productDemoVideoUrl,productScreenshots,productHostUrl));
                    customRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<softwareDetails>> call, Throwable t) {
                if (getActivity()!=null && isAdded()){
                    extraFunctions.text.setText(getResources().getString(R.string.sww));
                    extraFunctions.toast.show();
                }
            }
        });
    }

}
