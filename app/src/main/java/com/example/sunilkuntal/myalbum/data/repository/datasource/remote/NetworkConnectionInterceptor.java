package com.example.sunilkuntal.myalbum.data.repository.datasource.remote;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


abstract class NetworkConnectionInterceptor implements Interceptor {

    protected abstract boolean isInternetAvailable();

    protected abstract void onInternetUnavailable();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!isInternetAvailable()) {
            onInternetUnavailable();
           throw new NoConnectivityException();
          //  return ;
        }else
            return chain.proceed(request);
    }
}