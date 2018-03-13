package com.powerge.wise.powerge.config.soap;


import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.config.soap.response.ResponseEnvelope;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * soap 的请求接口发起
 * Created by xuzhiguang on 2018/2/1.
 */

public interface ApiService {
    @Headers({
            "Content-Type: text/xml",
            "Accept-Charset: utf-8"
    })
    @POST("YxywService?wsdl")
    Call<ResponseEnvelope> login(@Body RequestEnvelope requestEnvelope);



    class Creator {
        private static Strategy strategy = new AnnotationStrategy();
        private static Serializer serializer = new Persister(strategy);
        private static ApiService apiService;

        public static ApiService get() {
            if (apiService == null) {
                create();
            }
            return apiService;
        }

        private static synchronized void create() {
            if (apiService == null) {
                apiService = getRetrofit().create(ApiService.class);
            }
        }

        private static Retrofit getRetrofit() {
            return new Retrofit.Builder()
                    .baseUrl(Path.BASE_URL)
                    .client(RetrofitGenerator.getInstance().getOkHttpClient())
                    .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                    .addConverterFactory(GsonConverterFactory.create(RetrofitGenerator.getInstance().getGson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

}
