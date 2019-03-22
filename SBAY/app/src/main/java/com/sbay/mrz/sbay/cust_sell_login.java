package com.sbay.mrz.sbay;

import com.google.gson.annotations.SerializedName;

public class cust_sell_login {

    @SerializedName("user")
    private cust_sell_registration custSellRegistration;
    @SerializedName("userStatus")
    private String UserStatus;

    public cust_sell_registration getCustSellRegistration() {
        return custSellRegistration;
    }

    public String getUserStatus() {
        return UserStatus;
    }
}
