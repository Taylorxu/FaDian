package com.powerge.wise.powerge.config.soap.request;

import com.powerge.wise.powerge.bean.JiZuBean;
import com.powerge.wise.powerge.bean.MorningMeetingBean;
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
            @Element(name = "n0:queryMonitorLogs", type = ZhiZhangLogBean.class)
    })
    public Data requestModel;

}
