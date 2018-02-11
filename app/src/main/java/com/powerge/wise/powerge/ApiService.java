package com.powerge.wise.powerge;

import com.powerge.wise.basestone.heart.network.NetConfig;
import com.powerge.wise.basestone.heart.network.TopResponse;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.BaseUrl;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xuzhiguang on 2018/1/30.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("MobileAppv1/login")
    Observable<Response<TopResponse<User>>> login(@Field("account") String account, @Field("password") String password);

    class Creator {
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
                    .baseUrl(BaseUrl.Host)
                    .client(NetConfig.getInstance().getClient())
                    .addConverterFactory(GsonConverterFactory.create(NetConfig.getInstance().getGson()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }
}
