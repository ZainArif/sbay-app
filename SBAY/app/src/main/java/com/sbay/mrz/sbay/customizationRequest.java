package com.sbay.mrz.sbay;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class customizationRequest extends Fragment {
    private View rootView;

    private String productId;
    private String productCat;
    private String productName;
    private String productDesc;
    private String cust_id;

    private TextView pName;
    private TextView pCat;
    private EditText pDesc;
    private Button btn_submit;

    private ApiInterface apiInterface;
    private extraFunctions extraFunctions;

    private Bundle custBundle;

    private AlertDialog.Builder alertDialog;

    public customizationRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_customization_request, container, false);

        alertDialog = new AlertDialog.Builder(this.getContext());

        apiInterface = ApiClient.getApiClient("customRequest").create(ApiInterface.class);

        extraFunctions = new extraFunctions();
        extraFunctions.customToast(getActivity(), getContext());

        pName = (TextView)rootView.findViewById(R.id.pName);
        pCat = (TextView)rootView.findViewById(R.id.pCat);
        pDesc = (EditText) rootView.findViewById(R.id.pDesc);
        btn_submit = (Button)rootView.findViewById(R.id.btn_customiztionRequest);

        custBundle = this.getArguments();

        if (custBundle!=null){
            productId = custBundle.getString("pId");
            productName = custBundle.getString("pName");
            productCat = custBundle.getString("pCat");
            cust_id = custBundle.getString("cust_id");
            pName.setText(productName);
            pCat.setText(productCat);
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.closeKeyboard(getActivity(),getContext());
                productDesc = pDesc.getText().toString();
                if (TextUtils.isEmpty(productDesc)){
                    pDesc.setError("Required");
                    return;
                }

                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Are you sure you want to save this information?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        extraFunctions.showProgressDialog(getContext(),"Applying for customization");
                        customization_request();
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

        return rootView;
    }

    private void customization_request(){
        productCustomization productCustomization = new productCustomization(cust_id,productId,productDesc);

        Call<productCustomization> productCustomizationCall = apiInterface.productCustom(productCustomization);
        productCustomizationCall.enqueue(new Callback<com.sbay.mrz.sbay.productCustomization>() {
            @Override
            public void onResponse(Call<com.sbay.mrz.sbay.productCustomization> call, Response<com.sbay.mrz.sbay.productCustomization> response) {
                if (response.code() == 200) {
                    if (response.body().getCustId().equals(cust_id)) {
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.custreqsent));
                        extraFunctions.toast.show();
                        pDesc.getText().clear();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .remove(customizationRequest.this).commit();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                }
                else {
                    extraFunctions.hideProgressDialog();
                    extraFunctions.text.setText(getResources().getString(R.string.sww));
                    extraFunctions.toast.show();
                }
            }

            @Override
            public void onFailure(Call<com.sbay.mrz.sbay.productCustomization> call, Throwable t) {
                if (getActivity()!=null && isAdded()){
                    extraFunctions.hideProgressDialog();
                    extraFunctions.text.setText(getResources().getString(R.string.sww));
                    extraFunctions.toast.show();
                }
            }
        });

    }

}
