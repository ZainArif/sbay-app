package com.sbay.mrz.sbay;

import com.google.gson.annotations.SerializedName;

public class cust_sell_registration {

    @SerializedName("_id")
    private String Id;

    @SerializedName("name")
    private String Name;

    @SerializedName("email")
    private String Email;

    @SerializedName("password")
    private String Password;

    @SerializedName("contact")
    private long Contact;

    @SerializedName("address")
    private String Address;

    public cust_sell_registration(String name, String email, String password, long contact, String address) {
        Name = name;
        Email = email;
        Password = password;
        Contact = contact;
        Address = address;
    }

    public cust_sell_registration(String name, long contact, String address) {
        Name = name;
        Contact = contact;
        Address = address;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public long getContact() {
        return Contact;
    }

    public String getAddress() {
        return Address;
    }
}