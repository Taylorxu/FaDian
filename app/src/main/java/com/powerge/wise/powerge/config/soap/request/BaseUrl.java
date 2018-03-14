package com.powerge.wise.powerge.config.soap.request;

/**
 * Created by Administrator on 2018/1/30.
 */

public class BaseUrl {
    public static final boolean DEBUG = false;

    public static final String Host = DEBUG ? "http://work.wisesignsoft.com:20184/MWEB/services/" : "http://192.168.88.161:9170/MWEB/services/";
    public static final String NAMESPACE_Y = "http://yxyw.extinterface.web.wisesign.cn/";
    public static final String NAMESPACE_P = "http://powergene.extinterface.web.wisesign.cn/"; // DIAN CHANG
    public static final String SERVICE_P = "PowerGenerationService?wsdl";// DIAN CHANG
    public static final String SERVICE_Y = "YxywService?wsdl";//ITIL


}
