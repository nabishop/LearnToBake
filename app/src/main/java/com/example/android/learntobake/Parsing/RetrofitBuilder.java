package com.example.android.learntobake.Parsing;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static RetrofitInterface retrofitInterface;
    private static OkHttpClient httpClient = new OkHttpClient.Builder().build();

    public static RetrofitInterface getRecipes() {
        retrofitInterface = new Retrofit.Builder().baseUrl(ParsingInfo.BASE_URL)
                .client(httpClient).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build().create(RetrofitInterface.class);
        return retrofitInterface;
    }
}
