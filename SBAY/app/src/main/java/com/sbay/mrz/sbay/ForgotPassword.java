package com.sbay.mrz.sbay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    private EditText et_email;
    private Button btn_sendEmail;

    private String sl_type;
    private String email;
    private String resStatus;

    private ApiInterface apiInterface;
    private extraFunctions extraFunctions;
    private String emailPattern;
    private boolean valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        
        et_email = (EditText)findViewById(R.id.et_email);
        btn_sendEmail = (Button)findViewById(R.id.btn_spre);

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        sl_type = getIntent().getStringExtra("sl_type");
        apiInterface = ApiClient.getApiClient(sl_type).create(ApiInterface.class);

        extraFunctions  = new extraFunctions();
        extraFunctions.customToast(ForgotPassword.this,ForgotPassword.this);
        
        btn_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.closeKeyboard(ForgotPassword.this,ForgotPassword.this);
                email = et_email.getText().toString();
                sendPasswordResetEmail();
            }
        });
    }

    private boolean validation()
    {
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
        
        return valid;
    }

    private void sendPasswordResetEmail(){

        if (!validation()){
            return;
        }

        extraFunctions.showProgressDialog(ForgotPassword.this,"Sending password reset email...");

        PasswordResetResponse passwordResetResponse = new PasswordResetResponse(email);
        Call<PasswordResetResponse> passwordResetCall = apiInterface.sellerResetPsw(passwordResetResponse);
        passwordResetCall.enqueue(new Callback<PasswordResetResponse>() {
            @Override
            public void onResponse(Call<PasswordResetResponse> call, Response<PasswordResetResponse> response) {
                if (response.code() == 200) {
                    resStatus = response.body().getResponseStatus();
                    if (resStatus.equals("email not in db")) {
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.emailnotfound));
                        extraFunctions.toast.show();
                    } else if (resStatus.equals("recovery email sent")) {
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.resetemailsent) + email);
                        extraFunctions.toast.show();
                        finish();
                    }
                    else if (resStatus.equals("recovery email not sent")) {
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(getResources().getString(R.string.resetemailnotsent));
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
            public void onFailure(Call<PasswordResetResponse> call, Throwable t) {
                extraFunctions.hideProgressDialog();
                extraFunctions.text.setText(getResources().getString(R.string.sww));
                extraFunctions.toast.show();
            }
        });
    }
}
