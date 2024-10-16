package com.pasc.lib.workspace;

import android.app.Activity;

public class UserProxy implements User {

    private static UserProxy userProxy = new UserProxy();
    private User user;

    public static UserProxy getInstance() {
        return userProxy;
    }

    public void init(User user) {
        if (user == null) {
            throw new RuntimeException("user不能为空");
        }
        userProxy.user = user;
    }

    @Override
    public String getToken() {
        if (user != null) {
            return user.getToken();
        }
        return null;
    }

    @Override
    public String getPhoneNum() {
        if (user != null) {
            return user.getPhoneNum();
        }
        return null;
    }

    @Override
    public String getUserId() {
        if (user != null) {
            return user.getUserId();
        }
        return null;
    }

    @Override
    public boolean hasLoggedOn() {
        if (user != null) {
            return user.hasLoggedOn();
        }
        return false;
    }

    @Override
    public void notifyOnlineStateChangeInvalidToken() {
        if (user != null) {
            user.notifyOnlineStateChangeInvalidToken();
        }
    }

    @Override
    public void notifyOnlineStateChangeKickOff() {
        if (user != null) {
            user.notifyOnlineStateChangeKickOff();
        }
    }

    @Override
    public void checkLoginWithCallBack(Activity activity, Runnable callBack) {
        if (user != null) {
            user.checkLoginWithCallBack(activity, callBack);
        }
    }
}
