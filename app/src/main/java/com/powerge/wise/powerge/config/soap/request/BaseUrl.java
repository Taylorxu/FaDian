package com.powerge.wise.powerge.config.soap.request;


import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;

/**
 * Created by Administrator on 2018/1/30.
 * "http://60.8.94.102:9184/MWEB/"; // 张家口
 * "http://192.168.88.161:9170/MWEB/"; // 杨志达
 * "http://192.168.88.182:9180/IMS/"; // 李天勇
 */

public class BaseUrl {
    public static final boolean DEBUG = false;
    public static final String[] HOSTArray = new String[]{"http://60.8.94.102:9184/MWEB/services/", "http://192.168.88.160:9170/MWEB/services/", "http://192.168.88.182:9180/IMS/services/"};
    public static final String Host = MySharedpreferences.getServerString()+"services/";    //DEBUG ? "http://work.wisesignsoft.com:20184/MWEB/services/" : "http://192.168.88.161:9170/MWEB/services/";
    public static final String NAMESPACE_Y = "http://yxyw.extinterface.web.wisesign.cn/";
    public static final String NAMESPACE_P = "http://powergene.extinterface.web.wisesign.cn/"; // DIAN CHANG
    public static final String SERVICE_P = "PowerGenerationService?wsdl";// DIAN CHANG
    public static final String SERVICE_Y = "YxywService?wsdl";//ITIL


}
