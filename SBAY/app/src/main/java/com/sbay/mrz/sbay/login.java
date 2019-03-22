package com.sbay.mrz.sbay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    private EditText et_email;
    private EditText et_password;
    private Button btn_login;
    private TextView tv_signup;

    private ApiInterface apiInterface;

    private String email;
    private String password;
    private String name;
    private String seller_cust_id;

    private String emailPattern;
    private boolean valid;

    private String sl_type;
    private extraFunctions extraFunctions;

    private String userStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        tv_signup = (TextView)findViewById(R.id.tv_signup);

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        sl_type = getIntent().getStringExtra("sl_type");
        apiInterface = ApiClient.getApiClient(sl_type).create(ApiInterface.class);

        extraFunctions  = new extraFunctions();
        extraFunctions.customToast(login.this,login.this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.closeKeyboard(login.this,login.this);
                email = et_email.getText().toString();
                password = et_password.getText().toString();
                performLogin();
            }
        });
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup_Activity = new Intent(login.this, signup.class);
                signup_Activity.putExtra("sl_type",sl_type);
                startActivity(signup_Activity);
            }
        });
    }

    private boolean validation(){
        valid = true;

        if (TextUtils.isEmpty(email)){
            et_email.setError("required");
            valid = false;
        }
        else if (!email.matches(emailPattern)){
            extraFunctions.text.setText(R.string.iemail);
            extraFunctions.toast.show();
            valid = false;
        }

        if (TextUtils.isEmpty(password)){
            et_password.setError("required");
            valid = false;
        }

        return valid;
    }

    private void performLogin(){
        if (!validation()){
            return;
        }

        extraFunctions.showProgressDialog(login.this,"Authenticating...");

        if (sl_type.equals("sellers")){

            Call<cust_sell_login> custSellLoginCall = apiInterface.sellerLogin(email,password);

            custSellLoginCall.enqueue(new Callback<cust_sell_login>() {
                @Override
                public void onResponse(Call<cust_sell_login> call, Response<cust_sell_login> response) {
                    userStatus = response.body().getUserStatus();
                    if (userStatus.equals(" exist")){
                        extraFunctions.hideProgressDialog();
                        name = response.body().getCustSellRegistration().getName();
                        seller_cust_id = response.body().getCustSellRegistration().getId();
                        Intent main_Activity = new Intent();
                        main_Activity.putExtra("seller_cust_id",seller_cust_id);
                        main_Activity.putExtra("username",name);
                        main_Activity.putExtra("email",email);
                        main_Activity.putExtra("menu type",getResources().getString(R.string.seller));
                        setResult(RESULT_OK,main_Activity);
                        finish();
                    }
                    else if (userStatus.equals(" not exist")){
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.seller)+ userStatus);
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
        else if (sl_type.equals("customers")){
            Call<cust_sell_login> custSellLoginCall = apiInterface.customerLogin(email,password);

            custSellLoginCall.enqueue(new Callback<cust_sell_login>() {
                @Override
                public void onResponse(Call<cust_sell_login> call, Response<cust_sell_login> response) {
                    userStatus = response.body().getUserStatus();
                    if (userStatus.equals(" exist")){
                        extraFunctions.hideProgressDialog();
                        name = response.body().getCustSellRegistration().getName();
                        seller_cust_id = response.body().getCustSellRegistration().getId();
                        Intent main_activity = new Intent();
                        main_activity.putExtra("seller_cust_id",seller_cust_id);
                        main_activity.putExtra("username",name);
                        main_activity.putExtra("email",email);
                        main_activity.putExtra("menu type",getResources().getString(R.string.cust));
                        setResult(RESULT_OK,main_activity);
                        finish();
                    }
                    else if (userStatus.equals(" not exist")){
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.cust)+ userStatus);
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