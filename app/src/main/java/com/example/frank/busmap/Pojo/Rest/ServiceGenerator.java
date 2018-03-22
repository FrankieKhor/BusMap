package com.example.frank.busmap.Pojo.Rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by frank on 21/03/2018.
 */

public class ServiceGenerator {
    private static final String BASE_URL = "https://api.tfl.gov.uk/";

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson));

    private static Retrofit retrofit = builder.build();
    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();


    public static <S> S createTflClient(Class<S> serviceClass){
//        if(!httpClientBuilder.interceptors().contains(loggingInterceptor)){
//            httpClientBuilder.addInterceptor(loggingInterceptor);
//            builder = builder.client(httpClientBuilder.build());
//            retrofit = builder.build();
//        }
        return retrofit.create(serviceClass);
    }
}
