package com.pasc.business.life.util;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.ICallBack;
import com.pasc.lib.log.PascLog;
import com.pasc.lib.router.BaseJumper;
import java.lang.ref.WeakReference;

/**
 * 登陆成功后触发点击动作
 * Created by zc on 2017年12月18日11:04:05
 */
public class LoginSuccessActionUtils {
    private volatile static LoginSuccessActionUtils instance = null;
    //弱引用自定义view 防止内存泄露
    private WeakReference tWeakRef;
    //亲测 callback 用弱引用，极容易被回收调，所以针对性处理
    private ICallBack callBack;
    //调用处activity的名字（用于及时清空回调逻辑）
    private String activityName;

    private LoginSuccessActionUtils() {
    }

    public static LoginSuccessActionUtils getInstance() {
        if (instance == null) {
            synchronized (LoginSuccessActionUtils.class) {
                if (instance == null) {
                    instance = new LoginSuccessActionUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 是否需要登陆
     *
     * @param t 需要回调点击事件的view
     * @return true 需要
     */
    public <T> boolean isNeedLogin(Context context, T t) {
        context = checkContext(context);
        if (AppProxy.getInstance().getUserManager().isLogin()) {
            clearCallback();
            return false;
        } else {
            goToLogin(context, t);
            return true;
        }
    }

    /**
     * 带接口的回调 （回调内容写在接口实现里面）
     */
    public <T> void checkLoginWithCallBack(Context context, final T callBack) {
        context = checkContext(context);
        if (AppProxy.getInstance().getUserManager().isLogin()) {
            if (callBack != null && callBack instanceof ICallBack){
                //已经登录的情况 直接回调
                ((ICallBack) callBack).callBack();
                clearCallback();
            }
        } else {
            goToLogin(context, callBack);
        }
    }

    /**
     * 登陆
     *
     * @param t 需要回调点击事件的view
     */
    public <T> void goToLogin(Context context, T t) {
        checkContext(context);
        if (t instanceof ICallBack) {
            callBack = (ICallBack) t;
        } else {
            this.tWeakRef = new WeakReference<>(t);
        }
      /*  AppProxy.getInstance().addOnUserLoginStateListener(new OnUserLoginStateCleanWhenCloseListener() {

            @Override
            public void onLoginClose() {

            }

            @Override
            public void onUserLogin() {
                onLoginSuccessAction();
            }

            @Override
            public void onUserLogout() {

            }
        });
        LoginParamsConfigBuilder builder = new LoginParamsConfigBuilder();
        builder.lastUserPhoneNum(UserManager.getInstance().getMobileNo());
        LoginManager.getInstance().login(AppProxy.getInstance().getApplication(), LoginConfig.LOGIN_ALL, builder.build());*/

        Bundle bundle = new Bundle();
        bundle.putString("lastUserPhoneNum", AppProxy.getInstance().getUserManager().getMobile());
        BaseJumper.jumpARouter("/login/main/act");
    }

    /**
     * 登陆之后的动作
     */
    public void onLoginSuccessAction() {
        if (tWeakRef != null && tWeakRef.get() != null) {
            if (tWeakRef.get() instanceof View) {
                View view = (View) (tWeakRef.get());
                if (view.getContext() != null) {//防止记录记录页面的控件，导致重复触发
                    tWeakRef = null;
                    return;
                }
                view.performClick();
            }
            tWeakRef = null;
        } else {
            if (callBack != null) {
                callBack.callBack();
            }
            callBack = null;
        }
    }

    /**
     * loginActivity finish 时候调用这个方法清空view 或者其他callback
     */
    public void clearCallback() {
        tWeakRef = null;
        callBack = null;
    }

    /**
     * 基类baseActivity onDestroy 回调的方法（判断如果当前finish得activity如何和纪录事件的activity是同一个activity时清空事件）
     *
     * @param context
     */
    public void onDestroyClearCallback(Context context) {
        if (context != null && !TextUtils.isEmpty(activityName) && activityName.equals(context.getClass().getName())) {
            PascLog.d("LoginSuccessActionUtils", "activity已经被finish，清空回调事件   current:" + context.getClass().getName() + "  last:" + activityName);
            clearCallback();
        }
    }

    /**
     * 是否有记录的事件（是否有下一步的点击回调动作）
     */
    public boolean isHasNextStep() {
        return (tWeakRef != null && tWeakRef.get() != null) || callBack != null;
    }

    private Context checkContext(Context context) {
        activityName = context != null ? context.getClass().getCanonicalName() : null;
        return context == null ? AppProxy.getInstance().getContext() : context;
    }


}