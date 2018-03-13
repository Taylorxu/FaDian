package com.powerge.wise.powerge.config.soap.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Envelope 下的节点
 * Created by xuzhiguang on 2018/2/1.
 */

@Root(name = "soap:Body")
public class ResponseBody {
    @Element(name="mobileLoginResponse" ,required = false)
    public UnderBodyModel underBodyModel;

    public UnderBodyModel getUnderBodyModel() {
        return underBodyModel;
    }

    public void setUnderBodyModel(UnderBodyModel underBodyModel) {
        this.underBodyModel = underBodyModel;
    }
}
