package com.sbay.mrz.sbay;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button customer;
    private Button seller;

    private String username;
    private String email;
    private String menuType;
    private String seller_cust_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customer = (Button)findViewById(R.id.btn_customer);
        seller = (Button)findViewById(R.id.btn_seller);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_Activity = new Intent(MainActivity.this, login.class);
                login_Activity.putExtra("sl_type","customers");
                startActivityForResult(login_Activity,2);
            }
        });

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_Activity = new Intent(MainActivity.this, login.class);
                login_Activity.putExtra("sl_type","sellers");
                startActivityForResult(login_Activity,2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                username = data.getStringExtra("username");
                email = data.getStringExtra("email");
                menuType = data.getStringExtra("menu type");
                seller_cust_id = data.getStringExtra("seller_cust_id");

                Intent home_Activity = new Intent();
                home_Activity.putExtra("seller_cust_id",seller_cust_id);
                home_Activity.putExtra("username",username);
                home_Activity.putExtra("email",email);
                home_Activity.putExtra("menu type",menuType);
                setResult(RESULT_OK,home_Activity);
                finish();
            }
        }
    }
}