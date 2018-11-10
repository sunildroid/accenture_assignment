package com.example.sunilkuntal.myalbum.data.repository.datasource.remote;

import android.content.Context;
import android.util.Log;

import com.example.sunilkuntal.myalbum.BuildConfig;
import com.example.sunilkuntal.myalbum.utils.InternetUtil;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by SunilKuntal on 25-04-2018.
 */

public class ServiceGenerator {

    private static InternetConnectionListener mInternetConnectionListener;
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static Context mContext;

    private static final Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static final HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS);

    public static <S> S createService(Context context,
                                      Class<S> serviceClass) {
        mContext = context;

        httpClient.addInterceptor(new NetworkConnectionInterceptor() {
            @Override
            public boolean isInternetAvailable() {
                return ServiceGenerator.isInternetAvailable();
            }

            @Override
            public void onInternetUnavailable() {
                if(mInternetConnectionListener!=null)
                mInternetConnectionListener.onInternetUnavailable();
            }
        });

        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
        }
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }


    private static boolean isInternetAvailable() {
        Log.d("Internet Status" ,"IS Available"+ InternetUtil.haveNetworkConnection(mContext));
        return InternetUtil.haveNetworkConnection(mContext);
    }


    public static void setInternetConnectionListener(InternetConnectionListener listener) {
        mInternetConnectionListener = listener;
    }

    public static void removeInternetConnectionListener() {
        mInternetConnectionListener = null;
    }

}


