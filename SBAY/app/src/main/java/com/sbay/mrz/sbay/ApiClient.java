package com.sbay.mrz.sbay;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static String BASE_URL = "http://192.168.2.106:7000/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiClient(String mainRouteCall)
    {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL+mainRouteCall+"/").addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}
