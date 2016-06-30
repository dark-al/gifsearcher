package com.antonbelka.gifsearcher.gifsearcher.data;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GiphyApi {
    public static final String API_URL = "http://api.giphy.com";
    public final static String EMPTY = "";
    public final static String API_KEY = "dc6zaTOxFJmzC";

    private static OkHttpClient getHttpClient() {
        return new OkHttpClient().newBuilder()
                .build();
    }

    @NonNull
    public static GiphyServiceApi getGiphyServiceApi() {
        return getRetrofit().create(GiphyServiceApi.class);
    }

    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient())
                .build();
    }

}
