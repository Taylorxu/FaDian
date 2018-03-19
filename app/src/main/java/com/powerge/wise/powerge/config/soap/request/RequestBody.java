package com.powerge.wise.powerge.config.soap.request;

import com.powerge.wise.powerge.bean.DianLiangBean;
import com.powerge.wise.powerge.bean.FuHeYTChartLineBean;
import com.powerge.wise.powerge.bean.FuHeYTFormDataBean;
import com.powerge.wise.powerge.bean.JiZuBean;
import com.powerge.wise.powerge.bean.MorningMeetingBean;
import com.powerge.wise.powerge.bean.PeroidDateLineListBean;
import com.powerge.wise.powerge.bean.QueXianMagBean;
import com.powerge.wise.powerge.bean.SheBeiRootBean;
import com.powerge.wise.powerge.bean.TongJiForm;
import com.powerge.wise.powerge.bean.ZhiZhangLogBean;
import com.powerge.wise.powerge.config.soap.beans.LoginBean;

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
            @Element(name = "n0:queryUnits", type = JiZuBean.class),
            @Element(name = "n0:queryPowerGenerationData", type = DianLiangBean.class),
            @Element(name = "n0:queryLoadRealtimeData", type = FuHeYTChartLineBean.class),
            @Element(name = "n0:queryLoadStatisticData", type = FuHeYTFormDataBean.class),
            @Element(name = "n0:queryLoadRatioData", type = PeroidDateLineListBean.class),
            @Element(name = "n0:queryMonitorLogs", type = ZhiZhangLogBean.class)
    })
    public Data requestModel;

}
