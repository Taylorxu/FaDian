package com.powerge.wise.powerge.config.soap.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by xuzhiguang on 2018/2/1.
 */
@Root(name = "v:Body")
public class RequestBody {
    static RequestBody requestBody;
    @Element(name = "mobileLogin", required = false)
    public RequestModel mobileLogin;

    public RequestModel getMobileLogin() {
        return mobileLogin;
    }

    public void setMobileLogin(RequestModel mobileLogin) {
        this.mobileLogin = mobileLogin;
    }

    public static RequestBody  getRequestBody(){
        if(null==requestBody){
            requestBody=new RequestBody();
        }
        return requestBody;

    }
}
