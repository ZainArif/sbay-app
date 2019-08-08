package com.sbay.mrz.sbay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SellerCustomerProfile extends Fragment {
    private View rootView;

    private Bundle bundle;

    private EditText et_username;
    private EditText et_contact;
    private EditText et_address;

    private ImageButton imgbtn_username;
    private ImageButton imgbtn_contact;
    private ImageButton imgbtn_address;

    private Button btn_updateProfile;

    private extraFunctions extraFunctions;

    private String username;
    private String menuType;
    private String seller_cust_id;
    private String contact;
    private String address;

    private ApiInterface apiInterface;

    private cust_sell_registration updateUser;
    private home home;

    public SellerCustomerProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_seller_customer_profile, container, false);

        extraFunctions = new extraFunctions();
        extraFunctions.customToast(getActivity(), getContext());

        home = (home) getActivity();

        et_username = (EditText)rootView.findViewById(R.id.et_username);
        et_contact = (EditText)rootView.findViewById(R.id.et_contact);
        et_address = (EditText)rootView.findViewById(R.id.et_address);
        imgbtn_username = (ImageButton)rootView.findViewById(R.id.imgbtn_username);
        imgbtn_contact = (ImageButton)rootView.findViewById(R.id.imgbtn_contact);
        imgbtn_address = (ImageButton)rootView.findViewById(R.id.imgbtn_address);
        btn_updateProfile = (Button)rootView.findViewById(R.id.btn_updateProfile);

        bundle = this.getArguments();
        if (bundle!=null){
            username = bundle.getString("username");
            menuType = bundle.getString("menu type");
            seller_cust_id = bundle.getString("seller_cust_id");
            contact = bundle.getString("contact");
            address = bundle.getString("address");

            et_username.setText(username);
            et_contact.setText(contact);
            et_address.setText(address);

            if (menuType.equals("Seller"))
                apiInterface = ApiClient.getApiClient("sellers").create(ApiInterface.class);
            else if (menuType.equals("Customer"))
                apiInterface = ApiClient.getApiClient("customers").create(ApiInterface.class);

        }

        imgbtn_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.openKeyboard(getContext(),et_username,btn_updateProfile);
            }
        });

        imgbtn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.openKeyboard(getContext(),et_contact,btn_updateProfile);
            }
        });

        imgbtn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.openKeyboard(getContext(),et_address,btn_updateProfile);
            }
        });

        btn_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.showProgressDialog(getContext(), "Updating Profile");
                extraFunctions.closeKeyboard(getActivity(), getContext());
                username = et_username.getText().toString();
                contact = et_contact.getText().toString();
                address = et_address.getText().toString();
                updateProfile();

            }
        });
        return rootView;
    }

    private void updateProfile(){

        updateUser = new cust_sell_registration(username,Long.parseLong(contact),address);

        if (menuType.equals("Seller")){
            Call<cust_sell_login> updateUserCall = apiInterface.sellerUpdate(seller_cust_id,updateUser);
            updateUserCall.enqueue(new Callback<cust_sell_login>() {
                @Override
                public void onResponse(Call<cust_sell_login> call, Response<cust_sell_login> response) {
                    if (response.code() == 200) {
                        String updateStatus = response.body().getUpdateStatus();
                        if (updateStatus.equals("user updated")) {
                            extraFunctions.hideProgressDialog();
                            extraFunctions.text.setText(getResources().getString(R.string.profileupdated));
                            extraFunctions.toast.show();
                            home.updateProfile(username, contact, address);

                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .remove(SellerCustomerProfile.this).commit();
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            extraFunctions.hideProgressDialog();
                            extraFunctions.text.setText(getResources().getString(R.string.sww));
                            extraFunctions.toast.show();
                        }
                    }
                    else {
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.sww));
                        extraFunctions.toast.show();
                    }
                }

                @Override
                public void onFailure(Call<cust_sell_login> call, Throwable t) {
                    extraFunctions.hideProgressDialog();
                    extraFunctions.text.setText(getResources().getString(R.string.sww));
                    extraFunctions.toast.show();
                }
            });
        }
        else if (menuType.equals("Customer")){
            Call<cust_sell_login> updateUserCall = apiInterface.customerUpdate(seller_cust_id,updateUser);
            updateUserCall.enqueue(new Callback<cust_sell_login>() {
                @Override
                public void onResponse(Call<cust_sell_login> call, Response<cust_sell_login> response) {
                    if (response.code() == 200) {
                        String updateStatus = response.body().getUpdateStatus();
                        if (updateStatus.equals("user updated")) {
                            extraFunctions.hideProgressDialog();
                            extraFunctions.text.setText(getResources().getString(R.string.profileupdated));
                            extraFunctions.toast.show();
                            home.updateProfile(username, contact, address);

                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .remove(SellerCustomerProfile.this).commit();
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            extraFunctions.hideProgressDialog();
                            extraFunctions.text.setText(getResources().getString(R.string.sww));
                            extraFunctions.toast.show();
                        }
                    }
                    else {
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.sww));
                        extraFunctions.toast.show();
                    }
                }

                @Override
                public void onFailure(Call<cust_sell_login> call, Throwable t) {
                    extraFunctions.hideProgressDialog();
                    extraFunctions.text.setText(getResources().getString(R.string.sww));
                    extraFunctions.toast.show();
                }
            });
        }

    }

}
