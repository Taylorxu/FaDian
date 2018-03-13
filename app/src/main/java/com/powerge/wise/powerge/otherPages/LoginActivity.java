package com.powerge.wise.powerge.otherPages;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.TopResponse;
import com.powerge.wise.basestone.heart.util.LogUtils;
import com.powerge.wise.basestone.heart.util.MD5;
import com.powerge.wise.powerge.ApiService;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.NectConfig;
import com.powerge.wise.powerge.config.soap.beans.LoginBean;
import com.powerge.wise.powerge.config.soap.beans.ResultModel;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.config.soap.request.RequestModel;
import com.powerge.wise.powerge.config.soap.response.ResponseEnvelope;
import com.powerge.wise.powerge.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        //当确定密码时登录
        loginBinding.password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        // 点击登录按钮去登陆
        loginBinding.signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }


    private void attemptLogin() {
        //TODO 登录成功后 更新reaml 的数据
      /*  User.login();
        finish();*/
    }


    public void sing(View view) {

        RequestModel.getRequestModel().userName="itil";
        RequestModel.getRequestModel().userPassward="1";
        RequestBody.getRequestBody().setMobileLogin(RequestModel.getRequestModel());
        RequestEnvelope.getRequestEnvelope().setBody(RequestBody.getRequestBody());
        Call<ResponseEnvelope> call= NectConfig.getInterfaceApi().login(RequestEnvelope.getRequestEnvelope());
        call.enqueue(new Callback<ResponseEnvelope>() {
            @Override
            public void onResponse(Call<ResponseEnvelope> call, Response<ResponseEnvelope> response) {
                ResultModel resultModel=new Gson().fromJson(response.body().getResponseBody().getUnderBodyModel().result,ResultModel.class);
                if (null!=resultModel ) {
                    LoginBean loginBean=new Gson().fromJson(resultModel.getReturnValue().toString(),new TypeToken<LoginBean>(){}.getType());

                }
            }

            @Override
            public void onFailure(Call<ResponseEnvelope> call, Throwable t) {
                Log.e("报错",t.getMessage());
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
}

