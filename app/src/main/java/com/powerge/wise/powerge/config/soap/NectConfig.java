package com.powerge.wise.powerge.config.soap;

import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Administrator on 2018/2/2.
 */

public class NectConfig {

    public static ApiService weatherInterfaceApi;

    private static Strategy strategy = new AnnotationStrategy();
    private static Serializer serializer = new Persister(strategy);

    private static OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder retrofitBuilder =  new Retrofit.Builder()
            .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
            .baseUrl(Path.BASE_URL);

    public static <S> S createService(Class<S> serviceClass) {
        OkHttpClient client = okHttpClient.connectTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(new LogInterceptor())
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = retrofitBuilder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public static ApiService getInterfaceApi() {
        if(weatherInterfaceApi == null) {
            weatherInterfaceApi = createService(ApiService.class);
        }
        return weatherInterfaceApi;

    }
}
