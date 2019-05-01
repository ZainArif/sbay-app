package com.sbay.mrz.sbay;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 */
public class productInfo extends Fragment {
    private View rootView;
    private Bundle softwareDetailsBundle;

    private String pId;
    private String pName;
    private String pDescription;
    private String pDemoVideoURl;
    private String pHostUrl;
    private String pCost;
    private String pCategory;
    private String[] pScreenshot;

    private TextView tv_pCost;
    private TextView tv_pName;
    private TextView tv_pCat;
    private TextView tv_pDemoUrl;
    private TextView tv_pHostUrl;
    private TextView tv_pDesc;

    private ImageView iv_productImage;

    private Button btn_addToCart;
    private Button btn_appForCustz;

    private extraFunctions extraFunctions;

    private softwareDetails softwareDetails;
    private ProductsDatabase productsDatabase;

    private home home;
    private String cust_id;

    public productInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_product_info, container, false);

        productsDatabase = Room.databaseBuilder(getContext(), ProductsDatabase.class, "sbay.db").allowMainThreadQueries().build();

        extraFunctions = new extraFunctions();
        extraFunctions.customToast(getActivity(), getContext());

        home = (home) getActivity();

        tv_pCost = (TextView) rootView.findViewById(R.id.productCost);
        tv_pName = (TextView) rootView.findViewById(R.id.productName);
        tv_pCat = (TextView) rootView.findViewById(R.id.productCat);
        tv_pDemoUrl = (TextView) rootView.findViewById(R.id.productDemoUrl);
        tv_pHostUrl = (TextView) rootView.findViewById(R.id.productHostUrl);
        tv_pDesc = (TextView) rootView.findViewById(R.id.productDesc);

        iv_productImage = (ImageView) rootView.findViewById(R.id.productImage);

        softwareDetailsBundle = this.getArguments();
        if (softwareDetailsBundle != null) {
            pId = softwareDetailsBundle.getString("pId");
            pName = softwareDetailsBundle.getString("pName");
            pDescription = softwareDetailsBundle.getString("pDesc");
            pCategory = softwareDetailsBundle.getString("pCat");
            pCost = softwareDetailsBundle.getString("pCost");
            pDemoVideoURl = softwareDetailsBundle.getString("pDemoUrl");
            pHostUrl = softwareDetailsBundle.getString("pHostUrl");
            pScreenshot = softwareDetailsBundle.getStringArray("pScreenshots");
            Glide.with(getContext()).load(pScreenshot[0]).into(iv_productImage);
        }

        tv_pCost.setText(getResources().getString(R.string.rs) + pCost);
        tv_pName.setText(pName);
        tv_pCat.setText(pCategory);
        tv_pDesc.setText(pDescription);
        tv_pDemoUrl.setText(pDemoVideoURl);
        tv_pHostUrl.setText(pHostUrl);

        if (pDemoVideoURl != null)
            if (!pDemoVideoURl.equals(getResources().getString(R.string.notavailable))) {
                tv_pDemoUrl.setEnabled(true);
                tv_pDemoUrl.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

        tv_pDemoUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pDemoVideoURl));
                startActivity(intent);
            }
        });

        if (pHostUrl != null)
            if (!pHostUrl.equals(getResources().getString(R.string.notavailable))) {
                tv_pHostUrl.setEnabled(true);
                tv_pHostUrl.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

        tv_pHostUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pHostUrl));
                startActivity(intent);
            }
        });

        btn_addToCart = (Button) rootView.findViewById(R.id.addToCart);
        btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (softwareDetailsBundle != null) {
                    softwareDetails = productsDatabase.productsData().getProductById(pId);
                    if (softwareDetails == null) {
                        softwareDetails = new softwareDetails(pId, pName, pDescription, pCategory, pCost, pDemoVideoURl, pScreenshot[0]);
                        productsDatabase.productsData().insertProduct(softwareDetails);
                        extraFunctions.text.setText(getContext().getResources().getString(R.string.productadded));
                        extraFunctions.toast.show();
                    } else {
                        extraFunctions.text.setText(getContext().getResources().getString(R.string.productalreadyadded));
                        extraFunctions.toast.show();
                    }
                }
            }
        });

        btn_appForCustz = (Button) rootView.findViewById(R.id.appForCustz);
        btn_appForCustz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cust_id = home.getCust_id();
                if (cust_id==null){
                    extraFunctions.text.setText(getResources().getString(R.string.logascust));
                    extraFunctions.toast.show();
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("pId",pId);
                    bundle.putString("pName",pName);
                    bundle.putString("pCat",pCategory);
                    bundle.putString("cust_id",cust_id);
                    customizationRequest customizationRequest = new customizationRequest();
                    customizationRequest.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,customizationRequest)
                            .addToBackStack(null)
                            .commit();
                }

            }
        });

        return rootView;
    }

}
