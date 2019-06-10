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
import android.widget.Spinner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class newSoftwareRequest extends Fragment {
    private View rootView;

    private String newProductDesc;
    private String cust_id;
    private String pCat;

    private Spinner sp_productCat;
    private EditText pDesc;
    private Button btn_submit;

    private ApiInterface apiInterface;
    private extraFunctions extraFunctions;

    private Bundle bundle;

    private AlertDialog.Builder alertDialog;

    public newSoftwareRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_new_software_request, container, false);

        alertDialog = new AlertDialog.Builder(this.getContext());

        apiInterface = ApiClient.getApiClient("newRequest").create(ApiInterface.class);

        extraFunctions = new extraFunctions();
        extraFunctions.customToast(getActivity(), getContext());

        sp_productCat = (Spinner) rootView.findViewById(R.id.sp_productCat);
        pDesc = (EditText) rootView.findViewById(R.id.pDesc);
        btn_submit = (Button)rootView.findViewById(R.id.btn_newRequest);

        bundle = this.getArguments();
        if (bundle!=null){
            cust_id = bundle.getString("seller_cust_id");
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.closeKeyboard(getActivity(),getContext());
                pCat = sp_productCat.getSelectedItem().toString();
                newProductDesc = pDesc.getText().toString();
                if (TextUtils.isEmpty(newProductDesc)){
                    pDesc.setError("Required");
                    return;
                }

                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Are you sure you want to save this information?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        extraFunctions.showProgressDialog(getContext(),"Applying for new software development");
                        newRequest();
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

    private void newRequest(){
        newProduct product = new newProduct(cust_id,newProductDesc,pCat);

        Call<newProduct> newProductCall = apiInterface.newProduct(product);
        newProductCall.enqueue(new Callback<newProduct>() {
            @Override
            public void onResponse(Call<newProduct> call, Response<newProduct> response) {
                if (response.body().getCustId().equals(cust_id)){
                    extraFunctions.hideProgressDialog();
                    extraFunctions.text.setText(getResources().getString(R.string.newreqsent));
                    extraFunctions.toast.show();
                    pDesc.getText().clear();
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .remove(customizationRequest.this).commit();
//                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }

            @Override
            public void onFailure(Call<newProduct> call, Throwable t) {
                if (getActivity()!=null && isAdded()){
                    extraFunctions.hideProgressDialog();
                    extraFunctions.text.setText(getResources().getString(R.string.sww));
                    extraFunctions.toast.show();
                }
            }
        });

    }
}
