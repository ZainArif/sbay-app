package com.sbay.mrz.sbay;

import android.content.Intent;;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup extends AppCompatActivity {

    private EditText et_username;
    private EditText et_email;
    private EditText et_password;
    private EditText et_contact;
    private EditText et_address;
    private Button btn_signup;
    private TextView tv_login;
    private ImageButton imgbtn_pswNotVisible;
    private ImageButton imgbtn_pswVisible;

    private ApiInterface apiInterface;

    private String username;
    private String email;
    private String password;
    private String contact;
    private String address;

    private cust_sell_registration user;

    private String emailPattern;
    private String contactPattren;
    private boolean valid;
    private String sl_type;

    private extraFunctions extraFunctions;

    private String userStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_username = (EditText)findViewById(R.id.et_username);
        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);
        et_contact = (EditText)findViewById(R.id.et_contact);
        et_address = (EditText)findViewById(R.id.et_address);
        btn_signup = (Button)findViewById(R.id.btn_signup);
        tv_login = (TextView)findViewById(R.id.tv_login);
        imgbtn_pswNotVisible = (ImageButton)findViewById(R.id.imgbtn_pswNotVisible);
        imgbtn_pswVisible = (ImageButton)findViewById(R.id.imgbtn_pswVisible);

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        contactPattren = "^[+]?[0-9]{10,13}$";

        sl_type = getIntent().getStringExtra("sl_type");
        apiInterface = ApiClient.getApiClient(sl_type).create(ApiInterface.class);

        extraFunctions  = new extraFunctions();
        extraFunctions.customToast(signup.this,signup.this);

        imgbtn_pswNotVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imgbtn_pswNotVisible.setVisibility(View.GONE);
                imgbtn_pswVisible.setVisibility(View.VISIBLE);
            }
        });

        imgbtn_pswVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imgbtn_pswVisible.setVisibility(View.GONE);
                imgbtn_pswNotVisible.setVisibility(View.VISIBLE);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.closeKeyboard(signup.this,signup.this);
                username = et_username.getText().toString();
                email = et_email.getText().toString();
                password = et_password.getText().toString();
                contact = et_contact.getText().toString();
                address = et_address.getText().toString();
                performSignUp();
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent login_Activity = new Intent(signup.this, login.class);
//                login_Activity.putExtra("sl_type",sl_type);
//                startActivity(login_Activity);
                finish();
            }
        });

    }

    private boolean validation()
    {
        valid = true;
        if (TextUtils.isEmpty(username)){
            et_username.setError("required");
            valid = false;
        }

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

        if (TextUtils.isEmpty(contact)){
            et_contact.setError("required");
            valid = false;
        }
        else if (!contact.matches(contactPattren)){
            extraFunctions.text.setText(R.string.icontactno);
            extraFunctions.toast.show();
            valid = false;
        }

        if (TextUtils.isEmpty(address)){
            et_address.setError("required");
            valid = false;
        }

        return valid;
    }

    private void performSignUp(){

        if (!validation()){
            return;
        }

        extraFunctions.showProgressDialog(signup.this,"Authenticating...");

        user = new cust_sell_registration(username,email,password,Long.parseLong(contact),address);

        //Call<cust_sell_registration> cust_sell_registrationCall = apiInterface.signup(username,email,password,contact,address);

        if (sl_type.equals("sellers")){
            Call<cust_sell_login> cust_sell_registrationCall = apiInterface.sellerSignup(user);

            cust_sell_registrationCall.enqueue(new Callback<cust_sell_login>() {
                @Override
                public void onResponse(Call<cust_sell_login> call, Response<cust_sell_login> response) {
                    userStatus = response.body().getUserStatus();
                    if (userStatus.equals("verification email sent")){
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.verifyemail) + email);
                        extraFunctions.toast.show();

                        et_username.getText().clear();
                        et_email.getText().clear();
                        et_password.getText().clear();
                        et_contact.getText().clear();
                        et_address.getText().clear();

                    }
                    else if (userStatus.equals("verification email not sent")){
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.notverifyemail));
                        extraFunctions.toast.show();
                    }
                    else if (userStatus.equals(" exist")) {
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.seller) + getResources().getString(R.string.ae));
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
            Call<cust_sell_login> cust_sell_registrationCall = apiInterface.customerSignup(user);

            cust_sell_registrationCall.enqueue(new Callback<cust_sell_login>() {
                @Override
                public void onResponse(Call<cust_sell_login> call, Response<cust_sell_login> response) {
                    userStatus = response.body().getUserStatus();
                    if (userStatus.equals("verification email sent")){
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.verifyemail) + email);
                        extraFunctions.toast.show();

                        et_username.getText().clear();
                        et_email.getText().clear();
                        et_password.getText().clear();
                        et_contact.getText().clear();
                        et_address.getText().clear();
                    }
                    else if (userStatus.equals("verification email not sent")){
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.notverifyemail));
                        extraFunctions.toast.show();
                    }
                    else if (userStatus.equals(" exist")) {
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.cust) + getResources().getString(R.string.ae));
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
