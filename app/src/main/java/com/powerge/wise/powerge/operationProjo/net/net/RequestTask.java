package com.powerge.wise.powerge.operationProjo.net.net;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.powerge.wise.powerge.MyApplication;
import com.powerge.wise.powerge.operationProjo.net.Protocol;
import com.powerge.wise.powerge.operationProjo.net.net.response.BaseResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindUserByDeptIdResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.WorkOrderDetailResponse;
import com.powerge.wise.powerge.operationProjo.net.utils.PullPaseXmlUtil;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by ycs on 2016/11/25.
 */

public class RequestTask extends AsyncTask {

    private RequestTask() {
    }

    public static RequestTask getTask() {
        return new RequestTask();
    }

    private String namespace;
    private String method;
    private String url;
    private List<String> list;
    private ResultCallback callBack;

    /**
     * 运行任务
     *
     * @param namespae 命名空间
     * @param method   方法
     * @param url      url
     * @param list     参数的集合
     */
    public void runTask(String namespae, String method, String url, List<String> list, ResultCallback callBack) {
        this.namespace = namespae;
        this.method = method;
        this.url = url;
        this.list = list;
        this.callBack = callBack;
        Log.e("requestUrl",url+"/"+method+""+list.toString());
        execute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        SoapObject request = new SoapObject(namespace, method);
        if (list == null) {
            return null;
        }
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                request.addProperty("arg" + i, list.get(i));
            }
        }
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
        envelope.bodyOut = request;
        envelope.dotNet = false;
//        envelope.implicitTypes = true;
//        envelope.setOutputSoapObject(objects);
//        (new MarshalBase64()).register(envelope);
        final HttpTransportSE httpTransportSE = new HttpTransportSE(url, 30000);
        httpTransportSE.debug = true;
        try {
            httpTransportSE.call(namespace + method, envelope);//调用
            return envelope.bodyIn;
        } catch (final Exception e) {
            e.printStackTrace();
            startError("502", "服务器请求超时");
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    callBack.onError(e);
                }
            });
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        if (o != null) {
            if (o instanceof SoapObject) {
                SoapObject object = (SoapObject) o;
                String result;
                int count = object.getPropertyCount();
                if (count <= 0) {
                    Log.i("YCS", "onPostExecute: object:" + object.toString());
                    callBack.onError(new Exception());
                } else {
                    result = object.getProperty(0).toString();
                    Log.i("YCS", "onPostExecute: result:" + result);
                    if ("anyType{}".equals(result)) {
                        FindUserByDeptIdResponse response = new FindUserByDeptIdResponse();
                        response.setResult(null);
                        callBack.onResponse(response);
                        return;
                    }
                    if (result.startsWith("<") && method.equals(Protocol.findWorkOrderDetailByPiKey)) {
                        List list = PullPaseXmlUtil.pase(result);
                        WorkOrderDetailResponse response = new WorkOrderDetailResponse();
                        response.setResult(list);
                        callBack.onResponse(response);
                        return;
                    }
                    if (result.startsWith("[")) {
                        String head = "{\"result\":";
                        String foot = "}";
                        StringBuffer sb = new StringBuffer(head);
                        sb.append(result).append(foot);
                        result = sb.toString();
                    }
                    try {
                        if (result.equals("null object")) {
                            BaseResponse response = new BaseResponse();
                            response.setI("500");
                            callBack.onResponse(response);
                        } else {
                            int i = Integer.parseInt(result);
                            BaseResponse response = new BaseResponse();
                            response.setI(i + "");
                            callBack.onResponse(response);
                        }
                        return;
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    if (analyzeResponse(result)) {
                        Gson gson = new Gson();
                        Object obj = null;
                        try {
                            obj = gson.fromJson(result, callBack.mType);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callBack.onResponse(obj);
                    } else {
                        callBack.onError(new Exception());
                    }
                }
            } else {
                SoapFault fault = (SoapFault) o;
            }
        }
    }

    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Exception e);

        public abstract void onResponse(T response);
    }

    /**
     * 解析访问网络的返回参数
     */
    private boolean analyzeResponse(String response) {
        try {
            if (TextUtils.equals("0", response)) {
                return true;
            }
            Gson gson = new Gson();
            BaseResponse base = gson.fromJson(response, BaseResponse.class);
            String code = base.getReturnState();
            String msg = base.getReturnMsg();
            Log.i("YCS", "analyzeResponse: msg:" + msg);
            if (TextUtils.equals("0", code)) {
                return true;
            } else if (TextUtils.isEmpty(code)) {
                return true;
            } else {
                startError(code, msg);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            startError("2", response);
            return false;
        }
    }

    /**
     * 开启登录页面
     */
    private void startError(String code, String msg) {
        Intent intent = new Intent();
        intent.setAction("operationerror");
        intent.putExtra("error_code", code);
        intent.putExtra("msg", msg);
        MyApplication.getContext().sendBroadcast(intent);
    }
}
