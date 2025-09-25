package com.bioceuticamilano.network;

import android.util.Log;

import com.bioceuticamilano.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static RetrofitClient instance;

    public static ApiCall getInstance() {
        if (instance == null)
            instance = new RetrofitClient();
        return instance.getApiClient();
    }

    private OkHttpClient createOkHttpClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(120, TimeUnit.SECONDS);
        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY));
        httpClient.addInterceptor(chain -> {
            okhttp3.Request request = chain.request();
            Log.d("RETROFIT_API_CALL", request.method() + " " + request.url());
            return chain.proceed(request);
        });
        return httpClient.build();
    }


    private Retrofit createRetrofit() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build();
    }

    public ApiCall getApiClient() {
        final Retrofit retrofit = createRetrofit();
        return retrofit.create(ApiCall.class);
    }

}
