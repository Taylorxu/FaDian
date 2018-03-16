package com.powerge.wise.powerge.otherPages;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.beans.LoginBean;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityLoginBinding;
import com.wisesignsoft.OperationManagement.utils.ToastUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    String password_value, account_value;
    ActivityLoginBinding loginBinding;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginBinding.password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                } else {
                    return false;
                }
            }
        });
        loginBinding.signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }


    private void attemptLogin() {

       /* password_value = loginBinding.password.getText().toString();
        account_value = loginBinding.account.getText().toString();
        if (password_value.isEmpty() && account_value.isEmpty()) {
            ToastUtil.toast(getBaseContext(), "请输入账号和密码");
        } else {
            loginBinding.loginProgress.setVisibility(View.VISIBLE);
            loginBinding.signInButton.setEnabled(false);
            sing();
        }*/
        LoginBean log = new LoginBean();
        log.setUserName("jianlegui");
        User.login(log);
        finish();
    }


    public void sing() {
        LoginBean mobileLogin = LoginBean.newInstance();
        mobileLogin.setUserName(account_value);
        mobileLogin.setUserPassward(password_value);
        mobileLogin.setNameSpace(BaseUrl.NAMESPACE_Y);
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(mobileLogin));

        ApiService.Creator.get().login(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<LoginBean>>())
                .flatMap(new FlatMapTopRes<LoginBean>())
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {
                        loginBinding.loginProgress.setVisibility(View.GONE);
                        loginBinding.signInButton.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.toast(getBaseContext(), e.getMessage());
                        loginBinding.signInButton.setEnabled(true);
                        loginBinding.loginProgress.setVisibility(View.GONE);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        loginBinding.signInButton.setEnabled(true);
                        User.login(loginBean);
                        finish();
                    }
                });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}

