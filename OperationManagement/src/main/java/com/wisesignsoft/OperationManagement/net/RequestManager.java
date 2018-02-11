package com.wisesignsoft.OperationManagement.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

import com.wisesignsoft.OperationManagement.Protocol;
import com.wisesignsoft.OperationManagement.net.response.LoginResponse;

/**
 * Created by ycs on 2016/11/23.
 */

public class RequestManager {
    private CallBack callBack;

    public static RequestManager getInstance() {
        return new RequestManager();
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case -1:
                    callBack.onError("请求失败");
                    break;
                case 0:
                    SoapObject object = (SoapObject) message.obj;
                    String o = object.getProperty(0).toString();
                    callBack.response(o);
                    break;
            }
            return true;
        }
    });

    public void requestLogin(String method, List<String> list, final RequestTask.ResultCallback<LoginResponse> callBack) {
        String namespae = Protocol.yxyw_name_space;
        String url = Protocol.getYxywHostUrl();
        RequestTask.getTask().runTask(namespae, method, url, list, new RequestTask.ResultCallback<LoginResponse>() {
            @Override
            public void onError(Exception e) {
                callBack.onError(e);
            }

            @Override
            public void onResponse(LoginResponse response) {
                callBack.onResponse(response);
            }
        });
    }

    public void requestYxyw(final List<String> list, final String method, final CallBack callBack) {
        this.callBack = callBack;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String url = Protocol.getYxywHostUrl();
                final String namespace = Protocol.yxyw_name_space;
                SoapObject request = new SoapObject(namespace, method);
                if (list == null) {
                    return;
                }
                if (list.size() != 0) {
                    for (int i = 0; i < list.size(); i++) {
                        request.addProperty("arg" + i, list.get(i));
                    }
                }
                final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
                envelope.bodyOut = request;
                final HttpTransportSE httpTransportSE = new HttpTransportSE(url);
                Message message = Message.obtain();
                try {
                    httpTransportSE.call(namespace + method, envelope);//调用
                } catch (Exception e) {
                    message.what = -1;
                    mHandler.sendMessage(message);
                    e.printStackTrace();
                }
                Log.i("YCS", "run: " + envelope.bodyIn.toString());
                if (envelope.bodyIn instanceof SoapObject) {
                    SoapObject object = (SoapObject) envelope.bodyIn;
                    message.obj = object;
                    message.what = 0;
                    mHandler.sendMessage(message);
                } else {
                    SoapFault fault = (SoapFault) envelope.bodyIn;
                    message.obj = fault;
                    message.what = 0;
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }

    public void requestUser(final List<String> list, final String method, final CallBack callBack) {
        this.callBack = callBack;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String url = Protocol.getUserHostUrl();
                final String namespace = Protocol.user_name_space;
                SoapObject request = new SoapObject(namespace, method);
                if (list == null) {
                    return;
                }
                if (list.size() != 0) {
                    for (int i = 0; i < list.size(); i++) {
                        request.addProperty("arg" + i, list.get(i));
                    }
                }
                final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
                envelope.bodyOut = request;
                final HttpTransportSE httpTransportSE = new HttpTransportSE(url);
                Message message = Message.obtain();
                try {
                    httpTransportSE.call(namespace + method, envelope);//调用
                } catch (Exception e) {
                    message.what = -1;
                    mHandler.sendMessage(message);
                    e.printStackTrace();
                }
                Log.i("YCS", "run: " + envelope.bodyIn.toString());
                if (envelope.bodyIn instanceof SoapObject) {
                    SoapObject object = (SoapObject) envelope.bodyIn;
                    message.obj = object;
                    message.what = 0;
                    mHandler.sendMessage(message);
                } else {
                    SoapFault fault = (SoapFault) envelope.bodyIn;
                    message.obj = fault;
                    message.what = 0;
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }

    public interface CallBack {
        void response(String t);

        void onError(String message);
    }
}
