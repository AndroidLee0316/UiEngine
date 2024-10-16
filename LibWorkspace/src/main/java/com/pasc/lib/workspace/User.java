package com.pasc.lib.workspace;

import android.app.Activity;

public interface User {

    String getToken();

    String getPhoneNum();

    String getUserId();

    boolean hasLoggedOn();

    void notifyOnlineStateChangeInvalidToken();

    void notifyOnlineStateChangeKickOff();

    void checkLoginWithCallBack(Activity activity, Runnable callBack);
}
