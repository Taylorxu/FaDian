package com.powerge.wise.powerge.config.soap.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by xu on 2018/2/1.
 */
@Root(name = "n0:mobileLogin" ,strict = false)
@Namespace(reference ="http://yxyw.extinterface.web.wisesign.cn/",prefix = "n0")
public class RequestModel {
        public static RequestModel requestModel;
        @Element(name = "arg0" )
        public String userName;
        @Element(name = "arg1" )
        public String userPassward;

        public static RequestModel getRequestModel(){
            if(null==requestModel){
                requestModel=new RequestModel();
            }
            return requestModel;
        }

}
