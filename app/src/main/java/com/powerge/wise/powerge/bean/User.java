package com.powerge.wise.powerge.bean;

import com.powerge.wise.basestone.heart.network.Notification;
import com.powerge.wise.basestone.heart.util.RxBus;
import com.powerge.wise.powerge.config.soap.beans.LoginBean;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 2018/1/30.
 */

public class User extends RealmObject {
    private static User current;
    @PrimaryKey
    public int id;
    public String account;
    public boolean isLogin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        this.isLogin = login;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public static User getCurrentUser() {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<User> result = realm.where(User.class)
                        .equalTo("isLogin", true).findAll();
                if (result.size() == 1) {
                    current = result.first();
                } else if (result.size() > 1) {
                    for (User user : result) {
                        user.setLogin(false);
                    }
                }
            }
        });
        return current;
    }

    private static void setCurrent(final User current) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<User> result = realm.where(User.class)
                        .equalTo("isLogin", true).findAll();
                for (User user : result) {
                    user.setLogin(false);
                }
                if (current == null) {
                    User.current = null;
                    return;
                }
                current.setLogin(true);
                realm.copyToRealmOrUpdate(current);
            }
        });
    }

    public static void logout() {
        if (getCurrentUser() != null)
            RxBus.getDefault().post(new Notification(131, getCurrentUser().getId()));
        setCurrent(null);

    }

    public static void login(LoginBean loginBean) {
        User user = new User();
        user.setAccount(loginBean.getUserId());
        setCurrent(user);
    }
}
