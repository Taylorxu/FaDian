package com.powerge.wise.powerge.config.soap;


import com.powerge.wise.basestone.heart.network.NetConfig;
import com.powerge.wise.basestone.heart.network.ResultModelData;
import com.powerge.wise.powerge.bean.Items;
import com.powerge.wise.powerge.bean.JiZuBean;
import com.powerge.wise.powerge.bean.MorningMeetingBean;
import com.powerge.wise.powerge.bean.QueXianMagBean;
import com.powerge.wise.powerge.bean.SheBeiRootBean;
import com.powerge.wise.powerge.bean.TongJiForm;
import com.powerge.wise.powerge.bean.ZhiZhangLogBean;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.beans.LoginBean;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;


/**
 * soap 的请求接口发起
 * Created by xuzhiguang on 2018/2/1.
 */

public interface ApiService {
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_Y)
    Observable<Response<ResultModel<LoginBean>>> login(@Body RequestEnvelope requestEnvelope);

    //设备信息查询
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModelData<ResultModelData.ReturnValueBean<SheBeiRootBean>>>> queryDevicesData(@Body RequestEnvelope requestEnvelope);

    //早会
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModelData<ResultModelData.ReturnValueBean<MorningMeetingBean>>>> queryProductionEarlyMeetingData(@Body RequestEnvelope requestEnvelope);

    //统计报表
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModelData<ResultModelData.ReturnValueBean<TongJiForm>>>> queryStatisticData(@Body RequestEnvelope requestEnvelope);

    //值长日志
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModelData<ResultModelData.ReturnValueBean<ZhiZhangLogBean>>>> queryMonitorLogs(@Body RequestEnvelope requestEnvelope);

    //缺陷管理
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModelData<ResultModelData.ReturnValueBean<QueXianMagBean>>>> queryIssueDetails(@Body RequestEnvelope requestEnvelope);//缺陷管理

    //查询机组数据
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModelData<ResultModelData.ReturnValueBean<JiZuBean>>>> queryUnits(@Body RequestEnvelope requestEnvelope);


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
                    .baseUrl(BaseUrl.HOSTArray[2])
                    .client(NetConfig.getInstance().getClient())
                    .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                    .addConverterFactory(GsonConverterFactory.create(NetConfig.getInstance().getGson()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }

}
