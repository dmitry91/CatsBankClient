package com.dmitry.catsbankclient.services;

import com.dmitry.catsbankclient.services.interfaces.CatsApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private static String urlApi = "http://192.168.1.5:8080/"; // localhost:8080

    public Client() {
    }

    public CatsApi getCatsApi() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(CatsApi.class);
    }
}
