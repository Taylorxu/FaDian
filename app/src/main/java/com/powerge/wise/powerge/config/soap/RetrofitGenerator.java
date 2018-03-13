package com.powerge.wise.powerge.config.soap;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.powerge.wise.powerge.config.soap.response.ResponseEnvelope;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Retrofit变量初始化
 * Created by SmileXie on 16/7/16.
 */
public class RetrofitGenerator implements Interceptor {
    public static RetrofitGenerator instance;
    private Gson gson;

    public static RetrofitGenerator getInstance(){
        if (instance == null) {
            synchronized (RetrofitGenerator.class) {
                instance = new RetrofitGenerator();
            }
        }
        return instance;
    }

    static OkHttpClient client ;
    public OkHttpClient getOkHttpClient(){
        if(client==null){
            client = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
//                    .addInterceptor(this)
                    .build();
        }
        return client;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("Content-Type", "text/xml;charset=UTF-8")
                .method(original.method(), original.body());
        Request request = requestBuilder.build();
        Response response=chain.proceed(request);

        okhttp3.MediaType mediaType = response.body().contentType();
        if ("text/xml;charset=ISO-8859-1".equals(mediaType.toString())) {
            String content = response.body().string();
            Log.e("拦截中response",content);
            ResponseEnvelope responseEnvelope=new Gson().fromJson(content, ResponseEnvelope.class);
            String rebuildResult=responseEnvelope.getResponseBody().getUnderBodyModel().result;
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, rebuildResult))
                    .build();
        }
        return response;
    }

    public Gson getGson() {
        if (gson == null)
            gson = new GsonBuilder()
                    .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
                    .create();
        return gson;
    }
    
    
}
