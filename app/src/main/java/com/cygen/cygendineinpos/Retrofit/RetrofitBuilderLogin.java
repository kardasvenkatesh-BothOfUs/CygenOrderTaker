package com.cygen.cygendineinpos.Retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilderLogin {
//    static String userId;

    //    public static String baseUrl = "http://cygenpos.com.au/dev/retailapi/api/";
//    public static String baseUrl = "http://cy-gen.com/ssbservice/";
//    public static String baseUrl = "https://cygenpos.com/cygenposapi/restu/v1/api/";
    public static String baseUrl = "https://cygenpos.com/cygenposapi/test_new/v1/api/";

    public static Retrofit getApiBuilder() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(240, TimeUnit.SECONDS)
                .readTimeout(240, TimeUnit.SECONDS)
                .writeTimeout(240, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl).client(httpClient.build())
                .build();

        return retrofit;
    }

}
