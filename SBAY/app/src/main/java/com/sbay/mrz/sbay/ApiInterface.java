package com.sbay.mrz.sbay;

import android.support.annotation.RequiresPermission;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

//    @POST("postseller")
//    Call<cust_sell_registration> signup(@Query("name") String Name, @Query("email") String Email, @Query("password") String Password, @Query("contact") long Contact, @Query("address") String Address);

    @POST("postseller")
    Call<cust_sell_login> sellerSignup(@Body cust_sell_registration registration );

    @POST("postcustomer")
    Call<cust_sell_login> customerSignup(@Body cust_sell_registration registration );

    @POST("getseller")
    Call<cust_sell_login> sellerLogin(@Query("email") String email, @Query("password") String password);

    @POST("getcustomer")
    Call<cust_sell_login> customerLogin(@Query("email") String email, @Query("password") String password);

    @GET("getproducts")
    Call<List<softwareDetails>> allSoftwares();

    @GET("androidproducts")
    Call<List<softwareDetails>> mobileApps();

    @GET("webproducts")
    Call<List<softwareDetails>> webApps();

    @GET("vrar")
    Call<List<softwareDetails>> vrar();

    @GET("ai")
    Call<List<softwareDetails>> ai();

    @GET("ecommerce")
    Call<List<softwareDetails>> ecommerce();

    @GET("iot")
    Call<List<softwareDetails>> iot();

    @POST("postproduct")
    Call<softwareDetails> postProduct(@Body softwareDetails details);

    @POST("getSellerProducts")
    Call<List<softwareDetails>> getSellerProducts(@Query("seller_id") String seller_id);

    @DELETE("delProduct")
    Call<softwareDetails> delProduct(@Query("productid") String productId);

    @PATCH("updateProduct")
    Call<softwareDetails> updateProduct(@Query("productid") String productId, @Body softwareDetails details);
}
