package com.shijil.retrofit_19_example.web;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by shijil on 7/7/2015.
 */
public class ServiceGenerator {

    public static final String BASE_URL =//""; // replace with your base usrl
            "http://192.168.1.254/example/";



    // No need to instantiate this class.
    private  ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL);
                //.setClient(new OkClient(new OkHttpClient()));

        RestAdapter adapter = builder.build();
        adapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return adapter.create(serviceClass);
    }

    public static <S> S createServiceWithJsonHeader(Class<S> serviceClass) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(new SessionRequestInterceptor());
                //.setClient(new OkClient(new OkHttpClient()));

        RestAdapter adapter = builder.build();
        adapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return adapter.create(serviceClass);
    }

    public static class SessionRequestInterceptor implements RequestInterceptor {

        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Accept", "application/json");//you can add header here if you need in your api
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Accept-Encoding", "gzip");
        }
    }
}
