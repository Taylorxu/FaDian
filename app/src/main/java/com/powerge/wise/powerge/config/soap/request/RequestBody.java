package com.powerge.wise.powerge.config.soap.request;

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
import com.powerge.wise.powerge.bean.PaiMingChildItemBean;
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
import com.powerge.wise.powerge.config.soap.beans.LoginBean;
import com.powerge.wise.powerge.otherPages.xunJian.SignSoapRequest;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.Root;

/**
 * Created by xuzhiguang on 2018/2/1.
 */
@Root(name = "v:Body")
@Default(DefaultType.FIELD)
public class RequestBody<Data> {

    public RequestBody(Data requestModel) {
        this.requestModel = requestModel;
    }

    @ElementUnion({
            @Element(name = "n0:mobileLogin", type = LoginBean.class),
            @Element(name = "n0:queryProductionEarlyMeetingData", type = MorningMeetingBean.class),
            @Element(name = "n0:queryDevicesData", type = SheBeiRootBean.class),
            @Element(name = "n0:queryStatisticData", type = TongJiForm.class),
            @Element(name = "n0:queryIssueDetails", type = QueXianMagBean.class),
            @Element(name = "n0:queryIssueStatisticsMonthly", type = QueXianFormBean.class),
            @Element(name = "n0:queryUnits", type = JiZuBean.class),
            @Element(name = "n0:queryPowerGenerationData", type = DianLiangBean.class),
            @Element(name = "n0:queryLoadRealtimeData", type = FuHeYTChartLineBean.class),
            @Element(name = "n0:queryLoadStatisticData", type = FuHeYTFormDataBean.class),
            @Element(name = "n0:queryLoadRatioData", type = PeroidDateLineListBean.class),
            @Element(name = "n0:queryEnvironmentalIndicators", type = HuanBaoBean.class),
            @Element(name = "n0:queryEconomicIndicatorsList", type = ZhiBaioNameBean.class),
            @Element(name = "n0:queryEconomicIndicators", type = ZhiBIaoValueBean.class),
            @Element(name = "n0:queryLoadDetailsInHour", type = FuHeHourDataBean.class),
            @Element(name = "n0:queryInspectionResultData", type = XunJianSignBean.class),
            @Element(name = "n0:inspectPoint", type = SignSoapRequest.class),
            @Element(name = "n0:queryNotice", type = GonGaoBean.class),
            @Element(name = "n0:queryEnvAssessmentMonthly", type = KaoHeChildItemBean.class),
            @Element(name = "n0:queryWorkPlan", type = PlanTaskBean.class),
            @Element(name = "n0:queryWorkTask", type = PlanTaskDetailBean.class),
            @Element(name = "n0:queryGroupScore", type = JingSaiDeFenBean.class),
            @Element(name = "n0:queryWeatherInfo", type = Weather.class),
            @Element(name = "n0:queryMainPageData", type = MainPageBean.class),
            @Element(name = "n0:queryRankOfMonthData", type = PaiMingChildItemBean.class),
            @Element(name = "n0:queryMonitorLogs", type = ZhiZhangLogBean.class)
    })
    public Data requestModel;

}
