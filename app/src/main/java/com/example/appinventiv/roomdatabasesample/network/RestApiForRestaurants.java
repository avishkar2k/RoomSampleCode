package com.example.appinventiv.roomdatabasesample.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import android.support.annotation.NonNull;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


/**
 * Created at Appinventiv on 9/4/18 at IST 11:45 PM
 * Project StudioProjects
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

public class RestApiForRestaurants {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().
            baseUrl("https://maps.googleapis.com").
            addConverterFactory(JacksonConverterFactory.create());

    public static <S> S createService(Class<S> aClass) {

        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.writeTimeout(60, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = retrofitBuilder.client(httpClient.build()).build();
        return retrofit.create(aClass);
    }
}


