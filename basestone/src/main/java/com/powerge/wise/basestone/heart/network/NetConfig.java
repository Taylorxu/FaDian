package com.powerge.wise.basestone.heart.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.powerge.wise.basestone.heart.WApp;
import com.powerge.wise.basestone.heart.util.LogUtils;
import com.powerge.wise.basestone.heart.util.PhoneInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 徐志广 on 2018/1/30.
 */

public class NetConfig implements Interceptor, CookieJar {
    private static NetConfig instance;

    private OkHttpClient client;
    private PersistentCookieStore cookieStore;
    private Gson gson;

    private NetConfig() {
    }

    public static NetConfig getInstance() {
        if (instance == null) {
            synchronized (NetConfig.class) {
                instance = new NetConfig();
            }
        }
        return instance;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                getCookieStore().add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return getCookieStore().get(url);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        Response response = chain.proceed(builder.build());
        okhttp3.MediaType mediaType = response.body().contentType();
        if ("text/xml;charset=UTF-8".equals(mediaType.toString())) {
            String content = response.body().string();
            String rebuildResult = content.substring(content.indexOf(">{") + 1, content.indexOf("</return>"));
            rebuildResult = rebuildResult.replace("&quot;", "\"");
            rebuildResult = rebuildResult.replace("\"returnValue\":\"\"", "\"returnValue\":null");
            return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, rebuildResult))
                    .build();
        }
        return response;
    }


    private PersistentCookieStore getCookieStore() {
        if (cookieStore == null)
            cookieStore = new PersistentCookieStore(WApp.getInstance().getApplicationContext());
        return cookieStore;
    }

    public OkHttpClient getClient() {
        if (client == null)
            client = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(this)
                    .build();
        return client;
    }

    public Gson getGson() {
        if (gson == null)
            gson = new GsonBuilder()
                    .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
                    .create();
        return gson;
    }
}
