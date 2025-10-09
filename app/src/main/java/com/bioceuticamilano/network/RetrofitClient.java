package com.bioceuticamilano.network;

import android.util.Log;

import com.bioceuticamilano.ui.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance;

    public static ApiCall getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance.getApiClient();
    }

    private OkHttpClient createOkHttpClient() {
        // 🔹 Retrofit built-in logger for basic request/response
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> {
            Log.d("RETROFIT_BODY", message);
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 🔹 Custom interceptor to log full endpoint, headers & body safely
        Interceptor customLogger = chain -> {
            Request request = chain.request();

            // Log full URL
            Log.d("RETROFIT_API_CALL", "➡️ " + request.method() + " " + request.url());

            // Log headers
            Log.d("RETROFIT_HEADERS", request.headers().toString());

            // Log request body (if small)
            if (request.body() != null) {
                try {
                    Buffer buffer = new Buffer();
                    request.body().writeTo(buffer);
                    Log.d("RETROFIT_REQUEST_BODY", buffer.readUtf8());
                } catch (IOException e) {
                    Log.e("RETROFIT_BODY_ERROR", "Failed to read request body: " + e.getMessage());
                }
            }

            // Proceed with call
            Response response = chain.proceed(request);

            // Clone and log response body safely
            ResponseBody responseBody = response.body();
            String responseBodyString = null;
            if (responseBody != null) {
                responseBodyString = responseBody.string();
                Log.d("RETROFIT_RESPONSE_BODY", responseBodyString);

                // Re-create the response body to not consume it
                response = response.newBuilder()
                        .body(ResponseBody.create(responseBody.contentType(), responseBodyString))
                        .build();
            }

            return response;
        };

        return new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(customLogger)
                .addInterceptor(logging)
                .build();
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
        return createRetrofit().create(ApiCall.class);
    }
}
