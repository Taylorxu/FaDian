package com.powerge.wise.powerge.config.soap.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * 在body节点下的数据摸型
 * Created by xuzhiguang on 2018/2/1.
 */

@Root(name = "ns2:mobileLoginResponse")
@Namespace(reference = "http://yxyw.extinterface.web.wisesign.cn/",prefix = "ns2")
public class UnderBodyModel {


    @Element(name = "return",required = false)
    public String result;
}
