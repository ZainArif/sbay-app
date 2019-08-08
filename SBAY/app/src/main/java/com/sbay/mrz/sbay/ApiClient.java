package com.sbay.mrz.sbay;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static String BASE_URL = "https://sbay-mrz.herokuapp.com/";
    //public static String BASE_URL = "http://192.168.1.107:7000/";
    private static Retrofit retrofit = null;

    private static OkHttpClient client = null;

    public static Retrofit getApiClient(String mainRouteCall)
    {
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL+mainRouteCall+"/").client(client).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

}
