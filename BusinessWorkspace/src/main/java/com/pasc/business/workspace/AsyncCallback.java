package com.pasc.business.workspace;

import com.pasc.business.workspace.helper.ExceptionHandler;
import com.pasc.lib.base.util.ToastUtils;
import com.pasc.lib.net.ApiError;
import com.pasc.lib.workspace.UserProxy;
import com.pasc.lib.workspace.bean.NtErrorCode;

/**
 * 异步回调.
 * Created by chenruihan410 on 2018/09/10.
 */
public abstract class AsyncCallback<T> {

    public void onNext(T t) {
        try {
            onCallback(t);
        } catch (Exception e) {
            e.printStackTrace();
            onError(new RuntimeException("UI更新发生异常"));
        }
    }

    protected abstract void onCallback(T t) throws Exception; // 接收到数据

    // 结束
    public void onEnd() {
    }

    // 开始
    public void onStart() {
    }

    // 是否开启异常信息提示，默认是关闭的
    protected boolean showErrorMessage() {
        return false;
    }

    // 发生异常
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        if (showErrorMessage()) {
            String msg = ExceptionHandler.handleException(throwable);
            ToastUtils.toastMsg(msg);
        }
        if (throwable instanceof ApiError) {
            ApiError apiError = (ApiError) throwable;
            int code = apiError.getCode();
            if ((NtErrorCode.UNLEGEL_TOKE <= code && code <= NtErrorCode.NULL_TOKEN)) {//token失效了
                UserProxy.getInstance().notifyOnlineStateChangeInvalidToken();
            } else if (code == NtErrorCode.EDGE_OUT) {//账号被踢了
                UserProxy.getInstance().notifyOnlineStateChangeKickOff();
            }
        }
    }
}
