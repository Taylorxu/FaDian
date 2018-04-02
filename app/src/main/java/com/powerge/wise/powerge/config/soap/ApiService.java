package com.powerge.wise.powerge.config.soap;


import com.powerge.wise.basestone.heart.network.NetConfig;
import com.powerge.wise.basestone.heart.network.ResultModelData;
import com.powerge.wise.basestone.heart.util.LogUtils;
import com.powerge.wise.powerge.bean.DianLiangBean;
import com.powerge.wise.powerge.bean.FuHeHourDataBean;
import com.powerge.wise.powerge.bean.FuHeYTChartLineBean;
import com.powerge.wise.powerge.bean.FuHeYTFormDataBean;
import com.powerge.wise.powerge.bean.GonGaoBean;
import com.powerge.wise.powerge.bean.HuanBaoBean;
import com.powerge.wise.powerge.bean.JiZuBean;
import com.powerge.wise.powerge.bean.JingSaiDeFenBean;
import com.powerge.wise.powerge.bean.KaoHeChildItemBean;
import com.powerge.wise.powerge.bean.MainPageBean;
import com.powerge.wise.powerge.bean.MorningMeetingBean;
import com.powerge.wise.powerge.bean.PeroidDateLineListBean;
import com.powerge.wise.powerge.bean.PlanTaskBean;
import com.powerge.wise.powerge.bean.PlanTaskDetailBean;
import com.powerge.wise.powerge.bean.QueXianFormBean;
import com.powerge.wise.powerge.bean.QueXianMagBean;
import com.powerge.wise.powerge.bean.SheBeiRootBean;
import com.powerge.wise.powerge.bean.TongJiForm;
import com.powerge.wise.powerge.bean.Weather;
import com.powerge.wise.powerge.bean.XunJianSignBean;
import com.powerge.wise.powerge.bean.ZhiBIaoValueBean;
import com.powerge.wise.powerge.bean.ZhiBaioNameBean;
import com.powerge.wise.powerge.bean.ZhiZhangLogBean;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.beans.LoginBean;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.otherPages.xunJian.SignSoapRequest;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.util.List;

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

    //缺陷管理
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<QueXianFormBean>>> queryIssueStatisticsMonthly(@Body RequestEnvelope requestEnvelope);//缺陷管理统计

    //查询机组数据
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModelData<ResultModelData.ReturnValueBean<JiZuBean>>>> queryUnits(@Body RequestEnvelope requestEnvelope);

    //查询电量
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<DianLiangBean>>> queryPowerGenerationData(@Body RequestEnvelope requestEnvelope);

    //负荷昨日  今日
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<FuHeYTChartLineBean>>> queryLoadRealtimeData(@Body RequestEnvelope requestEnvelope);

    //查询负荷日统计数据
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<List<FuHeYTFormDataBean>>>> queryLoadStatisticData(@Body RequestEnvelope requestEnvelope);

    //查询日负荷率曲线
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<List<PeroidDateLineListBean>>>> queryLoadRatioData(@Body RequestEnvelope requestEnvelope);

    //查询当日环保指标
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<List<HuanBaoBean>>>> queryEnvironmentalIndicators(@Body RequestEnvelope requestEnvelope);


    //查询当日环保指标
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<List<KaoHeChildItemBean>>>> queryEnvAssessmentMonthly(@Body RequestEnvelope requestEnvelope);

    // 获取指标名称列表
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<List<ZhiBaioNameBean>>>> queryEconomicIndicatorsList(@Body RequestEnvelope requestEnvelope);

    // 获取各机组指标值列表
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<List<ZhiBIaoValueBean>>>> queryEconomicIndicators(@Body RequestEnvelope requestEnvelope);// 获取各机组指标值列表

    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<List<FuHeHourDataBean>>>> queryLoadDetailsInHour(@Body RequestEnvelope requestEnvelope);

    /**
     * 巡检管理
     *
     * @param requestEnvelope
     * @return
     */
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<List<XunJianSignBean>>>> queryInspectionResultData(@Body RequestEnvelope requestEnvelope);

    /**
     * 巡检管理
     *
     * @param requestEnvelope
     * @return
     */
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<SignSoapRequest>>> inspectPoint(@Body RequestEnvelope requestEnvelope);

    //公告
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModelData<ResultModelData.ReturnValueBean<GonGaoBean>>>> queryNotice(@Body RequestEnvelope requestEnvelope);

    /**
     * 计划任务
     *
     * @param requestEnvelope
     * @return
     */
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModelData<ResultModelData.ReturnValueBean<PlanTaskBean>>>> queryWorkPlan(@Body RequestEnvelope requestEnvelope);

    /**
     * 计划详情
     *
     * @param requestEnvelope
     * @return
     */
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModelData<ResultModelData.ReturnValueBean<PlanTaskDetailBean>>>> queryWorkTask(@Body RequestEnvelope requestEnvelope);

    /**
     * 竞赛得分
     *
     * @param requestEnvelope
     * @return
     */
    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<JingSaiDeFenBean>>> queryGroupScore(@Body RequestEnvelope requestEnvelope);

    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<Weather>>> queryWeatherInfo(@Body RequestEnvelope requestEnvelope);


    @Headers({
            "Content-Type:text/xml; charset=utf-8",
            "Accept-Charset: utf-8"
    })
    @POST(BaseUrl.SERVICE_P)
    Observable<Response<ResultModel<MainPageBean>>> queryMainPageData(@Body RequestEnvelope requestEnvelope);


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
            LogUtils.e("BaseUrl.getHost()" + BaseUrl.getHost());
            return new Retrofit.Builder()
                    .baseUrl(BaseUrl.getHost())
                    .client(NetConfig.getInstance().getClient())
                    .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                    .addConverterFactory(GsonConverterFactory.create(NetConfig.getInstance().getGson()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }

}
