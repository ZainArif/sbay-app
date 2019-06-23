package com.sbay.mrz.sbay;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {

    private SharedPreferences sharedPreferences;
    private Context context;

    private String name;
    private String email;
    private String contact;
    private String address;
    private String seller_cust_id;
    private String menu_type;

    public MySharedPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preference),Context.MODE_PRIVATE);
    }

    public void writeLoginInfo(String seller_cust_id, String name, String email, String contact, String address,String menu_type, Boolean loginStatus){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("seller_cust_id",seller_cust_id);
        editor.putString("name",name);
        editor.putString("email",email);
        editor.putString("contact",contact);
        editor.putString("address",address);
        editor.putString("menu type",menu_type);
        editor.putBoolean("loginStatus",loginStatus);
        editor.commit();
    }

    public boolean readLoginStatus(){
        boolean loginStatus = false;
        loginStatus = sharedPreferences.getBoolean("loginStatus",false);
        return loginStatus;
    }

    public String[] readLoginInfo(){
        String[] loginInfo = new String[6];
        loginInfo[0] = sharedPreferences.getString("seller_cust_id",null);
        loginInfo[1] = sharedPreferences.getString("name",null);
        loginInfo[2] = sharedPreferences.getString("email",null);
        loginInfo[3] = sharedPreferences.getString("contact",null);
        loginInfo[4] = sharedPreferences.getString("address",null);
        loginInfo[5] = sharedPreferences.getString("menu type",null);
        return loginInfo;
    }
}
